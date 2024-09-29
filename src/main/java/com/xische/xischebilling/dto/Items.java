package com.xische.xischebilling.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Items implements Serializable {
	
	

		/**
	 * 
	 */
	private static final long serialVersionUID = 7794557318664919470L;
		List<String> categories;
		String items;
	}
