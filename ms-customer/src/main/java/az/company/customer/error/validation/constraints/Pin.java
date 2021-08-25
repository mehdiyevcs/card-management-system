package az.company.customer.error.validation.constraints;

import az.company.customer.error.validation.ValidationMessage;
import az.company.customer.error.validation.ValidationPattern;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Pattern(regexp = ValidationPattern.PIN, message = ValidationMessage.PIN)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface Pin {
    String message() default ValidationMessage.PIN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
