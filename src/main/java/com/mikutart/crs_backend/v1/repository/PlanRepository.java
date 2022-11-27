package com.mikutart.crs_backend.v1.repository;

import com.mikutart.crs_backend.v1.annotation.AutoImplemented;
import com.mikutart.crs_backend.v1.entity.Plan;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanRepository extends IRepository<Plan> {
    @AutoImplemented
    Page<Plan> findAllByNameContains(Pageable pageable, String search);

    @AutoImplemented
    Plan findByName(String name);
}
