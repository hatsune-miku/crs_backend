package com.mikutart.crs_backend.v1.annotation;

import lombok.Getter;
import lombok.Setter;

public @interface RequireRoles {
    Roles[] value();

    enum Roles {
        STUDENT("student"),
        STAFF("staff"),
        ADMINISTRATOR("admin"),
        ANY("any");

        @Getter
        final String name;

        Roles(String name) {
            this.name = name;
        }
    }
}
