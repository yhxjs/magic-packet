package com.yhxjs.magicpacket.exception;

import com.yhxjs.magicpacket.controller.Code;

public class AccessIllegalException extends ServiceException {

    public AccessIllegalException() {
        super(Code.ACCESS_ILLEGAL);
    }
}
