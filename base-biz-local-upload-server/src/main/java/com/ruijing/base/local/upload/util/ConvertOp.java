package com.ruijing.base.local.upload.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class ConvertOp {
    
    public static boolean isNull(Object obj) {
        return obj == null;
    }
    
    public static String convert2String(Object obj) {
        String result;
        if (!isNull(obj)) {
            try {
                result = String.valueOf(obj);
            } catch (Exception e) {
                e.printStackTrace();
                result = StringUtils.EMPTY;
            }
        } else {
            result = StringUtils.EMPTY;
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
                    result = 0;
                }
            }
            
        } else {
            result = 0;
        }
        return result;
    }
}
