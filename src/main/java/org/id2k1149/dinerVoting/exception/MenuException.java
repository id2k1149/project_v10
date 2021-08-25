package org.id2k1149.dinerVoting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Can't add, need to edit")
public class MenuException extends RuntimeException {
    public MenuException(String msg) {
        super(msg);
    }
}
