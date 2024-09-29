package com.xische.xischebilling.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalculateBillResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2901941032648864412L;
	
	private BigDecimal totalAmount;
	private String orginalCurrency;
	private String targetCurrency;
	private String conversionAmount;
}


