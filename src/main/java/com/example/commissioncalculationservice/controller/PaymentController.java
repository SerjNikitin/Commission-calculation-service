package com.example.commissioncalculationservice.controller;

import com.example.commissioncalculationservice.massage.ResponseMassage;
import com.example.commissioncalculationservice.dto.PaymentDto;
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

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService paymentService;

	@PostMapping("/create")
	public ResponseEntity<?> createPayment(@RequestBody PaymentDto paymentDto) {
		return ResponseEntity.status(HttpStatus.OK).body(paymentService.createPayment(paymentDto));
	}

	@GetMapping("/calculate/commission")
	public ResponseEntity<?> getCommission(@RequestParam BigDecimal sum) {
		try {
			BigDecimal commission = paymentService.getCommission(sum);
			return ResponseEntity.status(HttpStatus.OK).body(commission);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMassage.SOMETHING_WENT_WRONG);
		}
	}
}
