package com.example.commissioncalculationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentDto {
	private Long id;
	private BigDecimal sumPayment;
	private LocalDate datePayment;
	private String commentByPayment;
	private UserDto user;
	private CommissionDto commission;

}
