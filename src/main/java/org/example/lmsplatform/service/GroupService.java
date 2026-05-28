package org.example.lmsplatform.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.dao.CourseRepository;
import org.example.lmsplatform.dao.GroupRepository;
import org.example.lmsplatform.exception.CourseNotFoundException;
import org.example.lmsplatform.exception.GroupNotFoundException;
import org.example.lmsplatform.model.dto.StudentGroupDtoRequest;
import org.example.lmsplatform.model.dto.StudentGroupDtoResponse;
import org.example.lmsplatform.model.entity.Course;
import org.example.lmsplatform.model.entity.StudentGroup;
import org.example.lmsplatform.model.mapper.GroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final GroupMapper groupMapper;

    @Transactional
    public StudentGroupDtoResponse createGroup(StudentGroupDtoRequest groupDtoRequest) {
        String name = groupDtoRequest.getName();

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Невалидное имя группы: " + groupDtoRequest.getName());
        }

        name = name.trim();
        if (groupRepository.existsByName(name)) {
            throw new IllegalArgumentException("Группа уже существует");
        }

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setName(name);

        return groupMapper.toDtoResponse(groupRepository.save(studentGroup));
    }

    @Transactional
    public StudentGroupDtoResponse updateGroup(Long groupId, StudentGroupDtoRequest groupDtoRequest) {

        StudentGroup studentGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        if (groupDtoRequest.getName() == null || groupDtoRequest.getName().isBlank()) {
            throw new IllegalArgumentException("Невалидное имя группы: " + groupDtoRequest.getName());
        }

        String name = groupDtoRequest.getName().trim();
        if (groupRepository.existsByNameAndIdNot(name, groupId)) {
            throw new IllegalArgumentException("Группа уже существует");
        }

        studentGroup.setName(name);

        StudentGroup saved = groupRepository.save(studentGroup);
        return groupMapper.toDtoResponse(saved);
    }

    @Transactional
    public void deleteGroup(Long id) {
        StudentGroup studentGroup = groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));

        groupRepository.delete(studentGroup);
    }

    @Transactional
    public void addGroupToCourse(Long groupId, Long courseId) {
        StudentGroup studentGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        course.getStudentGroups().add(studentGroup);
        studentGroup.getCourses().add(course);

        groupRepository.save(studentGroup);
    }
}
