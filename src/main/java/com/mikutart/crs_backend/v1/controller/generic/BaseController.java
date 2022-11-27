package com.mikutart.crs_backend.v1.controller.generic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikutart.crs_backend.util.SessionUtil;
import com.mikutart.crs_backend.v1.entity.Staff;
import com.mikutart.crs_backend.v1.entity.Student;
import com.mikutart.crs_backend.v1.helper.RedisHelper;
import com.mikutart.crs_backend.v1.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;

public class BaseController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    protected ObjectMapper objectMapper;

    @Resource
    protected StudentRepository studentRepository;

    @Resource
    protected StaffRepository staffRepository;

    @Resource
    protected MajorRepository majorRepository;

    @Resource
    protected CourseRepository courseRepository;

    @Resource
    protected RegistrationRepository registrationRepository;

    @Resource
    protected PlanRepository planRepository;

    @Resource
    protected GradeRepository gradeRepository;


    // Logger.
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // Redis key-value-only helper.
    private RedisHelper redisHelper = null;

    public RedisHelper getRedisHelper() {
        // We can not instantiate RedisHelper in constructor because
        // it requires StringRedisTemplate to be injected
        // and that injection needs time.
        if (redisHelper == null) {
            redisHelper = new RedisHelper(stringRedisTemplate);
        }
        return redisHelper;
    }

    public boolean isSessionValid(String sessionId) {
        if (sessionId == null) {
            return false;
        }
        String storedSessionInfo = getRedisHelper().get(
            SessionUtil.makeRedisSessionKey(sessionId));
        return storedSessionInfo != null;
    }

    public SessionUtil.SessionInfo getCurrentSessionInfo(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        String storedSessionInfo = getRedisHelper().get(
            SessionUtil.makeRedisSessionKey(sessionId));
        if (storedSessionInfo == null) {
            return null;
        }
        return SessionUtil.parseSessionInfo(storedSessionInfo);
    }

    public String getCurrentRole(String sessionId) {
        SessionUtil.SessionInfo info = getCurrentSessionInfo(sessionId);
        if (info == null) {
            return null;
        }
        return info.role();
    }

    public Staff getCurrentStaff(String sessionId) {
        SessionUtil.SessionInfo sessionInfo = getCurrentSessionInfo(sessionId);
        if (sessionInfo.role().equals("staff") || sessionInfo.role().equals("admin")) {
            return staffRepository.findByStaffNumber(sessionInfo.userId());
        }
        return null;
    }

    public Student getCurrentStudent(String sessionId) {
        SessionUtil.SessionInfo sessionInfo = getCurrentSessionInfo(sessionId);
        if (!sessionInfo.role().equals("student")) {
            return null;
        }
        return studentRepository.findByStudentNumber(sessionInfo.userId());
    }

    public ResponseEntity<String> unauthorized() {
        return ResponseEntity.status(401).body("Unauthorized");
    }

    public ResponseEntity<String> failed(String message) {
        return ResponseEntity.internalServerError().body(message);
    }

    public ResponseEntity<String> success(String message) {
        return ResponseEntity.ok(message);
    }
}
