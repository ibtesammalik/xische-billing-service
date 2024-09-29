package com.xische.controller.xischebilling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.xische.xischebilling.controller.CalculateDiscountController;
import com.xische.xischebilling.dto.ApiResponse;
import com.xische.xischebilling.dto.CalculateBillRequest;
import com.xische.xischebilling.dto.CalculateBillResponse;
import com.xische.xischebilling.dto.Items;
import com.xische.xischebilling.exception.BillingServiceException;
import com.xische.xischebilling.service.CalculateService;
import com.xische.xischebilling.util.Constants;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootTest(classes = JunitControllerTest.class)
public class JunitControllerTest {

	@Mock
	CalculateService calculcateServiceObj;

	@InjectMocks
	CalculateDiscountController calculateController;




	private Validator validator;

	@BeforeEach
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@Order(1)
	public void get_calculateAmount() {
		try {
			HttpHeaders header= new HttpHeaders();

			CalculateBillRequest billRequest = new CalculateBillRequest();
			billRequest.setCustomerTenure(1);
			billRequest.setOrginalCurrency("USD");
			billRequest.setTargetCurrency("PKR");
			billRequest.setTotalAmount(BigDecimal.TEN);
			billRequest.setUserType("employee");

			Items item = new Items();
			item.setItems("Meat");
			item.setCategories(List.of("eatable", "grocery"));

			List<Items> itemList = new ArrayList<>();
			itemList.add(item);

			billRequest.setItems(itemList);

			ApiResponse<CalculateBillResponse> res=new ApiResponse<CalculateBillResponse>
			(Constants.SuccessResponses.VALID_REQUEST.getCode(), Constants.SuccessResponses.VALID_REQUEST.getDescription(), new CalculateBillResponse(BigDecimal.valueOf(100.00), "AED", "PKR", "171"));



			when(calculcateServiceObj.calculatedBill(billRequest)).thenReturn(res);
			ResponseEntity<?> resp= calculateController.calculateAmount(header, billRequest);

			assertEquals(HttpStatus.OK, resp.getStatusCode());
		} catch (BillingServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	@Order(2)
	public void get_checkCurrency() {


		CalculateBillRequest billRequest = new CalculateBillRequest();
		billRequest.setCustomerTenure(1);
		billRequest.setOrginalCurrency("USD");
		billRequest.setTargetCurrency("PKR");
		billRequest.setTotalAmount(BigDecimal.valueOf(10000000.0000));
		billRequest.setUserType("employee");

		Items item = new Items();
		item.setItems("Meat");
		item.setCategories(List.of("eatable", "grocery"));

		List<Items> itemList = new ArrayList<>();
		itemList.add(item);

		billRequest.setItems(itemList);

		Set<ConstraintViolation<CalculateBillRequest>> validInput = validator.validate(billRequest);
		assertFalse(validInput.isEmpty()); //validation successful  


	}
}
