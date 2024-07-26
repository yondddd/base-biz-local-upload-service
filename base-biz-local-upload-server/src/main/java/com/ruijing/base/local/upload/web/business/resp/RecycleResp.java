package com.ruijing.base.local.upload.web.business.resp;

import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModel;
import com.ruijing.base.biz.api.server.api.rpc.annotation.RpcModelProperty;

import java.util.List;

/**
 * @Description: 回收结果
 * @Author: WangJieLong
 * @Date: 2023-07-11
 */
@RpcModel("回收结果")
public class RecycleResp {
    
    @RpcModelProperty("回收失败数据")
    private List<FailUrlResp> failData;
    @RpcModelProperty("入参的总url数")
    private Integer total;
    @RpcModelProperty("成功回收数量")
    private Integer success;
    
    
    public List<FailUrlResp> getFailData() {
        return failData;
    }
    
    public void setFailData(List<FailUrlResp> failData) {
        this.failData = failData;
    }
    
    public Integer getTotal() {
        return total;
    }
    
    public void setTotal(Integer total) {
        this.total = total;
    }
    
    public Integer getSuccess() {
        return success;
    }
    
    public void setSuccess(Integer success) {
        this.success = success;
    }
    
    @Override
    public String toString() {
        return "RecycleResp{" +
                "failData=" + failData +
                ", total=" + total +
                ", success=" + success +
                '}';
    }
}
