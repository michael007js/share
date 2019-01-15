package com.sss.share;

import android.app.Application;

import com.sss.share.lib.ShareHelper;
import com.sss.share.lib.ShareRegisterConstant;

/**
 * Created by Administrator on 2019/1/15.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareHelper.getInstance().init(new ShareHelper.Builder()
                .setContext(this)
                .setRegisterShareType(ShareRegisterConstant.WEIXIN, ShareRegisterConstant.QQ, ShareRegisterConstant.MINI_PROGRAM)
                .setWeixinAppID("微信AppId")
                .setQQAppId("QQAppId")
                .setMiniProgramId("小程序Id"));
    }
}
