package org.id2k1149.dinerVoting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Can't do it. There were votes today.")
public class CounterException extends RuntimeException {
    public CounterException(String msg) {
        super(msg);
    }
}
