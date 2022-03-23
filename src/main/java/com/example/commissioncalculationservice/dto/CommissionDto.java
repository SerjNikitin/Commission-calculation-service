package com.example.commissioncalculationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CommissionDto {
	private Long id;
	private BigDecimal tax;
	private Long paymentId;
}
