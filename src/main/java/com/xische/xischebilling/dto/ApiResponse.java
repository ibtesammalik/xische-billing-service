package com.xische.xischebilling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor

public class ApiResponse<T> {
    private Integer statusCode;
    private String statusDescription;
    private T result;
}
