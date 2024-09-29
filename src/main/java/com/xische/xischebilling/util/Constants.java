package com.xische.xischebilling.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public enum Errors {
		ERR_INVALID_REQUEST(2700068, "Invalid Request."),
		ERR_CONNECTIVITY_ERROR(3000100, "Sorry, there seems to be an With exchange Rate Api. Please try again after a few minutes.");
		private static final Map<Integer, String> errorsMap = new HashMap<>();
		private Errors(int code, String shortDesc) {
			this.code = code;
			this.shortDesc = shortDesc;
		}
		private Integer code;
		private String shortDesc;
	
		public Integer getCode() {
			return this.code;
		}

		public String getDescription() {
			return this.shortDesc;
		}
		static {
			for (Errors t : Errors.values()) {
				errorsMap.put(t.code, t.shortDesc);
			}
		}
		public static String getMessageByCode(Integer code) {
			return errorsMap.get(code);
		}
		
	}
	
	public enum SuccessResponses {
		VALID_REQUEST(00, "Valid Request.");
		private SuccessResponses(int code, String shortDesc) {
			this.code = code;
			this.shortDesc = shortDesc;
		}
		private Integer code;
		private String shortDesc;
	
		public Integer getCode() {
			return this.code;
		}

		public String getDescription() {
			return this.shortDesc;
		}

		
	}
}
