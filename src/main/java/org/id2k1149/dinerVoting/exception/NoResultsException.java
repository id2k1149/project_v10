package org.id2k1149.dinerVoting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "There are no votes today.")
public class NoResultsException extends RuntimeException {
    public NoResultsException(String msg) {
        super(msg);
    }
}
