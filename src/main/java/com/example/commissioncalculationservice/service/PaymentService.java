package com.example.commissioncalculationservice.service;

import com.example.commissioncalculationservice.dto.PaymentDto;

import java.math.BigDecimal;

public interface PaymentService {
	PaymentDto createPayment(PaymentDto paymentDto);
	BigDecimal getCommission(BigDecimal sum);
}
