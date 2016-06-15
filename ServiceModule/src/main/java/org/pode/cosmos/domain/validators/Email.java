package org.pode.cosmos.domain.validators;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;

/**
 * Created by patrick on 27.05.16.
 */
@Constraint(validatedBy = EmailValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface Email {

    String message() default "No valid email address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List{
        Email[] value();
    }

}
