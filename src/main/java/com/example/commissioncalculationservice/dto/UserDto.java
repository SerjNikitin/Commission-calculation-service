package com.example.commissioncalculationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
	private Long id;
	private String lastname;
	private String firstname;
	private String patronymic;
	private Set<PhoneNumberDto> phoneNumbers;
}
