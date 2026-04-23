package org.example.lms_platform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.lms_platform.model.entity.Student;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupDtoRq {
    private String name;
    private List<Student> students;
}
