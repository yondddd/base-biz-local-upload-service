package com.ruijing.base.local.upload.util;

import java.math.BigDecimal;

public class ConvertOp {

    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        } else {
            return false;
        }
    }

    public static String convert2String(Object obj) {
        String result;
        if (!isNull(obj)) {
            try {
                result = String.valueOf(obj);
            } catch (Exception e) {
                e.printStackTrace();
                ;
                result = StringUtil.EMPTY;
            }
        } else {
            result = StringUtil.EMPTY;
        }
        return result;
    }

    public static Integer convert2Int(Object obj) {
        Integer result;
        if (!isNull(obj)) {
            if (obj.getClass() == BigDecimal.class) {
                BigDecimal bigDecimal = (BigDecimal) obj;
                return bigDecimal.intValue();
            } else {
                try {
                    result = Integer.parseInt(String.valueOf(obj));
                } catch (Exception e) {
                    e.printStackTrace();
                    ;
                    result = 0;
                }
            }

        } else {
            result = 0;
        }
        return result;
    }
}
