package com.ruijing.base.local.upload;

import com.ruijing.fundamental.remoting.msharp.annotation.GateWayController;
import com.ruijing.fundamental.remoting.msharp.annotation.Ping;

/**
 * ping检测类
 * @author 锐竞-模板生成工具
 **/
@GateWayController(requestMapping = "/")
public class PingController {

    @Ping
    public String ping() {
        return "pong";
    }
}