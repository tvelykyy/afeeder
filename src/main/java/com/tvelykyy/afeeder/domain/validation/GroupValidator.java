package com.tvelykyy.afeeder.domain.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tvelykyy.afeeder.domain.Group;

public class GroupValidator implements Validator {

	public boolean supports(Class<?> aClass) {
		return Group.class.equals(aClass);
	}

	public void validate(Object object, Errors errors) {
		Group group = (Group) object;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "Name can't be blank");
		if ( !errors.hasFieldErrors("name")) {
			if (group.getName().length() > 50) {
				errors.rejectValue("name", "field.too_long", "Name length should be less than 50 chars");
			}
		}
	}

}