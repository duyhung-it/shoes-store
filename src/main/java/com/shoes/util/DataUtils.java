package com.shoes.util;

import java.lang.reflect.Field;
import java.util.Objects;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Slf4j
@NoArgsConstructor
public class DataUtils {

    private static final char KEY_ESCAPE = '\\';

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String trim(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        if (obj instanceof String) {
            return ((String) obj).trim();
        }
        return parseToString(obj);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static void trimValue(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getType().getName().equals(String.class.getName())) {
                    field.setAccessible(true);
                    String value = String.valueOf(FieldUtils.readDeclaredField(object, field.getName(), true));
                    if (value != null && !value.equals("null")) {
                        field.set(object, value.trim());
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public static String parseToString(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return String.valueOf(obj);
    }

    public static String likeSpecialToStr(String str) {
        str = str.trim();
        str = str.replace("_", KEY_ESCAPE + "_");
        str = str.replace("%", KEY_ESCAPE + "%");
        return str;
    }

    public static String makeLikeStr(String str) {
        if (isNullOrEmpty(str)) {
            return "%%";
        }
        return "%" + str + "%";
    }

    public static String toUpperCase(String str) {
        if (isNullOrEmpty(str)) return str;
        return str.toUpperCase();
    }
}
