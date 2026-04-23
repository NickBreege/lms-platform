package org.example.lms_platform.exception;

import lombok.experimental.StandardException;

@StandardException
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Не найден студент с id: " + id);
    }
}
