package io.relayr.product.comparison.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomResponseStatusExceptionHandler extends ResponseStatusException {

    public static final String INTERNAL_ERROR = "Oops, sorry, something went wrong";
    public static final String PROVIDER_PRODUCT_ALREADY_EXISTS = "You already have this product";
    public static final String FILE_NOT_IMPLEMENTED = "Sorry, your file type is not implemented yet";
    public static final String ERROR_FILE_NO_EXTENSION = "Upload a file with an extension";
    public static final String ERROR_FILE_PARSE = "Error while parsing file. Please, check your file";

    public CustomResponseStatusExceptionHandler(HttpStatus status, String reason) {
        super(status, reason);
    }
}