package org.example.lmsplatform.exception;

import lombok.experimental.StandardException;

@StandardException
public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(Long teacherId) {
        super("Учитель не найден с id: " + teacherId);
    }
}
