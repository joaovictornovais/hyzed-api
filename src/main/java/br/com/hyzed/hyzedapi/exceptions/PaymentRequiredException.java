package br.com.hyzed.hyzedapi.exceptions;

public class PaymentRequiredException extends RuntimeException {

    public PaymentRequiredException(String message) {
        super(message);
    }
}
