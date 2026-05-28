package org.example.lmsplatform.exception;

import lombok.experimental.StandardException;

@StandardException
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Студент не найден с id: " + id);
    }
}
