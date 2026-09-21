package org.example.lmsplatform.dao;


import org.example.lmsplatform.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
