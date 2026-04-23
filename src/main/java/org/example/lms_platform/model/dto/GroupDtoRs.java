package org.example.lms_platform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.lms_platform.model.entity.Student;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupDtoRs {
    private Long id;
    private String name;
    private List<Student> students;
}
