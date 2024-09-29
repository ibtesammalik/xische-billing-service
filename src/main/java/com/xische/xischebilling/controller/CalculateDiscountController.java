package com.xische.xischebilling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xische.xischebilling.dto.ApiResponse;
import com.xische.xischebilling.dto.CalculateBillRequest;
import com.xische.xischebilling.dto.CalculateBillResponse;
import com.xische.xischebilling.dto.ExchangeRateResponse;
import com.xische.xischebilling.exception.BillingServiceException;
import com.xische.xischebilling.logs.LogExecution;
import com.xische.xischebilling.service.CalculateService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class CalculateDiscountController {

	@Autowired
	CalculateService calculcateServiceObj;

	@PostMapping("/calculate")
	@LogExecution
	public ResponseEntity<?> calculateAmount(@RequestHeader HttpHeaders headers ,@Valid @RequestBody CalculateBillRequest BillRequest) {

		try {

			log.info("Bill Request"+BillRequest);
			ApiResponse<CalculateBillResponse> reponseCalculationRep= calculcateServiceObj.calculatedBill(BillRequest);
			ResponseEntity<?> resp=new ResponseEntity<>(reponseCalculationRep, HttpStatus.OK);
			return resp;
		} catch (BillingServiceException e) {

			e.printStackTrace(); 
			return  new ResponseEntity<>(new ApiResponse<>(201, e.getMessage(), null),HttpStatus.OK);
		}
		
	}

	@GetMapping("/exc")
	@LogExecution
	public ResponseEntity<?> exchangeRate(@RequestBody ExchangeRateResponse exchange) {

		try {
			log.info("exchange Set"+exchange);
			CalculateService.exchangeReponse=exchange;
			ResponseEntity<?> resp=new ResponseEntity<>(exchange, HttpStatus.OK);
			return resp;
		} catch (Exception e) {

			e.printStackTrace(); 
			//		return  new ResponseEntity<>(new ApiResponse<>(Integer.valueOf(ConstantsTransaction.FAIL_CODE), e.getMessage(), null),HttpStatus.OK);
		}
		return null;
	}
}
