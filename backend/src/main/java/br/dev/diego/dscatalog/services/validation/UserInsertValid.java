package br.dev.diego.dscatalog.services.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {UserInsertValidator.class})
@Target(TYPE)
@Retention(RUNTIME)
public @interface UserInsertValid {

    String message() default "Validation Error";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}