package com.example.demo.Status.Exception;

import com.example.demo.Status.GlobalException;

public class UnauthorizedException extends GlobalException {
    public UnauthorizedException(String message, int code) {
        super(message, code);
    }
}
