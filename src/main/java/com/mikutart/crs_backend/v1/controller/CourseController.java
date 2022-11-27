package com.mikutart.crs_backend.v1.controller;

import com.mikutart.crs_backend.v1.annotation.RequireRoles;
import com.mikutart.crs_backend.v1.controller.generic.AbstractGenericController;
import com.mikutart.crs_backend.v1.entity.Course;
import com.mikutart.crs_backend.v1.entity.Staff;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mikutart.crs_backend.v1.annotation.RequireRoles.Roles;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController extends AbstractGenericController<Course> {
    public CourseController(IRepository<Course> repository) {
        super(repository);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<Page<Course> > getPage(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String sessionId
    ) {
        return ResponseEntity.ok(
            courseRepository.findAllByNameContainsOrLocationContains(
                pageable, search, search
            )
        );
    }

    @GetMapping("/{crn}")
    public ResponseEntity<Course> getOne(
        @PathVariable Integer crn
    ) {
        return ResponseEntity.ok(courseRepository.findByCrn(crn));
    }

    @DeleteMapping("/{crn}")
    public ResponseEntity<String> delete(
        @PathVariable Integer crn,
        @RequestParam(required = false) String sessionId
    ) {
        Staff staff = getCurrentStaff(sessionId);
        if (staff == null) {
            return unauthorized();
        }
        try {
            if (staff.getIsAdmin()) {
                courseRepository.delete(
                    courseRepository.findByCrn(crn));
            }
            else {
                courseRepository.delete(
                    courseRepository.findByCrnAndStaffNumber(crn, staff.getStaffNumber()));
            }
        }
        catch (Exception e) {
            return failed("Course does not exist");
        }
        return ResponseEntity.ok("\\^O^/");
    }
}
