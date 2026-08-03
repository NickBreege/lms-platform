package org.example.lmsplatform.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException(Long id) {
        super("Группа не найдена по id: " + id);
    }
}
