package peaksoft.validation.passwordAnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PasswordValid.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordValidator {
    String message() default "Password length must be at lest 4 symbols";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
