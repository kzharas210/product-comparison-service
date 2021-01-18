package io.relayr.product.comparison;

import io.relayr.product.comparison.controller.ProductController;
import io.relayr.product.comparison.model.dto.ProductInfoDto;
import io.relayr.product.comparison.model.dto.ProviderRankingDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Sql(scripts = "classpath:data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductComparisonApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductController productController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(productController);
    }

    @Test
    public void testGetAllProductsForSuccess() {
        ResponseEntity<ProductInfoDto[]> responseEntity = restTemplate.getForEntity("/product/all", ProductInfoDto[].class);

        Assertions.assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(5, Objects.requireNonNull(responseEntity.getBody()).length);
    }

    @Test
    public void testSearchProductsForSuccess() {
        ResponseEntity<ProductInfoDto[]> responseEntity =
                restTemplate.getForEntity("/product/search?name={name}&category={category}", ProductInfoDto[].class, "name1", "category1");

        Assertions.assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).length);
    }

    @Test
    public void testCreateProductForSuccess() {
        ProductInfoDto productInfoDto = new ProductInfoDto("name10", "category10", 10.0, "provider10");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/product", productInfoDto, String.class);

        Assertions.assertSame(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void testCreateProductForInvalidData() {
        ProductInfoDto productInfoDto = new ProductInfoDto("", "", 0.0, "");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/product", productInfoDto, String.class);

        Assertions.assertSame(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateProductForAlreadyExistingData() {
        ProductInfoDto productInfoDto = new ProductInfoDto("name10", "category10", 10.0, "provider10");

        restTemplate.postForEntity("/product", productInfoDto, String.class);
        ResponseEntity<String> responseEntitySecond = restTemplate.postForEntity("/product", productInfoDto, String.class);

        Assertions.assertSame(responseEntitySecond.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void tesImportProductsFromFileForSuccess() {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file", new ClassPathResource("test.csv"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "multipart/form-data");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/product/import", new HttpEntity<>(parameters, headers), String.class);

        Assertions.assertSame(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void tesImportProductsFromFileForError() {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file", new ClassPathResource("data.sql"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "multipart/form-data");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/product/import", new HttpEntity<>(parameters, headers), String.class);

        Assertions.assertSame(responseEntity.getStatusCode(), HttpStatus.NOT_IMPLEMENTED);
    }

    @Test
    public void testProviderRankingForSuccess() {
        ProviderRankingDto providerRankingDto = new ProviderRankingDto("provider1", 4.0);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/product/providerRanking", providerRankingDto, String.class);

        Assertions.assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}