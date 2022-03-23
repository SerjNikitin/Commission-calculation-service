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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
	private final PaymentRepository paymentRepository;
	private final CalculationMapper mapperCalculation;

	@Override
	@Transactional
	public Optional<PaymentDto> createPayment(PaymentDto paymentDto) {
		Payment payment = mapperCalculation.toPayment(paymentDto);
		BigDecimal sumPayment = payment.getSumPayment();
		if (sumPayment != null) {
			BigDecimal commission = createCommission(sumPayment);
			payment.setSumPayment(sumPayment.subtract(commission));
			mapperCalculation.setCommission(payment, commission);
			return Optional.of(mapperCalculation.toPaymentDto(paymentRepository.save(payment)));
		} else return Optional.empty();
	}

	@Override
	public BigDecimal getCommission(BigDecimal sum) {
		return createCommission(sum);
	}

	public BigDecimal createCommission(BigDecimal sum) {
		if (sum.compareTo(BigDecimal.valueOf(0)) <= 0) {
			throw new RuntimeException("Сумма должна быть больше 0");
		}
		BigDecimal tax = null;
		if (sum.compareTo(BigDecimal.valueOf(10000)) <= 0 && sum.compareTo(BigDecimal.valueOf(0)) > 0) {
			tax = sum.divide(BigDecimal.valueOf(100));
		} else if (sum.compareTo(BigDecimal.valueOf(100000)) <= 0 && sum.compareTo(BigDecimal.valueOf(10000)) > 0) {
			tax = sum.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(3));
		} else if (sum.compareTo(BigDecimal.valueOf(100000)) >= 1) {
			tax = sum.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(5));
		}
		return tax.setScale(2, RoundingMode.DOWN);
	}
}
