package com.sss.share.lib.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sss.share.lib.ShareConstant;
import com.sss.share.lib.ShareHelper;
import com.sss.share.lib.bean.EventBusMessage;
import com.sss.share.lib.bean.WeixinResult;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2019/1/14.
 */

public class ShareHelperActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ShareHelper.getInstance().getWxApi().handleIntent(getIntent(), this);
            this.finish();
        } catch (Exception e) {
            this.finish();
        }
    }

    /**
     * 微信主动调用APP
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 微信回调
     * TODO 微信调整 见https://open.weixin.qq.com/cgi-bin/announce?spm=a311a.9588098.0.0&action=getannouncement&key=11534138374cE6li&version=
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                EventBus.getDefault().post(new EventBusMessage(ShareConstant.ACTION_SHARE_RESULT_WEIXIN, new WeixinResult(0, "success")));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                EventBus.getDefault().post(new EventBusMessage(ShareConstant.ACTION_SHARE_RESULT_WEIXIN, new WeixinResult(BaseResp.ErrCode.ERR_USER_CANCEL, baseResp.errStr)));
                break;
            default:
                EventBus.getDefault().post(new EventBusMessage(ShareConstant.ACTION_SHARE_RESULT_WEIXIN, new WeixinResult(baseResp.errCode, baseResp.errStr)));
                break;

        }

    }
}
