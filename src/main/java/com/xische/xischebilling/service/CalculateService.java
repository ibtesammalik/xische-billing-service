package com.xische.xischebilling.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xische.xischebilling.client.CurrencyConversionClient;
import com.xische.xischebilling.dto.ApiResponse;
import com.xische.xischebilling.dto.CalculateBillRequest;
import com.xische.xischebilling.dto.CalculateBillResponse;
import com.xische.xischebilling.dto.ConversionRates;
import com.xische.xischebilling.dto.ExchangeRateResponse;
import com.xische.xischebilling.dto.Items;
import com.xische.xischebilling.exception.BillingServiceException;
import com.xische.xischebilling.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CalculateService {

	@Autowired
	CurrencyConversionClient conversionClient;


	@Value("${employee.discount}")
	private BigDecimal employeDiscountPercent;

	@Value("${affiliate.discount}")
	private BigDecimal affiliateDiscountPercent;

	@Value("${customer.discount}")
	private BigDecimal customerDiscountPercent;

	@Value("${customer.discount.tenture}")
	private int customerDiscountTenture;

	@Value("${discount.hunderd}")
	private BigDecimal discountOnHunderd;


	@Value("${discount.bill}")
	private BigDecimal discountOnBill;

	public static ExchangeRateResponse exchangeReponse=null;

	public ApiResponse<CalculateBillResponse> calculatedBill(CalculateBillRequest billRequest)throws BillingServiceException{


		boolean percantageDiscount=false;
		CalculateBillResponse billResponse= new CalculateBillResponse();
		BigDecimal totalAmount=billRequest.getTotalAmount();
		if(exchangeReponse==null) {
			exchangeReponse=	conversionClient.getConversionRate();
		}
		if(Constants.Errors.ERR_CONNECTIVITY_ERROR.getCode().toString().equalsIgnoreCase(exchangeReponse.getResult())){
			return new ApiResponse<CalculateBillResponse>(Constants.Errors.ERR_CONNECTIVITY_ERROR.getCode(), Constants.Errors.ERR_CONNECTIVITY_ERROR.getDescription(), null);

		}
		switch (billRequest.getUserType()) {
		case "employee":
			if(!percantageDiscount&&!haveGrocery(billRequest)) {
				totalAmount =percentage(billRequest.getTotalAmount(),employeDiscountPercent);
				percantageDiscount=true;
			}
			break;
		case "affiliate":
			if(!percantageDiscount&&!haveGrocery(billRequest)) {
				totalAmount =percentage(billRequest.getTotalAmount(),affiliateDiscountPercent);
				percantageDiscount=true;
			}
			break;
		case "customer":

			if(billRequest.getCustomerTenure()>=customerDiscountTenture) {
				if(!percantageDiscount&&!haveGrocery(billRequest)) {
					totalAmount =percentage(billRequest.getTotalAmount(),customerDiscountPercent);
					percantageDiscount=true;
				}
			}
			break;
		}
		totalAmount=totalAmount.subtract(calculateDiscountOnAmount(totalAmount));
		BigDecimal targetedCurencyAmount= currencyConversion(billRequest.getOrginalCurrency(),billRequest.getTargetCurrency(),exchangeReponse.getConversion_rates(),totalAmount);

		billResponse.setOrginalCurrency(billRequest.getOrginalCurrency());
		billResponse.setTotalAmount(billRequest.getTotalAmount());
		billResponse.setTargetCurrency(billRequest.getTargetCurrency());
		billResponse.setConversionAmount(targetedCurencyAmount.toString());
		return new ApiResponse<CalculateBillResponse>(Constants.SuccessResponses.VALID_REQUEST.getCode(), Constants.SuccessResponses.VALID_REQUEST.getDescription(), billResponse);

	}

	private boolean haveGrocery(CalculateBillRequest billRequest) {


		for ( Items item : billRequest.getItems()) {
			return item.getCategories().stream().anyMatch(grc->grc.contains("grocery"));

		}
		return false;
	}

	private BigDecimal currencyConversion(String source, String target, ConversionRates conrate, BigDecimal billpaymentAmount ) {
		try {
			double sourceValue=callByName(source,conrate);
			double targetValue=callByName(target,conrate);
			log.info("source currency according to USD:"+source +": source value "+sourceValue);
			log.info("target currency according to USD::"+target+": target value "+targetValue);
			log.info("Amount: "+billpaymentAmount);
			BigDecimal convertToUsd=billpaymentAmount.divide(BigDecimal.valueOf(sourceValue),2, RoundingMode.HALF_UP);
			BigDecimal targetedAmount=convertToUsd.multiply(BigDecimal.valueOf(targetValue));

			log.info("Targeted Amount: "+targetedAmount);
			return targetedAmount;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BigDecimal.ZERO;
	}


	public double callByName(String funcName,ConversionRates conrate) throws ClassNotFoundException {
		try {

			//	Class c=Class.forName("com.xische.xischebilling.dto.ConversionRates");
			Method method = conrate.getClass().getDeclaredMethod("get"+funcName);
			Object obj=  method.invoke(conrate);
			if(obj instanceof Double)
				return ((Double)obj).doubleValue();
			else
				return 0.0;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	private  BigDecimal percentage(BigDecimal base, BigDecimal pct){
		return base.multiply(pct).divide(ONE_HUNDRED);
	}

	private BigDecimal calculateDiscountOnAmount(BigDecimal totalamount) {
		return  BigDecimal.valueOf(totalamount.divide(discountOnBill).intValue()).multiply(discountOnHunderd);
	}
	/*
	public static void main (String ...args) throws JsonProcessingException
	{
		CalculateBillRequest billRequest = new CalculateBillRequest();
		billRequest.setCustomerTenure(1);
		billRequest.setOrginalCurrency("USD");
		billRequest.setTargetCurrency("PKR");
		billRequest.setTotalAmount(BigDecimal.TEN);
		billRequest.setUserType("emp");

		Items item = new Items();
		item.setItems("Meat");
		item.setCategories(List.of("eatable", "groceray"));

		List<Items> itemList = new ArrayList<>();
		itemList.add(item);

		billRequest.setItems(itemList);



		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(billRequest));
		}
	 */
}
