package com.ruijing.base.local.upload.web.s3.metadata;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-05
 */
public class Metadata {
    
    private Meta meta;
    private Stat stat;
    
    public static Metadata custom() {
        return new Metadata();
    }
    
    public Meta getMeta() {
        return meta;
    }
    
    public Metadata setMeta(Meta meta) {
        this.meta = meta;
        return this;
    }
    
    public Stat getStat() {
        return stat;
    }
    
    public Metadata setStat(Stat stat) {
        this.stat = stat;
        return this;
    }
    
    @Override
    public String toString() {
        return "Metadata{" +
                "meta=" + meta +
                ", stat=" + stat +
                '}';
    }
}
