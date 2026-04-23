package org.example.lms_platform.service;

import lombok.RequiredArgsConstructor;
import org.example.lms_platform.dao.GroupRepository;
import org.example.lms_platform.dao.StudentRepository;
import org.example.lms_platform.exception.GroupNotFoundException;
import org.example.lms_platform.exception.StudentNotFoundException;
import org.example.lms_platform.model.dto.StudentDtoRq;
import org.example.lms_platform.model.dto.StudentDtoRs;
import org.example.lms_platform.model.entity.Group;
import org.example.lms_platform.model.entity.Student;
import org.example.lms_platform.model.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;

    public StudentDtoRs createStudent(StudentDtoRq studentDtoRq) {
        Student student = new Student();
        fillStudentFromDto(student, studentDtoRq);

        Student saved = studentRepository.save(student);
        return studentMapper.toDtoRs(saved);
    }

    @Transactional
    public StudentDtoRs updateStudent(Long id, StudentDtoRq studentDtoRq) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        fillStudentFromDto(student, studentDtoRq);

        Student saved = studentRepository.save(student);
        return studentMapper.toDtoRs(saved);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);
    }

    @Transactional
    public StudentDtoRs assignStudentToGroup(Long studentId, Long groupId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        Group group = getGroupOrThrow(groupId);
        student.setGroup(group);

        Student saved = studentRepository.save(student);
        return studentMapper.toDtoRs(saved);
    }


    @Transactional
    public List<StudentDtoRs> assignStudentsToGroup(List<Long> studentIds, Long groupId) {
        if (studentIds == null || studentIds.isEmpty()) {
            throw new IllegalArgumentException("Лист студентов пуст");
        }

        Group group = getGroupOrThrow(groupId);
        List<Student> students = studentRepository.findAllById(studentIds);

        if (students.size() != studentIds.size()) {
            throw new StudentNotFoundException("Некоторые студенты не найдены");
        }

        for (Student student : students) {
            student.setGroup(group);
        }

        List<Student> saved = studentRepository.saveAll(students);
        return saved.stream().map(studentMapper::toDtoRs).toList();
    }


    private void fillStudentFromDto(Student student, StudentDtoRq studentDtoRq) {
        student.setName(studentDtoRq.getName());
        student.setSurname(studentDtoRq.getSurname());
        setGroupIfPresentOrThrow(student, studentDtoRq.getGroupId());
    }

    private void setGroupIfPresentOrThrow(Student student, Long groupId) {
        if (groupId != null) {
            Group group = getGroupOrThrow(groupId);
            student.setGroup(group);
        }
    }

    private Group getGroupOrThrow(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

}