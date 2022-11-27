package com.mikutart.crs_backend.v1.service;

import com.mikutart.crs_backend.v1.interfaces.IEntity;
import com.mikutart.crs_backend.v1.interfaces.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public class GenericService<T extends IEntity<T>> {
    private final IRepository<T> repository;

    public GenericService(IRepository<T> repository) {
        this.repository = repository;
    }

    public Page<T> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T get(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    public T update(T updated) {
        T entity = get(updated.getId());
        entity.update(updated);
        entity.setUpdatedAt(new Date());
        return repository.save(entity);
    }

    public T create(T created) {
        T newEntityWithNewId = created.newCopyWithNewId();
        newEntityWithNewId.setCreatedAt(new Date());
        newEntityWithNewId.setUpdatedAt(new Date());
        return repository.save(newEntityWithNewId);
    }

    public void delete(Integer id) {
        try {
            get(id);
            repository.deleteById(id);
        }
        catch (Exception e) {
            // Ignored.
        }
    }
}
