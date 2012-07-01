package com.tvelykyy.afeeder.domain.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tvelykyy.afeeder.domain.User;

public class UserValidator implements Validator {

	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	public void validate(Object object, Errors errors) {
		User user = (User) object;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "Name can't be blank");
		if ( !errors.hasFieldErrors("name")) {
			if (user.getName().length() > 20) {
				errors.rejectValue("text", "field.too_long", "Name length should be less than 20 chars");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "field.required", "Login can't be blank");
		if ( !errors.hasFieldErrors("login")) {
			if (user.getLogin().length() < 4 || user.getLogin().length() > 8) {
				errors.rejectValue("login", "field.incorrect_length", "Login length should between 4 and 8 chars");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "Password can't be blank");
		if ( !errors.hasFieldErrors("password")) {
			if (user.getPassword().length() < 4 || user.getPassword().length() > 8) {
				errors.rejectValue("password", "field.incorrect_length", "Password length should between 4 and 8 chars");
			}
		}
	}

}