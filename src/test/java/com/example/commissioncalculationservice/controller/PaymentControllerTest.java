package com.example.commissioncalculationservice.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.commissioncalculationservice.dto.CommissionDto;
import com.example.commissioncalculationservice.dto.PaymentDto;
import com.example.commissioncalculationservice.dto.UserDto;
import com.example.commissioncalculationservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PaymentController.class})
@ExtendWith(SpringExtension.class)
class PaymentControllerTest {
	@Autowired
	private PaymentController paymentController;

	@MockBean
	private PaymentService paymentService;

	@Test
	void testCreatePayment() throws Exception {
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
		Optional<PaymentDto> ofResult = Optional.of(paymentDto);
		when(this.paymentService.createPayment(any())).thenReturn(ofResult);

		CommissionDto commissionDto1 = new CommissionDto();
		commissionDto1.setId(123L);
		commissionDto1.setPaymentId(123L);
		commissionDto1.setTax(BigDecimal.valueOf(42L));

		UserDto userDto1 = new UserDto();
		userDto1.setFirstname("Jane");
		userDto1.setId(123L);
		userDto1.setLastname("Doe");
		userDto1.setPatronymic("Patronymic");
		userDto1.setPhoneNumbers(new HashSet<>());

		PaymentDto paymentDto1 = new PaymentDto();
		paymentDto1.setCommentByPayment("Comment By Payment");
		paymentDto1.setCommission(commissionDto1);
		paymentDto1.setDatePayment(null);
		paymentDto1.setId(123L);
		paymentDto1.setSumPayment(BigDecimal.valueOf(42L));
		paymentDto1.setUser(userDto1);
		String content = (new ObjectMapper()).writeValueAsString(paymentDto1);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/payment/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		MockMvcBuilders.standaloneSetup(this.paymentController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content()
						.string(
								"{\"id\":123,\"sumPayment\":42,\"datePayment\":[1970,1,2],\"commentByPayment\":\"Comment By Payment\",\"user\":{"
										+ "\"id\":123,\"lastname\":\"Doe\",\"firstname\":\"Jane\",\"patronymic\":\"Patronymic\",\"phoneNumbers\":[]},\"commission"
										+ "\":{\"id\":123,\"tax\":42,\"paymentId\":123}}"));
	}

	@Test
	void testCreatePayment2() throws Exception {
		when(this.paymentService.createPayment(any())).thenReturn(Optional.empty());

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
		paymentDto.setDatePayment(null);
		paymentDto.setId(123L);
		paymentDto.setSumPayment(BigDecimal.valueOf(42L));
		paymentDto.setUser(userDto);
		String content = (new ObjectMapper()).writeValueAsString(paymentDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/payment/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.paymentController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("????? ?????? ???? ?????? 42"));
	}

	@Test
	void testGetCommission2() throws Exception {
		when(this.paymentService.getCommission((BigDecimal) any())).thenReturn(BigDecimal.valueOf(42L));
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/payment/calculate/commission");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("sum", String.valueOf((Object) 42));
		MockMvcBuilders.standaloneSetup(this.paymentController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("42"));
	}

	@Test
	void testGetCommission3() throws Exception {
		when(this.paymentService.getCommission((BigDecimal) any())).thenReturn(BigDecimal.valueOf(42L));
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/payment/calculate/commission");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("sum", String.valueOf((Object) 0));
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.paymentController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("????? ?????? ???? ?????? 0"));
	}

	@Test
	void testGetCommission4() throws Exception {
		when(this.paymentService.getCommission(any())).thenReturn(BigDecimal.valueOf(42L));
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/payment/calculate/commission");
		getResult.contentType("https://example.org/example");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("sum", String.valueOf((Object) 42));
		MockMvcBuilders.standaloneSetup(this.paymentController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("42"));
	}

	@Test
	void testGetCommission() throws Exception {
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/payment/calculate/commission");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("sum", String.valueOf((Object) null));
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.paymentController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
	}
}

