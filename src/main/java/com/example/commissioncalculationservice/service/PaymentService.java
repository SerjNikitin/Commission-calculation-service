package com.example.commissioncalculationservice.service;

import com.example.commissioncalculationservice.dto.PaymentDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentService {
	Optional<PaymentDto> createPayment(PaymentDto paymentDto);
	BigDecimal getCommission(BigDecimal sum);
}
