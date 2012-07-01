package com.tvelykyy.afeeder.domain.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tvelykyy.afeeder.domain.Activity;

public class ActivityValidator implements Validator {

	public boolean supports(Class<?> aClass) {
		return Activity.class.equals(aClass);
	}

	public void validate(Object object, Errors errors) {
		Activity activity = (Activity) object;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "field.required", "Text can't be blank");
		if ( !errors.hasFieldErrors("text")) {
			if (activity.getText().length() > 200) {
				errors.rejectValue("text", "field.too_long", "Text length should be less than 200 chars");
			}
		}
		
		//Group validation
		if (activity.getGroup() == null) {
			errors.rejectValue("group", "field.cant_be_null", "Activity should have group assigned");
		}
		
		//User validation
		if (activity.getUser() == null) {
			errors.rejectValue("user", "field.cant_be_null", "Activity should have user assigned");
		}
	}

}