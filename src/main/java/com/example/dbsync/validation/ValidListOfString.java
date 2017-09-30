package com.example.dbsync.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {ListValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ValidListOfString {
	String message() default "List should not be null and should contain non blank string(s)";
	boolean required() default true;
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
