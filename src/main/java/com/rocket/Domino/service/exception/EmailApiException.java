package com.rocket.Domino.service.exception;

public class EmailApiException extends RuntimeException{
    public EmailApiException(){
        super("Error sending mail ...");
    }
}
