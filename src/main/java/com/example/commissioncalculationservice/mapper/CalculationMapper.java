package com.example.commissioncalculationservice.mapper;

import com.example.commissioncalculationservice.dto.CommissionDto;
import com.example.commissioncalculationservice.dto.PaymentDto;
import com.example.commissioncalculationservice.dto.PhoneNumberDto;
import com.example.commissioncalculationservice.dto.UserDto;
import com.example.commissioncalculationservice.entity.Commission;
import com.example.commissioncalculationservice.entity.Payment;
import com.example.commissioncalculationservice.entity.PhoneNumber;
import com.example.commissioncalculationservice.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class CalculationMapper {

	@Mapping(target = "datePayment", ignore = true)
	public abstract Payment toPayment(PaymentDto paymentDto);
	public abstract PaymentDto toPaymentDto(Payment payment);

	public abstract CommissionDto toCommissionDto(Commission commission);
	@Mapping(target = "payment", ignore = true)
	@Mapping(source ="paymentId", target = "payment.id")
	public abstract Commission toCommission(CommissionDto commissionDto);

	public abstract UserDto toUserDto(User user);
	public abstract User toUser(UserDto userDto);

	public abstract PhoneNumberDto toPhoneNumberDto(PhoneNumber phoneNumber);
	public abstract Set<PhoneNumberDto> toPhoneNumberDtos(Set<PhoneNumber> phoneNumber);
	public abstract PhoneNumber toPhoneNumber(PhoneNumberDto phoneNumberDto);
	public abstract Set<PhoneNumber> toPhoneNumbers(Set<PhoneNumberDto> phoneNumberDto);


	@AfterMapping
	public void setDatePayment(@MappingTarget Payment payment) {
		payment.setDatePayment(LocalDate.now());
	}

	public void setCommission(Payment payment, BigDecimal tax) {
		Commission commission = new Commission();
		commission.setTax(tax);
		commission.setPayment(payment);
		payment.setCommission(commission);
	}
}