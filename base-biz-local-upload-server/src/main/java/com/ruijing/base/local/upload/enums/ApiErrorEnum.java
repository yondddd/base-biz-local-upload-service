package com.ruijing.base.local.upload.enums;

import com.ruijing.fundamental.api.annotation.Model;
import software.amazon.awssdk.http.HttpStatusCode;

/**
 * @Description: api error
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@Model("api error")
public enum ApiErrorEnum {

    NO(null,);

    private int id;
    private String code;
    private String description;
    private HttpStatusCode httpStatusCode;
    
     ApiErrorEnum(int id,String code,String description,HttpStatusCode httpStatusCode){
        this.id = id;
        this.code = code;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
    
}
