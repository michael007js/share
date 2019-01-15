package com.sss.share.lib.base;

import android.util.Log;

import com.sss.share.lib.ShareConstant;
import com.sss.share.lib.bean.EventBusMessage;
import com.sss.share.lib.bean.WeixinResult;
import com.sss.share.lib.listener.OnShareHelperListener;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 本层主要处理eventBus(为微信回调服务)
 * Created by Administrator on 2019/1/14.
 */

public abstract class BaseShareHelperAccept {
    protected OnShareHelperListener onShareHelperListener;//回调
    private boolean isInit = false;//是否初始化过

    protected void registerAcceptMessageForWeixin() {
        if (!isInit) {
            EventBus.getDefault().register(this);
            isInit = true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessage message) {
        if (ShareConstant.ACTION_SHARE_RESULT_WEIXIN == message.getTag()) {
            WeixinResult weixinResult = (WeixinResult) message.getMessage();
            Log.e("WXEntryActivity", weixinResult.errCode + "---" + weixinResult.errMsg);
            if (onShareHelperListener != null) {
                switch (weixinResult.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        onShareHelperListener.onComplete();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        onShareHelperListener.onCancel();
                        break;
                    default:
                        onShareHelperListener.onError(weixinResult.errCode, weixinResult.errMsg);
                }
            }
        }
    }
}
