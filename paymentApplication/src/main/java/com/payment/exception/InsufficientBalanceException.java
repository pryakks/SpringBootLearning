package com.payment.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String errMsg){
        super(errMsg);
    }
}
