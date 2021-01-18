package io.relayr.product.comparison.controller;

import io.relayr.product.comparison.model.dto.MessageDto;
import io.relayr.product.comparison.model.dto.ProductInfoDto;
import io.relayr.product.comparison.model.dto.ProviderRankingDto;
import io.relayr.product.comparison.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * @return Returns the list of all products
     */
    @GetMapping("/all")
    List<ProductInfoDto> getAllProducts() {
        return productService.getAllProviderProductsSortedById();
    }

    /**
     * Returns the list of the products by name and category
     *
     * @param name:     Product name
     * @param category: Product category
     * @return Returns the list of the products with given name and category
     */
    @GetMapping("/search")
    List<ProductInfoDto> searchProducts(@NotBlank @RequestParam(value = "name") String name,
                                        @NotBlank @RequestParam(value = "category") String category) {
        return productService.getProductListByNameAndCategory(name, category);
    }

    /**
     * Creates a new record of ProviderProduct inside database
     *
     * @param productInfoDto: Object of ProductInfoDto
     * @return Returns the message of success with status HttpStatus.CREATED (201)
     * @throws Throws an exception with status HttpStatus.FORBIDDEN (403) if Provider already has product inside database
     */
    @PostMapping
    ResponseEntity<MessageDto> createProduct(@Valid @RequestBody ProductInfoDto productInfoDto) {
        productService.createProduct(productInfoDto, true);
        return new ResponseEntity<>(new MessageDto("Product has successfully created"), HttpStatus.CREATED);
    }

    /**
     * Creates a new records of ProviderProduct from file inside database
     *
     * @param file: File with list of objects of ProductInfoDto
     * @return Returns the message of success with status HttpStatus.CREATED (201)
     * @throws Throws an exception with status HttpStatus.BAD_REQUEST (400) if file is malformed
     */
    @PostMapping("/import")
    ResponseEntity<MessageDto> importProductsFromFile(@RequestParam(value = "file") MultipartFile file) {
        productService.createProductsFromFile(file);
        return new ResponseEntity<>(new MessageDto("Products has successfully created"), HttpStatus.CREATED);
    }

    /**
     * Creates or updates a record of ProviderRanking inside database
     *
     * @param providerRankingDto: Object of ProviderRankingDto
     * @return Returns the message of success with status HttpStatus.CREATED (201)
     */
    @PostMapping("/providerRanking")
    ResponseEntity<MessageDto> saveProviderRanking(@Valid @RequestBody ProviderRankingDto providerRankingDto) {
        productService.createOrUpdateProviderRanking(providerRankingDto);
        return new ResponseEntity<>(new MessageDto("Products has successfully created"), HttpStatus.OK);
    }
}