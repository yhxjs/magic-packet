package com.yhxjs.magicpacket.validation.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MacValidator implements ConstraintValidator<MAC, String> {

    public static final Pattern MAC_PATTERN = Pattern.compile("[A-F0-9]{2}(-[A-F0-9]{2}){5}|[A-F0-9]{2}(:[A-F0-9]{2}){5}");

    @Override
    public void initialize(MAC constraintAnnotation) { // do nothing
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return MAC_PATTERN.matcher(value).matches();
    }
}
