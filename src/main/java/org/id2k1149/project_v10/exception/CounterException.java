package org.id2k1149.project_v10.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "There are votes today.")
public class CounterException extends RuntimeException {
    public CounterException(String msg) {
        super(msg);
    }
}
