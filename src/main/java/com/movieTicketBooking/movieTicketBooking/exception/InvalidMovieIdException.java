package com.movieTicketBooking.movieTicketBooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMovieIdException extends RuntimeException {
    public InvalidMovieIdException(String message) {
        super(message);
    }
}