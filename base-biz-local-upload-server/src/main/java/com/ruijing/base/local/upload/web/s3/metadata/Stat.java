package com.ruijing.base.local.upload.web.s3.metadata;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-05
 */
public class Stat {
    
    private String size;
    private String lastModified;
    
    public static Stat custom() {
        return new Stat();
    }
    
    public String getSize() {
        return size;
    }
    
    public Stat setSize(String size) {
        this.size = size;
        return this;
    }
    
    public String getLastModified() {
        return lastModified;
    }
    
    public Stat setLastModified(String lastModified) {
        this.lastModified = lastModified;
        return this;
    }
    
    @Override
    public String toString() {
        return "Stat{" +
                "size=" + size +
                ", lastModified=" + lastModified +
                '}';
    }
}
