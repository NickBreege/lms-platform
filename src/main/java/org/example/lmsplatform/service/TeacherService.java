package org.example.lmsplatform.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.dao.TeacherRepository;
import org.example.lmsplatform.exception.TeacherNotFoundException;
import org.example.lmsplatform.model.dto.TeacherDtoRequest;
import org.example.lmsplatform.model.dto.TeacherDtoResponse;
import org.example.lmsplatform.model.entity.Teacher;
import org.example.lmsplatform.model.mapper.TeacherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Transactional
    public TeacherDtoResponse createTeacher(TeacherDtoRequest teacherDtoRequest) {
        Teacher teacher = new Teacher();
        fillTeacherFromDto(teacher, teacherDtoRequest);

        return teacherMapper.toDtoResponse(teacherRepository.save(teacher));
    }

    public TeacherDtoResponse getTeacherById(Long teacherId) {
        Teacher teacher = getTeacherOrThrow(teacherId);
        return teacherMapper.toDtoResponse(teacher);
    }

    @Transactional
    public TeacherDtoResponse updateTeacher(Long teacherId, TeacherDtoRequest teacherDtoRequest) {
        Teacher teacher = getTeacherOrThrow(teacherId);
        fillTeacherFromDto(teacher, teacherDtoRequest);

        return teacherMapper.toDtoResponse(teacher);
    }

    @Transactional
    public void deleteTeacher(Long teacherId) {
        Teacher teacher = getTeacherOrThrow(teacherId);
        teacherRepository.delete(teacher);
    }

    private Teacher getTeacherOrThrow(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId));
    }

    private void fillTeacherFromDto(Teacher teacher, TeacherDtoRequest teacherDtoRequest) {
        teacher.setName(teacherDtoRequest.name());
        teacher.setSurname(teacherDtoRequest.surname());
    }
}
