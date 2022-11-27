package com.mikutart.crs_backend.v1.repository;

import com.mikutart.crs_backend.v1.annotation.AutoImplemented;
import com.mikutart.crs_backend.v1.entity.Staff;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffRepository extends IRepository<Staff> {
    @AutoImplemented
    Staff findByStaffNumber(Integer staffId);

    @AutoImplemented
    Page<Staff> findAllByNameContainsOrEmailContains(
        Pageable pageable,
        String name,
        String email
    );
}
