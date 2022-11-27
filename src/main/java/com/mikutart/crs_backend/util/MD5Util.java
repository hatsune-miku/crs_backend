package com.mikutart.crs_backend.util;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;

/**
 * MD5 is considered unsecure 但是我确实用习惯了
 */
public class MD5Util {
    public static String md5(String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] md5InBytes = digest.digest(source.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(md5InBytes)
                .toLowerCase(Locale.ROOT);
        }
        catch (Exception e) {
            return "";
        }
    }
}
