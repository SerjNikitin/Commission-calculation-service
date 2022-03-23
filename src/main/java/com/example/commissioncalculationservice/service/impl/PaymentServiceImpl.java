package com.example.commissioncalculationservice.service.impl;

import com.example.commissioncalculationservice.dto.PaymentDto;
import com.example.commissioncalculationservice.entity.Payment;
import com.example.commissioncalculationservice.mapper.CalculationMapper;
import com.example.commissioncalculationservice.repository.PaymentRepository;
import com.example.commissioncalculationservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
	private final PaymentRepository paymentRepository;
	private final CalculationMapper mapperCalculation;

	@Override
	@Transactional
	public PaymentDto createPayment(PaymentDto paymentDto) {
		Payment payment = mapperCalculation.toPayment(paymentDto);
		BigDecimal commission = createCommission(payment.getSumPayment());
		payment.setSumPayment(payment.getSumPayment().subtract(commission));
		mapperCalculation.setCommission(payment, commission);
		return mapperCalculation.toPaymentDto(paymentRepository.save(payment));
	}

	@Override
	public BigDecimal getCommission(BigDecimal sum) {
		return createCommission(sum);
	}

	private BigDecimal createCommission(BigDecimal sum) {
		BigDecimal tax = null;
		if (sum.compareTo(BigDecimal.valueOf(10000)) <= 0 && sum.compareTo(BigDecimal.valueOf(0)) > 0) {
			tax = sum.divide(BigDecimal.valueOf(100));
		} else if (sum.compareTo(BigDecimal.valueOf(100000)) <= 0 && sum.compareTo(BigDecimal.valueOf(10000)) > 0) {
			tax = sum.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(3));
		} else if (sum.compareTo(BigDecimal.valueOf(100000)) >= 1) {
			tax = sum.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(5));
		}
		assert false;
		return tax.setScale(2, RoundingMode.DOWN);
	}
}
