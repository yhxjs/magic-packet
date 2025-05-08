package com.yhxjs.magicpacket.validation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {IPValidator.class})
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IP {

    String message() default "非法的IP地址";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
