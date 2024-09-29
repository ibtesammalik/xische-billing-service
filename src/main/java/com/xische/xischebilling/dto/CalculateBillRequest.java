package com.xische.xischebilling.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalculateBillRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2901941032648864412L;
	@NotNull(message = "List of Item can not be null")
	private List<Items> items;
	@NotNull(message = "Amount can not be null")
	@Digits(message= "Amount must be in number", fraction = 2, integer = 6)
	private BigDecimal totalAmount;
	@NotNull(message = "user type can't not null")
	private String userType;
	@NotNull(message = "customerTenure can not be null")
	private int customerTenure;
	@NotNull(message = "orginalCurrency can not be null")

	@Size(max = 3, message = "Name should have at most 3 characters")
	@Pattern(regexp = "^[A-Z].*$", message = "Name should All Upper Case ")
	private String orginalCurrency;
	@NotNull(message = "targetCurrency can not be null")
	@Size(max = 3, message = "Name should have at most 3 characters")
	@Pattern(regexp = "^[A-Z].*$", message = "Name should All Upper Case ")
	private String targetCurrency;
}


