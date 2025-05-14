package com.yhxjs.magicpacket.validation.validator;

import com.yhxjs.magicpacket.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IPValidator implements ConstraintValidator<IP, String> {

    @Override
    public void initialize(IP constraintAnnotation) { // do nothing
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        String[] t = value.split("\\.", -1);
        if (t.length != 4) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (t[i].isEmpty() || t[i].length() > 3 || t[i].length() > 1 && t[i].charAt(0) == '0') {
                return false;
            }
            int sum = 0;
            for (char c : t[i].toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
                sum = sum * 10 + c - '0';
            }
            if (sum > 255) {
                return false;
            }
        }
        return true;
    }
}
