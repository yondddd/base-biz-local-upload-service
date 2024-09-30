package com.ruijing.base.local.upload.web.console.console.controller;

import com.ruijing.base.local.upload.web.console.console.req.AccountLoginReq;
import com.ruijing.base.local.upload.web.console.console.resp.TokenResp;
import com.ruijing.fundamental.api.remote.RemoteResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 控制台账号
 * @Author: WangJieLong
 * @Date: 2024-09-23
 */
@RestController
@RequestMapping("/console/account")
public class ConsoleAccountController {
    
    @PostMapping("/login")
    public RemoteResponse<TokenResp> login(@RequestBody AccountLoginReq req) {
        TokenResp data = new TokenResp();
        return RemoteResponse.success(data);
    }
    
}
