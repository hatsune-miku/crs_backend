package com.mikutart.crs_backend.v1.controller;

import com.mikutart.crs_backend.v1.controller.generic.AbstractGenericController;
import com.mikutart.crs_backend.v1.entity.Plan;
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
@RequestMapping("/api/v1/registration")
public class RegistrationController extends AbstractGenericController<Registration> {
    public RegistrationController(IRepository<Registration> repository) {
        super(repository);
    }

    @Override
    public ResponseEntity<Page<Registration>> getPage(
        Pageable pageable,
        @RequestParam String search,
        @RequestParam String sessionId
    ) {
        String role = getCurrentRole(sessionId);
        if (role.equals("student")) {
            Student student = getCurrentStudent(sessionId);
            List<Registration> registrations = student.getRegistrations(registrationRepository);
            return ResponseEntity.ok(new PageImpl<>(registrations, pageable, registrations.size()));
        }
        else {
            Staff staff = getCurrentStaff(sessionId);
            if (staff.getIsAdmin()) {
                return super.getPage(pageable, search, sessionId);
            }
            else {
                List<Registration> registrations = staff.getCourses(courseRepository)
                    .stream()
                    .map(course -> course.getRegistrations(registrationRepository))
                    .flatMap(List::stream)
                    .toList();
                return ResponseEntity.ok(new PageImpl<>(registrations, pageable, registrations.size()));
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
        @PathVariable Integer id,
        @RequestParam String sessionId
    ) {
        String role = getCurrentRole(sessionId);

        if (role.equals("student")) {
            Student student = getCurrentStudent(sessionId);
            registrationRepository.delete(
                registrationRepository.findByIdAndStudentNumber(id, student.getStudentNumber())
            );
        }
        else {
            Staff staff = getCurrentStaff(sessionId);
            if (!staff.getIsAdmin()) {
                return unauthorized();
            }
            registrationRepository.delete(registrationRepository.getReferenceById(id));
        }
        return ResponseEntity.ok("\\^O^/");
    }
}
