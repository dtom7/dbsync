package com.example.dbsync.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class ListValidator implements ConstraintValidator<ValidListOfString, List<String>> {

	private boolean required;

	@Override
	public void initialize(ValidListOfString validList) {
		required = validList.required();
	}

	@Override
	public boolean isValid(List<String> list, ConstraintValidatorContext constraintValidatorContext) {
		if (list != null && list.size() > 0) {
			for (String item : list) {
				if (StringUtils.isEmpty(item)) {
					return false;
				}
			}
			return true;
		} else {
			if (required) {
				return false;
			} else {
				return true;
			}
		}
	}

}
