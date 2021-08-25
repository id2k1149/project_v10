package org.id2k1149.dinerVoting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Time is over for voting")
public class TimeException extends RuntimeException {
    public TimeException(String msg) {
        super(msg);
    }
}