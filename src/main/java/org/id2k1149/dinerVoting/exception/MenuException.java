package org.id2k1149.dinerVoting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This diner has a menu for today")
public class MenuException extends RuntimeException {
    public MenuException(String msg) {
        super(msg);
    }
}
