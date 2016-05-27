package org.pode.cosmos.domain.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by patrick on 27.05.16.
 */
public class EmailValidator implements ConstraintValidator<Email, String>{

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "{validation.error.message.email}"
        ).addConstraintViolation();
        return false;
    }
}
