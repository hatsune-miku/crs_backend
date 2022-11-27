package com.mikutart.crs_backend.v1.interfaces;

import java.lang.reflect.Field;
import java.util.Date;

public interface IEntity<T> {
    // update current instance with provided data
    T update(T source);

    Integer getId();

    // based on current data create new instance with new id
    T newCopyWithNewId();

    void setUpdatedAt(Date updatedAt);

    void setCreatedAt(Date createdAt);
}
