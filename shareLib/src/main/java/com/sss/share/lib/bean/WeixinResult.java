package com.sss.share.lib.bean;

/**
 * Created by Administrator on 2019/1/14.
 */

public class WeixinResult {
    public int errCode;
    public String errMsg;

    public WeixinResult(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
