package com.mikutart.crs_backend.v1.controller;

import com.mikutart.crs_backend.v1.controller.generic.AbstractGenericController;
import com.mikutart.crs_backend.v1.entity.Plan;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plan")
public class PlanController extends AbstractGenericController<Plan> {
    public PlanController(IRepository<Plan> repository) {
        super(repository);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<Page<Plan>> getPage(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam String sessionId
    ) {
        if (!isSessionValid(sessionId)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(
            planRepository.findAllByNameContains(
                pageable, search
            )
        );
    }

    @GetMapping("/{planName}")
    public ResponseEntity<Plan> getOne(
        @PathVariable String planName,
        @RequestParam String sessionId
    ) {
        if (!isSessionValid(sessionId)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(planRepository.findByName(planName));
    }

    @DeleteMapping("/{planName}")
    public ResponseEntity<String> delete(
        @PathVariable String planName,
        @RequestParam String sessionId
    ) {
        if (getCurrentRole(sessionId).equals("admin")) {
            planRepository.delete(planRepository.findByName(planName));
            return ResponseEntity.ok("\\^O^/");
        }
        return unauthorized();
    }
}
