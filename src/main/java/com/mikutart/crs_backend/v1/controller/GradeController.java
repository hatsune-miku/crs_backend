package com.mikutart.crs_backend.v1.controller;

import com.mikutart.crs_backend.v1.controller.generic.AbstractGenericController;
import com.mikutart.crs_backend.v1.entity.Grade;
import com.mikutart.crs_backend.v1.entity.Registration;
import com.mikutart.crs_backend.v1.entity.Staff;
import com.mikutart.crs_backend.v1.entity.Student;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grade")
public class GradeController extends AbstractGenericController<Grade> {
    public GradeController(IRepository<Grade> repository) {
        super(repository);
    }

    @Override
    public ResponseEntity<Page<Grade>> getPage(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String sessionId
    ) {
        String role = getCurrentRole(sessionId);
        if (role.equals("student")) {
            Student student = getCurrentStudent(sessionId);
            List<Grade> grades = student.getRegistrations(registrationRepository)
                .stream()
                .map(reg -> reg.getGrade(gradeRepository))
                .toList();
            return ResponseEntity.ok(new PageImpl<>(grades, pageable, grades.size()));
        }
        else {
            Staff staff = getCurrentStaff(sessionId);
            if (staff.getIsAdmin()) {
                return super.getPage(pageable, search, sessionId);
            } else {
                List<Grade> grades = staff.getCourses(courseRepository)
                    .stream()
                    .map(course -> course.getRegistrations(registrationRepository))
                    .flatMap(List::stream)
                    .map(reg -> reg.getGrade(gradeRepository))
                    .toList();
                return ResponseEntity.ok(new PageImpl<>(grades, pageable, grades.size()));
            }
        }
    }

    @DeleteMapping("/{gradeId}")
    public ResponseEntity<String> delete(
        @PathVariable Integer gradeId,
        @RequestParam(required = false) String sessionId
    ) {
        if (getCurrentRole(sessionId).equals("admin")) {
            gradeRepository.delete(gradeRepository.getReferenceById(gradeId));
            return ResponseEntity.ok("\\^O^/");
        }
        return unauthorized();
    }
}
