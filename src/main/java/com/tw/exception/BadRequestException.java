package com.tw.exception;

public class BadRequestException extends RuntimeException{
    private Integer code;
    private String message;

    public BadRequestException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BadRequestException() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
