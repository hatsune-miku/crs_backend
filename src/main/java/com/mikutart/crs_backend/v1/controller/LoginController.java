package com.mikutart.crs_backend.v1.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mikutart.crs_backend.util.SessionUtil;
import com.mikutart.crs_backend.v1.controller.generic.BaseController;
import com.mikutart.crs_backend.v1.entity.Staff;
import com.mikutart.crs_backend.v1.entity.Student;
import com.mikutart.crs_backend.v1.entity.intermediate.LoginEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Locale;

@Controller
public class LoginController extends BaseController {
    @PostMapping("/api/v1/login")
    public ResponseEntity<ObjectNode> login(
        @RequestBody LoginEntity loginEntity
    ) {
        String role = loginEntity.getRole().toLowerCase(Locale.ROOT);
        Integer userId = loginEntity.getAccountNumber();
        String sessionId = loginEntity.getSessionId();
        String password = loginEntity.getPassword();
        String name;
        boolean isAdmin;

        if (loginEntity.getAccountNumber() == null) {
            return ResponseEntity.ok(loginFailed("Account number is required"));
        }

        // Client is trying to log in with session id.
        if (isSessionValid(sessionId)) {
            if (role.equals("staff") || role.equals("admin")) {
                Staff staff = getCurrentStaff(sessionId);
                isAdmin = staff.getIsAdmin();
                name = staff.getName();
            }
            else {
                Student student = getCurrentStudent(sessionId);
                isAdmin = false;
                name = student.getName();
            }
            return ResponseEntity.ok(loginSuccess(sessionId, isAdmin, name));
        }
        else if (password == null) {
            // Only session provided?
            return ResponseEntity.ok(loginFailed("Session expired. Please login again."));
        }

        // Login using password.
        return ResponseEntity.ok(
            switch (role) {
                case "student" ->
                    loginStudent(userId, password);

                case "staff" ->
                    loginStaff(userId, password);

                case "admin" ->
                    loginStaff(userId, password);
                    // loginAdmin(userId, password);

                default ->
                    loginFailed("Invalid credentials.");
            }
        );
    }

    private ObjectNode loginStudent(Integer studentNumber, String password) {
        try {
            Student student = studentRepository.findByStudentNumber(studentNumber);
            if (student.isPasswordValid(password)) {
                return loginSuccess(
                    createSessionIdForUser("student", studentNumber),
                    false,
                    student.getName()
                );
            }
        } catch (Exception e) {
            return loginFailed("The user does not exist.");
        }
        return loginFailed("Invalid credentials.");
    }

    private ObjectNode loginStaff(Integer staffNumber, String password) {
        try {
            Staff staff = staffRepository.findByStaffNumber(staffNumber);
            if (staff.isPasswordValid(password)) {
                return loginSuccess(
                    createSessionIdForUser(
                        staff.getIsAdmin() ? "admin" : "staff",
                        staffNumber
                    ),
                    staff.getIsAdmin(),
                    staff.getName()
                );
            }
        } catch (Exception e) {
            return loginFailed("The user does not exist.");
        }
        return loginFailed( "Invalid credentials.");
    }

    private ObjectNode loginAdmin(Integer adminNumber, String password) {
        return loginFailed( "Admin login is blocked.");
    }

    private ObjectNode loginSuccess(String sessionId, boolean isAdmin, String name) {
        ObjectNode ret = objectMapper.createObjectNode();
        ret.put("success", true);
        ret.put("reason", "");
        ret.put("sessionId", sessionId);
        ret.put("isAdmin", isAdmin);
        ret.put("name", name);
        return ret;
    }

    private ObjectNode loginFailed(String reason) {
        ObjectNode ret = objectMapper.createObjectNode();
        ret.put("success", false);
        ret.put("reason", reason);
        ret.put("sessionId", "");
        return ret;
    }

    private String createSessionIdForUser(String role, Integer userId) {
        String sessionId = SessionUtil.generateSessionId();
        getRedisHelper().set(
            SessionUtil.makeRedisSessionKey(sessionId),
            SessionUtil.makeRedisSessionValue(role, userId)
        );
        return sessionId;
    }
}
