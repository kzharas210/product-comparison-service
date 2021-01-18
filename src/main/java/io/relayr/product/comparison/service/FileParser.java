package io.relayr.product.comparison.service;

import io.relayr.product.comparison.model.dto.ProductInfoDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileParser {

    List<ProductInfoDto> parse(InputStream inputStream) throws IOException;
}