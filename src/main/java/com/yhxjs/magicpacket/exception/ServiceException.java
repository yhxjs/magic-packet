package com.yhxjs.magicpacket.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ServiceException extends RuntimeException {

    private final Integer code;

    @Setter
    private String message;

    public ServiceException(Integer code) {
        this.code = code;
    }

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
