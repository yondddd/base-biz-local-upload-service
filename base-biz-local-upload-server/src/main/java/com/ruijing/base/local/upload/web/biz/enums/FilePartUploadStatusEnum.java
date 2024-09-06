package com.ruijing.base.local.upload.web.biz.enums;

/**
 * @Description: 分片上传状态
 * @Author: WangJieLong
 * @Date: 2023/5/21
 */
public enum FilePartUploadStatusEnum {
    
    DELETE(0, "删除"),
    
    INIT_FINISH(1, "初始化完成"),
    
    UPLOADING(2, "上传中"),
    
    FINISH(3, "上传完成"),
    
    ABORT(4, "取消");
    
    private final Integer val;
    
    private final String desc;
    
    FilePartUploadStatusEnum(int value, String desc) {
        this.val = value;
        this.desc = desc;
    }
    
    public static String getDescByVal(Integer val) {
        if (val == null) {
            return null;
        }
        for (FilePartUploadStatusEnum typeEnum : FilePartUploadStatusEnum.values()) {
            if (typeEnum.getVal().equals(val)) {
                return typeEnum.desc;
            }
        }
        return null;
    }
    
    public Integer getVal() {
        return val;
    }
    
    public String getDesc() {
        return desc;
    }
}
