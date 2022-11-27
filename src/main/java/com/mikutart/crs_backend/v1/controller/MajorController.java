package com.mikutart.crs_backend.v1.controller;

import com.mikutart.crs_backend.v1.controller.generic.AbstractGenericController;
import com.mikutart.crs_backend.v1.entity.Major;
import com.mikutart.crs_backend.v1.entity.Plan;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/major")
public class MajorController extends AbstractGenericController<Major> {
    public MajorController(IRepository<Major> repository) {
        super(repository);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<Page<Major>> getPage(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam String sessionId
    ) {
        if (!isSessionValid(sessionId)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(
            majorRepository.findAllByNameContains(
                pageable, search
            )
        );
    }

    @GetMapping("/{majorName}")
    public ResponseEntity<Major> getOne(
        @PathVariable String majorName,
        @RequestParam(required = false) String sessionId
    ) {
        if (!isSessionValid(sessionId)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(majorRepository.findByName(majorName));
    }

    @DeleteMapping("/{majorName}")
    public ResponseEntity<String> delete(
        @PathVariable String majorName,
        @RequestParam(required = false) String sessionId
    ) {
        if (getCurrentRole(sessionId).equals("admin")) {
            majorRepository.delete(majorRepository.findByName(majorName));
            return ResponseEntity.ok("\\^O^/");
        }
        return unauthorized();
    }
}
