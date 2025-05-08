package com.yhxjs.magicpacket.validation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {MacValidator.class})
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MAC {

    String message() default "非法的MAC地址";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
