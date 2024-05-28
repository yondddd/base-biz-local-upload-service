package com.ruijing.base.local.upload.enums;

import com.ruijing.fundamental.api.annotation.Model;

/**
 * @Description: api error
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@Model("api error")
public enum ApiErrorEnum {

    ErrNoSuchKey(64, "NoSuchKey", "The specified key does not exist.", "0026-00000001");

    private final int id;
    private final String code;
    private final String description;
    private final String ec;

    ApiErrorEnum(int id, String code, String description, String ec) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.ec = ec;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getEc() {
        return ec;
    }

}
