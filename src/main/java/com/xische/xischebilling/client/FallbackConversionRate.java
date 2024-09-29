package com.xische.xischebilling.client;

import org.springframework.stereotype.Component;

import com.xische.xischebilling.dto.ExchangeRateResponse;
import com.xische.xischebilling.util.Constants;

@Component
public class FallbackConversionRate implements CurrencyConversionClient {

	@Override
	public ExchangeRateResponse getConversionRate(){
		ExchangeRateResponse onelinkReponse = new ExchangeRateResponse();
	//	onelinkReponse.setResponseCode(Constants.Errors.ERR_CONNECTIVITY_ERROR.getCode()+"");
		onelinkReponse.setResult(Constants.Errors.ERR_CONNECTIVITY_ERROR.getCode()+"");
		return onelinkReponse;

	}

}
