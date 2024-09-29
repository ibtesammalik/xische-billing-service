package com.xische.xischebilling.exception;

import org.springframework.http.HttpStatus;


import com.xische.xischebilling.util.Constants;

public class BillingServiceException extends Exception {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -471650683449072165L;
	private Integer errorCode;
	    private String errorMessage;

	    public BillingServiceException(Integer code) {
	        this(code, Constants.Errors.getMessageByCode(code));
	    }

	    public BillingServiceException(Constants.Errors error) {
	        this(error.getCode(), error.getDescription());
	    }

	    public BillingServiceException(Integer code, String errorMessage) {
	        super(errorMessage);
	        this.errorCode = code;
	        this.errorMessage = errorMessage;
	    }

    public BillingServiceException(Integer integer, String message, HttpStatus httpStatus) {
    }

    public Integer getErrorCode() {
	        return this.errorCode;
	    }

	    public String getErrorMessage() {
	        return this.errorMessage;
	    }
}
