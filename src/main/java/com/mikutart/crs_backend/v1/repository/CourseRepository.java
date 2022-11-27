package com.mikutart.crs_backend.v1.repository;

import com.mikutart.crs_backend.v1.annotation.AutoImplemented;
import com.mikutart.crs_backend.v1.entity.Course;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseRepository extends IRepository<Course> {
    @AutoImplemented
    Page<Course> findAllByNameContainsOrLocationContains(
        Pageable pageable,
        String name,
        String location
    );

    @AutoImplemented
    Course findByCrn(Integer crn);

    @AutoImplemented
    Course findByCrnAndStaffNumber(Integer crn, Integer staffNumber);

    @AutoImplemented
    List<Course> findAllByStaffNumber(Integer staffNumber);
}
