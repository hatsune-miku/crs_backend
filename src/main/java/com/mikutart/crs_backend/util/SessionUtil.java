package com.mikutart.crs_backend.util;

import java.util.UUID;

public class SessionUtil {
    public record SessionInfo(
        String role,
        Integer userId
    ) { }

    public static String makeRedisSessionKey(String sessionId) {
        return "session_" + sessionId;
    }

    public static String makeRedisSessionValue(String role, Integer userId) {
        return "%s,%s"
            .formatted(role, userId);
    }

    public static SessionInfo parseSessionInfo(String sessionValue) {
        String[] parts = sessionValue.split(",");
        return new SessionInfo(
            parts[0],
            Integer.parseInt(parts[1])
        );
    }

    public static String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
