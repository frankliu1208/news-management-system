package org.example.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.StateValidation;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = {StateValidation.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface State {

    String message() default "the value of state can only be released or draft";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
