package io.relayr.product.comparison.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import io.relayr.product.comparison.model.dto.ProductInfoDto;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class CsvFileParser implements FileParser {

    @Override
    public List<ProductInfoDto> parse(InputStream inputStream) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CsvToBean<ProductInfoDto> cb = new CsvToBeanBuilder<ProductInfoDto>(reader)
                    .withType(ProductInfoDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return cb.parse();
        }
    }
}