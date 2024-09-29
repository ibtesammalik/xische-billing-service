package com.xische.xischebilling.dto;

import lombok.Data;

@Data

public class ExchangeRateResponse {
	public String result;
	public String documentation;
	public String terms_of_use;
	public int time_last_update_unix;
	public String time_last_update_utc;
	public int time_next_update_unix;
	public String time_next_update_utc;
	public String base_code;
	public ConversionRates conversion_rates;
	
	

}
