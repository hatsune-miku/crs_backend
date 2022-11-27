package com.mikutart.crs_backend.v1.controller.generic;

import com.mikutart.crs_backend.v1.interfaces.IEntity;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import com.mikutart.crs_backend.v1.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractGenericController<T extends IEntity<T>> extends BaseController {
    protected final GenericService<T> service;

    public AbstractGenericController(IRepository<T> repository) {
        this.service = new GenericService<>(repository);
    }

    @GetMapping("")
    public ResponseEntity<Page<T>> getPage(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String sessionId
    ) {
        return ResponseEntity.ok(service.getPage(pageable));
    }

    @PutMapping("")
    public ResponseEntity<T> update(
        @RequestBody T updated,
        @RequestParam(required = false) String sessionId
    ) {
        return ResponseEntity.ok(service.update(updated));
    }

    @PostMapping("")
    public ResponseEntity<T> create(
        @RequestBody T created,
        @RequestParam(required = false) String sessionId
    ) {
        return ResponseEntity.ok(service.create(created));
    }
}
