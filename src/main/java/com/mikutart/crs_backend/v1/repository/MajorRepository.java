package com.mikutart.crs_backend.v1.repository;

import com.mikutart.crs_backend.v1.annotation.AutoImplemented;
import com.mikutart.crs_backend.v1.entity.Major;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MajorRepository extends IRepository<Major> {
    @AutoImplemented
    Page<Major> findAllByNameContains(Pageable pageable, String name);

    @AutoImplemented
    Major findByName(String name);
}
