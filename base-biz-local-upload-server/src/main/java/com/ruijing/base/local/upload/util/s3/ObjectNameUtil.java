package com.ruijing.base.local.upload.util.s3;

import com.ruijing.fundamental.cat.Cat;
import com.ruijing.fundamental.common.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: oss objectName生成
 * @Author: WangJieLong
 * @Date: 2023-06-28
 */
public class ObjectNameUtil {
    
    /**
     * 日期对应
     */
    private final static Map<String, String> MAP = new HashMap<String, String>() {{
        put("0", "a");
        put("1", "b");
        put("2", "c");
        put("3", "d");
        put("4", "e");
        put("5", "f");
        put("6", "g");
        put("7", "h");
        put("8", "i");
        put("9", "j");
        put("10", "k");
        put("11", "l");
        put("12", "m");
    }};
    
    private static final String FORMAT = "yyyy-M";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectNameUtil.class);
    
    /**
     * @param key object
     * @return
     */
    public static String getTimeKey(String key) {
        try {
            return getTimeLetter() + "/" + key;
        } catch (Exception e) {
            Cat.logError("ObjectNameTimeUtil", "getObjectName", e.getMessage(), e);
            LOGGER.error("<|>ObjectNameTimeUtil_getObjectName<|>", e);
        }
        return key;
    }
    
    
    /**
     * 2023年2季度6月->202326->cacdcg
     */
    public static String getTimeLetter() {
        long now = System.currentTimeMillis();
        String format = DateUtils.format(FORMAT, new Date(now));
        String[] split = format.split("-");
        String year = split[0];
        String month = split[1];
        String quarter = String.valueOf((Integer.parseInt(month) - 1) / 3 + 1);
        StringBuilder yearLetter = new StringBuilder();
        for (char c : year.toCharArray()) {
            yearLetter.append(MAP.get(String.valueOf(c)));
        }
        return yearLetter + MAP.get(quarter) + MAP.get(month);
    }
    
}
