package com.ruijing.base.local.upload.manager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @Description: init
 * @Author: WangJieLong
 * @Date: 2024-05-28
 */
@Component
public class InitManager implements InitializingBean {
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
        initSystemMetaData();
    }
    
    
    // 初始化系统元数据
    private void initSystemMetaData() {
    
    }
    
    
    
}
