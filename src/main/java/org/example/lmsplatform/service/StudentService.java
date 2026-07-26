package org.example.lmsplatform.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.dao.GroupRepository;
import org.example.lmsplatform.dao.StudentRepository;
import org.example.lmsplatform.exception.GroupNotFoundException;
import org.example.lmsplatform.exception.StudentNotFoundException;
import org.example.lmsplatform.model.dto.StudentDtoRequest;
import org.example.lmsplatform.model.dto.StudentDtoResponse;
import org.example.lmsplatform.model.entity.Student;
import org.example.lmsplatform.model.entity.StudentGroup;
import org.example.lmsplatform.model.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public StudentDtoResponse createStudent(StudentDtoRequest studentDtoRequest) {
        Student student = new Student();
        fillStudentFromDto(student, studentDtoRequest);

        return studentMapper.toDtoResponse(studentRepository.save(student));
    }

    @Transactional
    public StudentDtoResponse getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        return studentMapper.toDtoResponse(student);
    }

    @Transactional
    public StudentDtoResponse updateStudent(Long studentId, StudentDtoRequest studentDtoRequest) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        fillStudentFromDto(student, studentDtoRequest);

        return studentMapper.toDtoResponse(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        studentRepository.delete(student);
    }

    @Transactional
    public StudentDtoResponse assignStudentToGroup(Long studentId, Long groupId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        StudentGroup studentGroup = getGroupOrThrow(groupId);
        student.setStudentGroup(studentGroup);

        Student saved = studentRepository.save(student);
        return studentMapper.toDtoResponse(saved);
    }


    @Transactional
    public List<StudentDtoResponse> assignStudentsToGroup(List<Long> studentIds, Long groupId) {
        if (studentIds == null || studentIds.isEmpty()) {
            throw new IllegalArgumentException("Лист студентов пуст");
        }
        HashSet<Long> uniqueIds = new HashSet<>(studentIds);
        StudentGroup studentGroup = getGroupOrThrow(groupId);
        List<Student> students = studentRepository.findAllById(uniqueIds);

        if (students.size() != uniqueIds.size()) {
            throw new StudentNotFoundException("Некоторые студенты не найдены");
        }

        for (Student student : students) {
            student.setStudentGroup(studentGroup);
        }

        List<Student> saved = studentRepository.saveAll(students);
        return saved.stream().map(studentMapper::toDtoResponse).toList();
    }


    private void fillStudentFromDto(Student student, StudentDtoRequest studentDtoRequest) {
        student.setName(studentDtoRequest.name());
        student.setSurname(studentDtoRequest.surname());
        setGroupOrThrow(student, studentDtoRequest.studentGroupId());
    }

    private void setGroupOrThrow(Student student, Long groupId) {
        StudentGroup studentGroup = getGroupOrThrow(groupId);
        student.setStudentGroup(studentGroup);
    }

    private StudentGroup getGroupOrThrow(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

}