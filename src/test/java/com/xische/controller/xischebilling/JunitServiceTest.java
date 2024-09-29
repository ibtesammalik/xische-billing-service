package com.xische.controller.xischebilling;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.xische.xischebilling.client.CurrencyConversionClient;
import com.xische.xischebilling.dto.ApiResponse;
import com.xische.xischebilling.dto.CalculateBillRequest;
import com.xische.xischebilling.dto.CalculateBillResponse;
import com.xische.xischebilling.dto.ConversionRates;
import com.xische.xischebilling.dto.ExchangeRateResponse;
import com.xische.xischebilling.dto.Items;
import com.xische.xischebilling.exception.BillingServiceException;
import com.xische.xischebilling.service.CalculateService;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootTest(classes = JunitServiceTest.class)
public class JunitServiceTest {

	@InjectMocks
	CalculateService calculcateServiceObj;

	@Mock
	CurrencyConversionClient conversionClient;


		@Test
	@Order(1)
	public void getEmployeeWithGrocery() {
		try {

			CalculateBillRequest billRequest = new CalculateBillRequest();
			billRequest.setCustomerTenure(1);
			billRequest.setOrginalCurrency("AED");
			billRequest.setTargetCurrency("PKR");
				billRequest.setTotalAmount(BigDecimal.valueOf(4958));
			billRequest.setUserType("employee");

			Items item = new Items();
			item.setItems("Meat");
			item.setCategories(List.of("eatable", "grocery"));

			List<Items> itemList = new ArrayList<>();
			itemList.add(item);

			billRequest.setItems(itemList);

			ExchangeRateResponse resObj=new ExchangeRateResponse();
			resObj.setResult("success");

			ConversionRates conversionRates= new ConversionRates();
			conversionRates.setAED(3.6725);
			conversionRates.setPKR(277.7713);

			resObj.setConversion_rates(conversionRates);
			resObj.setBase_code("USD");


			when(conversionClient.getConversionRate()).thenReturn(resObj);


			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnBill", BigDecimal.valueOf(100));
			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnHunderd", BigDecimal.valueOf(5));

			ApiResponse<CalculateBillResponse> resp=	calculcateServiceObj.calculatedBill(billRequest);

			System.out.println("**************Employee: with grocery "+resp);
		} catch (BillingServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	@Order(2)
	public void getEmployeeWithOutGroceryRecord() {
		try {

			CalculateBillRequest billRequest = new CalculateBillRequest();
			billRequest.setCustomerTenure(1);
			billRequest.setOrginalCurrency("AED");
			billRequest.setTargetCurrency("PKR");
				billRequest.setTotalAmount(BigDecimal.valueOf(4958));
			billRequest.setUserType("employee");

			Items item = new Items();
			item.setItems("Meat");
			item.setCategories(List.of("eatable"));

			List<Items> itemList = new ArrayList<>();
			itemList.add(item);

			billRequest.setItems(itemList);

			ExchangeRateResponse resObj=new ExchangeRateResponse();
			resObj.setResult("success");

			ConversionRates conversionRates= new ConversionRates();
			conversionRates.setAED(3.6725);
			conversionRates.setPKR(277.7713);

			resObj.setConversion_rates(conversionRates);
			resObj.setBase_code("USD");


			when(conversionClient.getConversionRate()).thenReturn(resObj);


			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnBill", BigDecimal.valueOf(100));
			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnHunderd", BigDecimal.valueOf(5));

			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "employeDiscountPercent", BigDecimal.valueOf(30.0));

			
			ApiResponse<CalculateBillResponse> resp=	calculcateServiceObj.calculatedBill(billRequest);

			System.out.println("**************Employee with out grocery "+resp);
		} catch (BillingServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	@Order(3)
	public void getAffiliateWithGrocery() {
		try {

			CalculateBillRequest billRequest = new CalculateBillRequest();
			billRequest.setCustomerTenure(1);
			billRequest.setOrginalCurrency("AED");
			billRequest.setTargetCurrency("PKR");
				billRequest.setTotalAmount(BigDecimal.valueOf(4958));
			billRequest.setUserType("affiliate");

			Items item = new Items();
			item.setItems("Meat");
			item.setCategories(List.of("eatable", "grocery"));

			List<Items> itemList = new ArrayList<>();
			itemList.add(item);

			billRequest.setItems(itemList);

			ExchangeRateResponse resObj=new ExchangeRateResponse();
			resObj.setResult("success");

			ConversionRates conversionRates= new ConversionRates();
			conversionRates.setAED(3.6725);
			conversionRates.setPKR(277.7713);

			resObj.setConversion_rates(conversionRates);
			resObj.setBase_code("USD");


			when(conversionClient.getConversionRate()).thenReturn(resObj);


			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnBill", BigDecimal.valueOf(100));
			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnHunderd", BigDecimal.valueOf(5));

			ApiResponse<CalculateBillResponse> resp=	calculcateServiceObj.calculatedBill(billRequest);

			System.out.println("**************affiliate: with grocery "+resp);
		} catch (BillingServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(4)
	public void getAffiliateWithOutGroceryRecord() {
		try {

			CalculateBillRequest billRequest = new CalculateBillRequest();
			billRequest.setCustomerTenure(1);
			billRequest.setOrginalCurrency("AED");
			billRequest.setTargetCurrency("PKR");
			billRequest.setTotalAmount(BigDecimal.valueOf(4958));
			billRequest.setUserType("affiliate");

			Items item = new Items();
			item.setItems("Meat");
			item.setCategories(List.of("eatable"));

			List<Items> itemList = new ArrayList<>();
			itemList.add(item);

			billRequest.setItems(itemList);

			ExchangeRateResponse resObj=new ExchangeRateResponse();
			resObj.setResult("success");

			ConversionRates conversionRates= new ConversionRates();
			conversionRates.setAED(3.6725);
			conversionRates.setPKR(277.7713);

			resObj.setConversion_rates(conversionRates);
			resObj.setBase_code("USD");


			when(conversionClient.getConversionRate()).thenReturn(resObj);


			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnBill", BigDecimal.valueOf(100));
			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnHunderd", BigDecimal.valueOf(5));

			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "affiliateDiscountPercent", BigDecimal.valueOf(10));

			ApiResponse<CalculateBillResponse> resp=	calculcateServiceObj.calculatedBill(billRequest);

			System.out.println("**************Affiliate with out grocery "+resp);
		} catch (BillingServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@Test
	@Order(5)
	public void getCustomerWithGroceryRecord() {
		try {

			CalculateBillRequest billRequest = new CalculateBillRequest();
			billRequest.setCustomerTenure(1);
			billRequest.setOrginalCurrency("AED");
			billRequest.setTargetCurrency("PKR");
				billRequest.setTotalAmount(BigDecimal.valueOf(4958));
			billRequest.setUserType("customer");

			Items item = new Items();
			item.setItems("Meat");
			item.setCategories(List.of("eatable", "grocery"));

			List<Items> itemList = new ArrayList<>();
			itemList.add(item);

			billRequest.setItems(itemList);

			ExchangeRateResponse resObj=new ExchangeRateResponse();
			resObj.setResult("success");

			ConversionRates conversionRates= new ConversionRates();
			conversionRates.setAED(3.6725);
			conversionRates.setPKR(277.7713);

			resObj.setConversion_rates(conversionRates);
			resObj.setBase_code("USD");


			when(conversionClient.getConversionRate()).thenReturn(resObj);


			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnBill", BigDecimal.valueOf(100));
			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnHunderd", BigDecimal.valueOf(5));

			ApiResponse<CalculateBillResponse> resp=	calculcateServiceObj.calculatedBill(billRequest);

			System.out.println("**************customer: with grocery "+resp);
		} catch (BillingServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	@Order(6)
	public void getCustomerWithoutGroceryRecord() {
		try {

			CalculateBillRequest billRequest = new CalculateBillRequest();
			billRequest.setCustomerTenure(2);
			billRequest.setOrginalCurrency("AED");
			billRequest.setTargetCurrency("PKR");
				billRequest.setTotalAmount(BigDecimal.valueOf(4958));
			billRequest.setUserType("customer");

			Items item = new Items();
			item.setItems("Meat");
			item.setCategories(List.of("eatable"));

			List<Items> itemList = new ArrayList<>();
			itemList.add(item);

			billRequest.setItems(itemList);

			ExchangeRateResponse resObj=new ExchangeRateResponse();
			resObj.setResult("success");

			ConversionRates conversionRates= new ConversionRates();
			conversionRates.setAED(3.6725);
			conversionRates.setPKR(277.7713);

			resObj.setConversion_rates(conversionRates);
			resObj.setBase_code("USD");


			when(conversionClient.getConversionRate()).thenReturn(resObj);


			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnBill", BigDecimal.valueOf(100));
			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "discountOnHunderd", BigDecimal.valueOf(5));

			
			
			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "customerDiscountTenture", 2);
			org.springframework.test.util.ReflectionTestUtils.setField(calculcateServiceObj, "customerDiscountPercent", BigDecimal.valueOf(5));

			
			ApiResponse<CalculateBillResponse> resp=	calculcateServiceObj.calculatedBill(billRequest);

			System.out.println("**************customer with 2 tenure: with out grocery "+resp);
		} catch (BillingServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	

}
