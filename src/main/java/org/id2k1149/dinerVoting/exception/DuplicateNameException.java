package org.id2k1149.dinerVoting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Someone already has this name")
public class DuplicateNameException extends RuntimeException{
    public DuplicateNameException(String msg) {
        super(msg);
    }
}