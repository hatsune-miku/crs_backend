package com.mikutart.crs_backend.v1.entity.generic;

import com.mikutart.crs_backend.v1.interfaces.IEntity;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Date;

public abstract class AbstractEntity<T> implements IEntity<T> {
    /**
     * Update all fields of current instance with provided data.
     * Only non-null fields from provided data will be recorded.
     *
     * @param source the template.
     * @return this.
     */
    @Override
    @SuppressWarnings("unchecked")
    public T update(T source) {
        try {
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                Field sourceField = source.getClass()
                    .getDeclaredField(field.getName());

                field.setAccessible(true);
                sourceField.setAccessible(true);

                if (sourceField.get(source) != null) {
                    field.set(this, sourceField.get(source));
                }
                else {
                    // Default value for fields.
                    if (field.getType() == String.class) {
                        field.set(this, "");
                    }
                    else if (field.getType() == Integer.class) {
                        field.set(this, 0);
                    }
                    else if (field.getType() == Long.class) {
                        field.set(this, 0L);
                    }
                    else if (field.getType() == Boolean.class) {
                        field.set(this, false);
                    }
                    else if (field.getType() == Date.class) {
                        field.set(this, new Date());
                    }
                }
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(getClass())
                .error(e.getMessage(), e);
        }
        return (T) this;
    }
}
