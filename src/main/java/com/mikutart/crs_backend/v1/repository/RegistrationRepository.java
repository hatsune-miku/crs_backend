package com.mikutart.crs_backend.v1.repository;

import com.mikutart.crs_backend.v1.annotation.AutoImplemented;
import com.mikutart.crs_backend.v1.entity.Registration;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RegistrationRepository extends IRepository<Registration> {
    @AutoImplemented
    List<Registration> findAllByStudentNumber(Integer studentNumber);

    @AutoImplemented
    List<Registration> findAllByCourseCrn(Integer courseCrn);

    @AutoImplemented
    Registration findByIdAndStudentNumber(Integer id, Integer studentNumber);
}
