package io.relayr.product.comparison.service;

import io.relayr.product.comparison.exception.CustomResponseStatusExceptionHandler;
import io.relayr.product.comparison.model.enums.FileType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FileParserFactory {

    private final CsvFileParser csvFileParser;

    public FileParserFactory(CsvFileParser csvFileParser) {
        this.csvFileParser = csvFileParser;
    }

    public FileParser getFileParser(String fileType) {
        if (fileType.equals(FileType.CSV.name())) {
            return csvFileParser;
        }
        throw new CustomResponseStatusExceptionHandler(HttpStatus.NOT_IMPLEMENTED, CustomResponseStatusExceptionHandler.FILE_NOT_IMPLEMENTED);
    }
}