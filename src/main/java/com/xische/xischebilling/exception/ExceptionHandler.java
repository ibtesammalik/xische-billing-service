package com.xische.xischebilling.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage resourceNotFoundException(NullPointerException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return message;

	}

	

	@org.springframework.web.bind.annotation.ExceptionHandler(BillingServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage globalExceptionHandler(BillingServiceException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return message;
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
	
	@org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ErrorMessage handleValidationErrors(org.springframework.http.converter.HttpMessageNotReadableException ex) {
    
                
        		ErrorMessage message = new ErrorMessage(
        				HttpStatus.INTERNAL_SERVER_ERROR.value(),
        				new Date(),
        				ex.getMessage(),
        				"Request Exception");

        		return message;
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}


