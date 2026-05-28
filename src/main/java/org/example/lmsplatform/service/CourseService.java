package org.example.lmsplatform.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.dao.CourseRepository;
import org.example.lmsplatform.dao.GroupRepository;
import org.example.lmsplatform.dao.TeacherRepository;
import org.example.lmsplatform.exception.CourseNotFoundException;
import org.example.lmsplatform.exception.GroupNotFoundException;
import org.example.lmsplatform.exception.TeacherNotFoundException;
import org.example.lmsplatform.model.dto.CourseDtoRequest;
import org.example.lmsplatform.model.dto.CourseDtoResponse;
import org.example.lmsplatform.model.entity.Course;
import org.example.lmsplatform.model.entity.StudentGroup;
import org.example.lmsplatform.model.entity.Teacher;
import org.example.lmsplatform.model.mapper.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;


    @Transactional
    public CourseDtoResponse createCourse(CourseDtoRequest courseDtoRequest) {
        Course course = new Course();

        fillCourseFromDto(course, courseDtoRequest);
        return courseMapper.toDtoResponse(courseRepository.save(course));
    }

    @Transactional
    public CourseDtoResponse updateCourse(Long courseId, CourseDtoRequest courseDtoRequest) {
        Course course = getCourseOrThrow(courseId);
        fillCourseFromDto(course, courseDtoRequest);

        return courseMapper.toDtoResponse(course);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = getCourseOrThrow(courseId);
        courseRepository.delete(course);
    }

    @Transactional(readOnly = true)
    public CourseDtoResponse getCourse(Long courseId) {
        Course course = getCourseOrThrow(courseId);
        return courseMapper.toDtoResponse(course);
    }

    @Transactional(readOnly = true)
    public List<CourseDtoResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDtoResponse)
                .toList();
    }

    @Transactional
    public void addGroupToCourse(Long courseId, Long groupId) {
        Course course = getCourseOrThrow(courseId);
        StudentGroup group = getStudentGroupOrThrow(groupId);

        course.getStudentGroups().add(group);
        group.getCourses().add(course);
    }

    @Transactional
    public void removeGroupFromCourse(Long courseId, Long groupId) {
        Course course = getCourseOrThrow(courseId);
        StudentGroup group = getStudentGroupOrThrow(groupId);

        course.getStudentGroups().remove(group);
        group.getCourses().remove(course);
    }


    private Course getCourseOrThrow(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    private Teacher getTeacherOrThrow(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId));
    }

    private StudentGroup getStudentGroupOrThrow(Long studentGroupId) {
        return groupRepository.findById(studentGroupId)
                .orElseThrow(() -> new GroupNotFoundException(studentGroupId));
    }

    private void fillCourseFromDto(Course course, CourseDtoRequest courseDtoRequest) {
        course.setName(courseDtoRequest.getName());
        course.setDescription(courseDtoRequest.getDescription());
        course.setTeacher(getTeacherOrThrow(courseDtoRequest.getTeacherId()));
    }
}
