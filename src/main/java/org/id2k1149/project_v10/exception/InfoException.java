package org.id2k1149.project_v10.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Can't add, need to edit")
public class InfoException extends RuntimeException {
    public InfoException(String msg) {
        super(msg);
    }
}
