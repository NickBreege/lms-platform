package org.example.lmsplatform.exception;

import lombok.experimental.StandardException;

@StandardException
public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long courseId) {
        super("Курс не найден по id: " + courseId);
    }
}
