package com.mikutart.crs_backend.v1.controller;

import com.mikutart.crs_backend.v1.config.Config;
import com.mikutart.crs_backend.v1.controller.generic.AbstractGenericController;
import com.mikutart.crs_backend.v1.entity.Student;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController extends AbstractGenericController<Student> {
    public StudentController(IRepository<Student> repository) {
        super(repository);
    }

    @GetMapping("/{studentNumber}")
    public ResponseEntity<Student> getOne(
        @PathVariable Integer studentNumber,
        @RequestParam String sessionId
    ) {
        if (!isSessionValid(sessionId)) {
            return ResponseEntity.status(401).build();
        }
        Student student = studentRepository.findByStudentNumber(studentNumber);
        student.setPasswordMd5(Config.PASSWORD_MASK);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{studentNumber}")
    public ResponseEntity<String> delete(
        @PathVariable Integer studentNumber,
        @RequestParam String sessionId
    ) {
        if (!getCurrentRole(sessionId).equals("admin")) {
            return unauthorized();
        }
        Student student = studentRepository.findByStudentNumber(studentNumber);
        studentRepository.delete(student);
        return ResponseEntity.ok("\\^O^/");
    }

    @Override
    @GetMapping("")
    public ResponseEntity<Page<Student>> getPage(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam String sessionId
    ) {
        if (!isSessionValid(sessionId)) {
            return ResponseEntity.status(401).build();
        }
        ResponseEntity<Page<Student>> ret = ResponseEntity.ok(
            studentRepository.findAllByNameContainsOrEmailContains(
                pageable, search, search
            )
        );
        Objects.requireNonNull(ret.getBody())
            .getContent().forEach(
                student -> student.setPasswordMd5(Config.PASSWORD_MASK)
            );
        return ret;
    }
}
