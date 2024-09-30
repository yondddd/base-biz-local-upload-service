package com.ruijing.base.local.todo;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-08-16
 */
public class TodoList {
    
    /**
     * todo
     * 1. 加个切面统一打日志
     * 2. partupload in the tem path
     * 3. accesskey 才能访问,先有默认账号, 上去创建后可以用配置
     * 4. 地址配在配置中心
     * 5. 第二期再实现回收站
     * 6. 默认只用一个桶。不同业务怎么区分路径呢 怎么限制格式之类的
     * 一个目录下最多能存多少个文件 性能会变低吗 inode  默认用时间做目录 不区分业务了
     * 8. 分片上传要本地存条记录。存个kv?
     * 9. 如果单位可以开放外网 我们需要有个平台去配置这些 本地部署上传。通过不同的orgId去来取
     *  11 限制最大上传量
     *  12 xl.meta
     *  定时清理掉分片信息
     *  13 默认用ip  控制台配置用域名、防盗链. 控制台也用来测试上传是否正常
     *  14 k8s如何部署 挂载到目录
     *
     *  client级别隔离 不同client就代表不同环境 不要混用
     *  本地部署的换一种加密方式，有风险
     */
    
}
