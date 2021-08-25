package org.id2k1149.dinerVoting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Someone already has this name")
public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg) {
        super(msg);
    }
}