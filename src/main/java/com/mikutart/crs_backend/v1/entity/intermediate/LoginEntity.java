package com.mikutart.crs_backend.v1.entity.intermediate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginEntity {
    private String role;
    private String sessionId;
    private Integer accountNumber;
    private String password;
}
