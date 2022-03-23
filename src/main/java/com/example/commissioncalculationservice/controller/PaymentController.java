package com.example.commissioncalculationservice.controller;

import com.example.commissioncalculationservice.dto.PaymentDto;
import com.example.commissioncalculationservice.massage.ResponseMassage;
import com.example.commissioncalculationservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService paymentService;

	@PostMapping("/create")
	public ResponseEntity<?> createPayment(@RequestBody PaymentDto paymentDto) {
		Optional<PaymentDto> payment = paymentService.createPayment(paymentDto);
		if (payment.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(payment.get());
		} else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format(ResponseMassage.AMOUNT_MUST_BE_GREATER, paymentDto.getSumPayment()));
	}

	@GetMapping("/calculate/commission")
	public ResponseEntity<?> getCommission(@RequestParam BigDecimal sum) {
		try {
			if (sum.compareTo(BigDecimal.valueOf(0)) <= 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format(ResponseMassage.AMOUNT_MUST_BE_GREATER, sum));
			} else {
				BigDecimal commission = paymentService.getCommission(sum);
				return ResponseEntity.status(HttpStatus.OK).body(commission);
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMassage.SOMETHING_WENT_WRONG);
		}
	}
}