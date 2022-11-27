package com.mikutart.crs_backend.v1.repository;

import com.mikutart.crs_backend.v1.entity.Grade;
import com.mikutart.crs_backend.v1.interfaces.IRepository;

public interface GradeRepository extends IRepository<Grade> {
    Grade findByRegistrationId(Integer registrationId);
}
