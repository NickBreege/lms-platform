package org.example.lmsplatform.exception;

import lombok.experimental.StandardException;

@StandardException
public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException(Long id) {
        super("Группа не найдена по id: " + id);
    }
}
