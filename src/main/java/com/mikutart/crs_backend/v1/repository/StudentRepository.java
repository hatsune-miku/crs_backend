package com.mikutart.crs_backend.v1.repository;

import com.mikutart.crs_backend.v1.annotation.AutoImplemented;
import com.mikutart.crs_backend.v1.entity.Student;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentRepository extends IRepository<Student> {
    @AutoImplemented
    Student findByStudentNumber(Integer studentNumber);

    @AutoImplemented
    Page<Student> findAllByNameContainsOrEmailContains(
        Pageable pageable,
        String name,
        String email
    );
}
