package com.ruijing.base.local.upload.util;

public class StringUtil {
    public static final String EMPTY = "";

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || "null".equals(str);
    }
}
