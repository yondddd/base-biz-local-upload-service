package com.ruijing.base.local.upload.enums;

import com.ruijing.fundamental.api.annotation.Model;

/**
 * @Description: api error
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@Model("api error")
public enum ApiErrorEnum {
    
    ErrNoSuchKey(64, "NoSuchKey", "The specified key does not exist."),
    
    ErrInvalidRequest(174, "InvalidRequest", "Invalid Request"),
    
    ErrBusy(228, "ServerBusy", "The service is unavailable. Please retry.");
    
    private final int id;
    private final String code;
    private final String description;
    
    ApiErrorEnum(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
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
    
}
