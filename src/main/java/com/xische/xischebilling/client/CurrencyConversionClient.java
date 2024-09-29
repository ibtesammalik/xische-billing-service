package com.xische.xischebilling.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.xische.xischebilling.dto.ExchangeRateResponse;

@FeignClient(name = "exchange-rate-service", url = "${exchange.rate.service.url}",fallback = FallbackConversionRate.class)
public interface CurrencyConversionClient {

	@GetMapping("/latest/USD")
	ExchangeRateResponse  getConversionRate() ;
	
	
	
}
