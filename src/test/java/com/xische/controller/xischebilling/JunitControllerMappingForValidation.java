package com.xische.controller.xischebilling;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xische.xischebilling.controller.CalculateDiscountController;
import com.xische.xischebilling.dto.ApiResponse;
import com.xische.xischebilling.dto.CalculateBillRequest;
import com.xische.xischebilling.dto.CalculateBillResponse;
import com.xische.xischebilling.dto.Items;
import com.xische.xischebilling.service.CalculateService;
import com.xische.xischebilling.util.Constants;


@ComponentScan(basePackages = "com.xische.xischebilling")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = JunitControllerMappingForValidation.class)
public class JunitControllerMappingForValidation {

	@Autowired
	MockMvc mockMvc;
	
	@Mock
	CalculateService calculcateServiceObj;

	@InjectMocks
	CalculateDiscountController calculateController;
	
	

	    @BeforeEach
	    public void setUp() {
	       mockMvc=MockMvcBuilders.standaloneSetup(calculateController).build();
	    }
	    
	@Test
	@Order(1)
	public void get_calculateAmount() throws Exception {
		
		
			CalculateBillRequest billRequest = new CalculateBillRequest();
			billRequest.setCustomerTenure(1);
			billRequest.setOrginalCurrency("USD");
			billRequest.setTargetCurrency("PKR");
			billRequest.setTotalAmount(BigDecimal.valueOf(100.00));
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
			
			HttpHeaders headers= new HttpHeaders();
			headers.add("Authorization", "Bearer eyJraWQiOiIxOTM1OWViNi0wYmM3LTQxZjQtODQ0OS1iYjkyM2Y1ZTk5YmMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjbGllbnQiLCJhdWQiOiJjbGllbnQiLCJuYmYiOjE3Mjc1MzI2MDcsInNjb3BlIjpbInJlYWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDoxMDAxIiwiZXhwIjoxNzI3NjE5MDA3LCJpYXQiOjE3Mjc1MzI2MDcsInVzZXIiOiJ1c2VyIiwianRpIjoiZjBiZWVmNjEtMzIwOC00OGZkLWFjOGQtNDBlZTY4ODI4M2YwIiwiYXV0aG9yaXRpZXMiOlsicmVhZCIsInRlc3QiXX0.teKgDQTWXvaD6s1sdYbCfZEwE-ldJQU-_9aLHRQ2wbQMHV2zwSG5HgVeq6CJ6312cLgQOZyl9PDhohkvu2w789deDeIN1K7gfAGV9vTe3lOMQqqZqzkI3uixbIkcAvfbZxDOElXKRM8IiPUpmK6PAvYPcUJ5t7jHOSRJNypEGvvGj9ViKRwEq5eCnnpTVjrsc8gbzUWl7F-RYtY0inY_j9ty9VETZNDPWc5ylQlGX02Bb5gZlfY9kebMZeQ8tWa25rp04Uy7ZuvNAT2jOOukcCLG-353ZvyWQcgNa8oK5a8m4Fi69HlxXNJkqUB_eFtqfaAffTAfv-YdRbXRclspDA");
			headers.add("Content-Type", "application/json");
			
			ObjectMapper mapper=  new ObjectMapper();
			String jsonReqBody=mapper.writeValueAsString(billRequest);
			this.mockMvc.perform(post("/api/calculate").headers(headers).content(jsonReqBody)).andDo(print());
	

	}

}
