package com.ruijing.base.local.upload.web.console.console.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-08-28
 */
public class BucketVO implements Serializable {
    
    private static final long serialVersionUID = 2767276407065109893L;
    
    private String name;
    private Date creationDate;
    
    public String getName() {
        return name;
    }
    
    public BucketVO setName(String name) {
        this.name = name;
        return this;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
    
    public BucketVO setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
    
    @Override
    public String toString() {
        return "BucketVO{" +
                "name='" + name + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
