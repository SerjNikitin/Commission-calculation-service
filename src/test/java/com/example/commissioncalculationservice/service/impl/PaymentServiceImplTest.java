package com.example.commissioncalculationservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.commissioncalculationservice.dto.CommissionDto;
import com.example.commissioncalculationservice.dto.PaymentDto;
import com.example.commissioncalculationservice.dto.UserDto;
import com.example.commissioncalculationservice.entity.Commission;
import com.example.commissioncalculationservice.entity.Payment;
import com.example.commissioncalculationservice.entity.User;
import com.example.commissioncalculationservice.mapper.CalculationMapper;
import com.example.commissioncalculationservice.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {PaymentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PaymentServiceImplTest {
	@MockBean
	private CalculationMapper calculationMapper;

	@MockBean
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentServiceImpl paymentServiceImpl;

	@Test
	void testCreatePayment2() {
		Payment payment = new Payment();
		payment.setCommentByPayment("Comment By Payment");
		payment.setCommission(new Commission());
		payment.setDatePayment(null);
		payment.setId(123L);
		payment.setSumPayment(null);
		payment.setUser(new User());

		Commission commission = new Commission();
		commission.setId(123L);
		commission.setPayment(payment);
		commission.setTax(BigDecimal.valueOf(42L));

		User user = new User();
		user.setFirstname("Jane");
		user.setId(123L);
		user.setLastname("Doe");
		user.setPatronymic("Patronymic");
		user.setPhoneNumbers(new HashSet<>());

		Payment payment1 = new Payment();
		payment1.setCommentByPayment("Comment By Payment");
		payment1.setCommission(commission);
		payment1.setDatePayment(LocalDate.ofEpochDay(1L));
		payment1.setId(123L);
		payment1.setSumPayment(BigDecimal.valueOf(42L));
		payment1.setUser(user);

		Commission commission1 = new Commission();
		commission1.setId(123L);
		commission1.setPayment(payment1);
		commission1.setTax(BigDecimal.valueOf(42L));

		User user1 = new User();
		user1.setFirstname("Jane");
		user1.setId(123L);
		user1.setLastname("Doe");
		user1.setPatronymic("Patronymic");
		user1.setPhoneNumbers(new HashSet<>());

		Payment payment2 = new Payment();
		payment2.setCommentByPayment("Comment By Payment");
		payment2.setCommission(commission1);
		payment2.setDatePayment(LocalDate.ofEpochDay(1L));
		payment2.setId(123L);
		payment2.setSumPayment(BigDecimal.valueOf(42L));
		payment2.setUser(user1);
		when(this.paymentRepository.save((Payment) any())).thenReturn(payment2);

		Payment payment3 = new Payment();
		payment3.setCommentByPayment("Comment By Payment");
		payment3.setCommission(new Commission());
		payment3.setDatePayment(null);
		payment3.setId(123L);
		payment3.setSumPayment(null);
		payment3.setUser(new User());

		Commission commission2 = new Commission();
		commission2.setId(123L);
		commission2.setPayment(payment3);
		commission2.setTax(BigDecimal.valueOf(42L));

		User user2 = new User();
		user2.setFirstname("Jane");
		user2.setId(123L);
		user2.setLastname("Doe");
		user2.setPatronymic("Patronymic");
		user2.setPhoneNumbers(new HashSet<>());

		Payment payment4 = new Payment();
		payment4.setCommentByPayment("Comment By Payment");
		payment4.setCommission(commission2);
		payment4.setDatePayment(LocalDate.ofEpochDay(1L));
		payment4.setId(123L);
		payment4.setSumPayment(BigDecimal.valueOf(42L));
		payment4.setUser(user2);

		Commission commission3 = new Commission();
		commission3.setId(123L);
		commission3.setPayment(payment4);
		commission3.setTax(BigDecimal.valueOf(42L));

		User user3 = new User();
		user3.setFirstname("Jane");
		user3.setId(123L);
		user3.setLastname("Doe");
		user3.setPatronymic("Patronymic");
		user3.setPhoneNumbers(new HashSet<>());

		Payment payment5 = new Payment();
		payment5.setCommentByPayment("Comment By Payment");
		payment5.setCommission(commission3);
		payment5.setDatePayment(LocalDate.ofEpochDay(1L));
		payment5.setId(123L);
		payment5.setSumPayment(BigDecimal.valueOf(42L));
		payment5.setUser(user3);
		when(this.calculationMapper.toPaymentDto((Payment) any())).thenThrow(new RuntimeException("An error occurred"));
		doThrow(new RuntimeException("An error occurred")).when(this.calculationMapper)
				.setCommission((Payment) any(), (BigDecimal) any());
		when(this.calculationMapper.toPayment((PaymentDto) any())).thenReturn(payment5);

		CommissionDto commissionDto = new CommissionDto();
		commissionDto.setId(123L);
		commissionDto.setPaymentId(123L);
		commissionDto.setTax(BigDecimal.valueOf(42L));

		UserDto userDto = new UserDto();
		userDto.setFirstname("Jane");
		userDto.setId(123L);
		userDto.setLastname("Doe");
		userDto.setPatronymic("Patronymic");
		userDto.setPhoneNumbers(new HashSet<>());

		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setCommentByPayment("Comment By Payment");
		paymentDto.setCommission(commissionDto);
		paymentDto.setDatePayment(LocalDate.ofEpochDay(1L));
		paymentDto.setId(123L);
		paymentDto.setSumPayment(BigDecimal.valueOf(42L));
		paymentDto.setUser(userDto);
		assertThrows(RuntimeException.class, () -> this.paymentServiceImpl.createPayment(paymentDto));
		verify(this.calculationMapper).toPayment((PaymentDto) any());
		verify(this.calculationMapper).setCommission((Payment) any(), (BigDecimal) any());
	}

	@Test
	void testGetCommission() {
		assertEquals("0.42", this.paymentServiceImpl.getCommission(BigDecimal.valueOf(42L)).toString());
		assertThrows(RuntimeException.class, () -> this.paymentServiceImpl.getCommission(BigDecimal.valueOf(-1L)));
		assertEquals("461168601842738790.35",
				this.paymentServiceImpl.getCommission(BigDecimal.valueOf(Long.MAX_VALUE)).toString());
	}

	@Test
	void testCreateCommission() {
		assertEquals("0.42", this.paymentServiceImpl.createCommission(BigDecimal.valueOf(42L)).toString());
		assertThrows(RuntimeException.class, () -> this.paymentServiceImpl.createCommission(BigDecimal.valueOf(-1L)));
		assertEquals("461168601842738790.35",
				this.paymentServiceImpl.createCommission(BigDecimal.valueOf(Long.MAX_VALUE)).toString());
	}
}

