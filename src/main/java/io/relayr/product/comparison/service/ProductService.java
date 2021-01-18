package io.relayr.product.comparison.service;

import io.relayr.product.comparison.exception.CustomResponseStatusExceptionHandler;
import io.relayr.product.comparison.model.Product;
import io.relayr.product.comparison.model.Provider;
import io.relayr.product.comparison.model.ProviderProduct;
import io.relayr.product.comparison.model.ProviderRanking;
import io.relayr.product.comparison.model.dto.ProductInfoDto;
import io.relayr.product.comparison.model.dto.ProviderRankingDto;
import io.relayr.product.comparison.repository.ProductRepository;
import io.relayr.product.comparison.repository.ProviderProductRepository;
import io.relayr.product.comparison.repository.ProviderRankingRepository;
import io.relayr.product.comparison.repository.ProviderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger LOGGER = LogManager.getLogger(ProductService.class);

    private final Validator validator;

    private final ProductRepository productRepository;

    private final ProviderRepository providerRepository;

    private final ProviderRankingRepository providerRankingRepository;

    private final ProviderProductRepository providerProductRepository;

    private final FileParserFactory fileParserFactory;

    public ProductService(Validator validator, ProductRepository productRepository, ProviderRepository providerRepository, ProviderProductRepository providerProductRepository, FileParserFactory fileParserFactory, ProviderRankingRepository providerRankingRepository) {
        this.validator = validator;
        this.productRepository = productRepository;
        this.providerRepository = providerRepository;
        this.providerProductRepository = providerProductRepository;
        this.fileParserFactory = fileParserFactory;
        this.providerRankingRepository = providerRankingRepository;
    }

    private Sort sortByIdAsc() {
        return Sort.by(Sort.Direction.ASC, "id");
    }

    public List<ProductInfoDto> getAllProviderProductsSortedById() {
        List<ProviderProduct> providerProductList = providerProductRepository.findAll(sortByIdAsc());
        return convertProductToProductInfoDto(providerProductList);
    }

    public List<ProductInfoDto> getProductListByNameAndCategory(String name, String category) {
        try {
            List<ProviderProduct> providerProductList = Collections.emptyList();
            Product product = productRepository.findByNameAndCategory(name, category);
            if (product != null) {
                providerProductList = providerProductRepository.findByProduct(product);
            }

            return convertProductToProductInfoDto(providerProductList);
        } catch (DataAccessException ex) {
            LOGGER.error("Error while querying for Product list by name={} and category={}", name, category);
            LOGGER.error(ex.getMessage());
            throw new CustomResponseStatusExceptionHandler(HttpStatus.INTERNAL_SERVER_ERROR, CustomResponseStatusExceptionHandler.INTERNAL_ERROR);
        }
    }

    public void createProduct(ProductInfoDto productInfoDto, Boolean isAlreadyValidated) {
        ProviderProduct providerProduct = convertDtoToProviderProduct(productInfoDto, isAlreadyValidated);
        providerProductRepository.save(providerProduct);
    }

    public void createProductsFromFile(MultipartFile file) {
        Optional<String> fileType = getFileType(file.getOriginalFilename());
        if (!fileType.isPresent())
            throw new CustomResponseStatusExceptionHandler(HttpStatus.BAD_REQUEST, CustomResponseStatusExceptionHandler.ERROR_FILE_NO_EXTENSION);

        FileParser fileParser = fileParserFactory.getFileParser(fileType.get());
        try {
            List<ProductInfoDto> productInfoDtoList = fileParser.parse(file.getInputStream());

            for (int i = 0; i < productInfoDtoList.size(); i++) {
                try {
                    createProduct(productInfoDtoList.get(i), false);
                } catch (ConstraintViolationException ex) {
                    LOGGER.error("Row {} is not valid", i + 1);
                    LOGGER.error(ex.getMessage());
                } catch (CustomResponseStatusExceptionHandler ex) {
                    LOGGER.error("Row {}", i + 1);
                    LOGGER.error(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            throw new CustomResponseStatusExceptionHandler(HttpStatus.BAD_REQUEST, CustomResponseStatusExceptionHandler.ERROR_FILE_PARSE);
        }
    }

    public void createOrUpdateProviderRanking(ProviderRankingDto providerRankingDto) {
        ProviderRanking providerRanking = null;
        Provider provider = providerRepository.findByName(providerRankingDto.getName());

        if (provider != null)
            providerRanking = providerRankingRepository.findByProvider(provider);
        else
            provider = new Provider(providerRankingDto.getName());

        if (providerRanking == null)
            providerRanking = new ProviderRanking(provider);

        providerRanking.setRanking(providerRankingDto.getRanking());
        providerRankingRepository.save(providerRanking);
    }

    private List<ProductInfoDto> convertProductToProductInfoDto(List<ProviderProduct> providerProductList) {
        return providerProductList.stream()
                .sorted(Comparator.comparing(a -> a.getProvider().getProviderRanking() == null ? 0 : a.getProvider().getProviderRanking().getRanking(), Comparator.reverseOrder()))
                .map(ProviderProduct::toDto).collect(Collectors.toList());
    }

    private ProviderProduct convertDtoToProviderProduct(ProductInfoDto productInfoDto, Boolean isAlreadyValidated) {
        if (!isAlreadyValidated) {
            Set<ConstraintViolation<ProductInfoDto>> constraintViolation = validator.validate(productInfoDto);
            if (!constraintViolation.isEmpty())
                throw new ConstraintViolationException(constraintViolation);
        }

        Provider provider = providerRepository.findByName(productInfoDto.getProviderName());
        if (provider == null)
            provider = new Provider(productInfoDto.getProviderName());

        Product product = productRepository.findByNameAndCategory(productInfoDto.getName(), productInfoDto.getCategory());
        if (product == null)
            product = new Product(productInfoDto.getName(), productInfoDto.getCategory());

        if (product.getId() != 0 && provider.getId() != 0) {
            if (providerProductRepository.findByProviderAndProduct(provider, product) != null) {
                throw new CustomResponseStatusExceptionHandler(HttpStatus.FORBIDDEN, CustomResponseStatusExceptionHandler.PROVIDER_PRODUCT_ALREADY_EXISTS);
            }
        }

        return new ProviderProduct(provider, product, productInfoDto.getPrice());
    }

    private Optional<String> getFileType(String filename) {
        return Optional.ofNullable(filename).filter(f -> f.contains(".")).map(f -> f.substring(filename.lastIndexOf(".") + 1).toUpperCase());
    }
}