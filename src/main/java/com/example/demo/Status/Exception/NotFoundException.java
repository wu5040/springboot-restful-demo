package com.example.demo.Status.Exception;


import com.example.demo.Status.GlobalException;

public class NotFoundException extends GlobalException {
    public NotFoundException(String message, int code) {
        super(message, code);
    }

}
