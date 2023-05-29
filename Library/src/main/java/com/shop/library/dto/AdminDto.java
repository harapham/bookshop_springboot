package com.shop.library.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AdminDto {
	@Size(min = 3, max = 10, message = "firstName phải có từ 3 đến 10 ký tự")
	@Length
	private String firstName;

	@Size(min = 3, max = 10, message = "lastName phải có từ 3 đến 10 ký tự")
	private String lastName;

	private String username;

	@Size(min = 5, max = 15, message = "password phải có từ 5 đến 15 ký tự")
	private String password;
	
	private String repeatPassword;

}
