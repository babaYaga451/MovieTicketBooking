package com.movieTicketBooking.movieTicketBooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTicketIdException extends RuntimeException {
    public InvalidTicketIdException(String message) {
        super(message);
    }
}