package com.mikutart.crs_backend.v1.controller;

import com.mikutart.crs_backend.v1.config.Config;
import com.mikutart.crs_backend.v1.controller.generic.AbstractGenericController;
import com.mikutart.crs_backend.v1.entity.Staff;
import com.mikutart.crs_backend.v1.entity.Student;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController extends AbstractGenericController<Staff> {
    public StaffController(IRepository<Staff> repository) {
        super(repository);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<Page<Staff>> getPage(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam String sessionId
    ) {
        if (!isSessionValid(sessionId)) {
            return ResponseEntity.status(401).build();
        }
        ResponseEntity<Page<Staff>> ret = ResponseEntity.ok(
            staffRepository.findAllByNameContainsOrEmailContains(
                pageable, search, search
            )
        );
        Objects.requireNonNull(ret.getBody())
            .getContent().forEach(
                s -> s.setPasswordMd5(Config.PASSWORD_MASK)
            );
        return ret;
    }

    @GetMapping("/{staffNumber}")
    public ResponseEntity<Staff> getOne(
        @PathVariable Integer staffNumber,
        @RequestParam String sessionId
    ) {
        if (!isSessionValid(sessionId)) {
            return ResponseEntity.status(401).build();
        }
        Staff staff = staffRepository.findByStaffNumber(staffNumber);
        staff.setPasswordMd5(Config.PASSWORD_MASK);
        return ResponseEntity.ok(staff);
    }

    @DeleteMapping("/{staffNumber}")
    public ResponseEntity<String> delete(
        @PathVariable Integer staffNumber,
        @RequestParam String sessionId
    ) {
        if (!getCurrentRole(sessionId).equals("admin")) {
            return unauthorized();
        }
        Staff staff = staffRepository.findByStaffNumber(staffNumber);
        staffRepository.delete(staff);
        return ResponseEntity.ok("\\^O^/");
    }
}
