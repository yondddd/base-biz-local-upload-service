package com.ruijing.base.local.upload;

import com.ruijing.cat.springboot.autoconfigure.annotation.EnableCat;
import com.ruijing.fundamental.springboot.starter.ServiceBootApplication;
import com.ruijing.pearl.annotation.EnablePearl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * spring boot 启动类
 *
 * @author 锐竞-模板生成工具
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCat
@EnablePearl
public class AppRootApplication {
    
    public static void main(String[] args) {
        ServiceBootApplication.main(AppRootApplication.class, args);
    }
    
}
