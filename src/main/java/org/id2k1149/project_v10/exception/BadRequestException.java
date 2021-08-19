package org.id2k1149.project_v10.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong request")
public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg) {
        super(msg);
    }
}