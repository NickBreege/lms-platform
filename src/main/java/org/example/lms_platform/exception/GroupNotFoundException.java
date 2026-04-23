package org.example.lms_platform.exception;

import lombok.experimental.StandardException;

@StandardException
public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException(Long id) {
        super("Группа не найдена по данному id: " + id);
    }
}
