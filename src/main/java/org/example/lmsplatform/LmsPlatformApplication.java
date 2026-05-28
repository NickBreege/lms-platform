package org.example.lmsplatform;

import org.example.lmsplatform.model.dto.StudentDtoRequest;
import org.example.lmsplatform.model.dto.StudentDtoResponse;
import org.example.lmsplatform.model.dto.StudentGroupDtoRequest;
import org.example.lmsplatform.model.dto.StudentGroupDtoResponse;
import org.example.lmsplatform.service.GroupService;
import org.example.lmsplatform.service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LmsPlatformApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LmsPlatformApplication.class, args);

        //создал 2 группы
        GroupService groupService = context.getBean(GroupService.class);
        StudentGroupDtoResponse group1 = groupService.createGroup(new StudentGroupDtoRequest("Юриспруденция"));
        StudentGroupDtoResponse group2 = groupService.createGroup(new StudentGroupDtoRequest("Журналистика"));
        System.out.println("Две группы:");
        System.out.println(group1);
        System.out.println(group2);

        System.out.println("_____________________________________________________");

        //создал студента в группе юриспруденция
        StudentService studentService = context.getBean(StudentService.class);
        StudentDtoResponse studentDtoResponse = studentService.createStudent(
                new StudentDtoRequest("Ivan", "Ivanov", group1.getId()));

        System.out.println(studentDtoResponse);

        //назначаю новую группу студенту(журналистика)
        studentService.assignStudentToGroup(studentDtoResponse.getId(), group2.getId());

        System.out.println("_____________________________________________________");
        //проверка изменилось ли группа с юриспруденции на журналистику
        StudentDtoResponse studentById = studentService.getStudentById(studentDtoResponse.getId());
        System.out.println(studentById);


    }

}
