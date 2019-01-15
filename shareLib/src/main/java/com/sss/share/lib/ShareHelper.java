package com.sss.share.lib;

import android.content.Context;
import android.util.Log;

import com.sss.share.lib.base.BaseShareHelperThirdSDK;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 本层主要构建必须参数并初始化对象
 * Created by Administrator on 2019/1/11.
 */

/*
    todo QQ回调请copy以下代码至Activity中：
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      Tencent.onActivityResultData(requestCode, resultCode, data, ShareHelper.getInstance().getUiListener());
    }

    todo 微信回调接受了一个ACTION_SHARE_RESULT_WEIXIN的eventBus事件，请参考BaseShareHelperAccept类以及ShareHelperActivity中的onResp回调

* */
public class ShareHelper extends BaseShareHelperThirdSDK {
    private static ShareHelper instance;//instance


    public static ShareHelper getInstance() {
        if (instance == null) {
            instance = new ShareHelper();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param builder
     */
    public void init(Builder builder) {
        for (int i = 0; i < builder.registerShareType.length; i++) {
            if (ShareRegisterConstant.WEIXIN == builder.registerShareType[i]) {
                if (wxApi == null) {
                    // 通过WXAPIFactory工厂，获取IWXAPI的实例
                    wxApi = WXAPIFactory.createWXAPI(builder.context, builder.weixinAppID, true/*是否需要检查签名*/);
                    // 将应用的appId注册到微信
                    wxApi.registerApp(builder.weixinAppID);
                } else {
                    Log.e("SSS","请勿重复初始化微信");
                }
            } else if (ShareRegisterConstant.QQ == builder.registerShareType[i]) {
                if (tencent == null) {
                    tencent = Tencent.createInstance(builder.qqAppId, builder.context);
                    iUiListener = new IUiListener() {
                        @Override
                        public void onComplete(Object o) {
                            if (onShareHelperListener != null) {
                                onShareHelperListener.onComplete();
                            }

                        }

                        @Override
                        public void onError(UiError uiError) {
                            if (onShareHelperListener != null) {
                                onShareHelperListener.onError(uiError.errorCode, uiError.errorMessage);
                            }
                        }

                        @Override
                        public void onCancel() {
                            if (onShareHelperListener != null) {
                                onShareHelperListener.onCancel();
                            }
                        }
                    };
                }
            } else if (ShareRegisterConstant.MINI_PROGRAM == builder.registerShareType[i]) {
                super.miniProgramId = builder.miniProgramId;
                if (wxApi == null) {
                    // 通过WXAPIFactory工厂，获取IWXAPI的实例
                    wxApi = WXAPIFactory.createWXAPI(builder.context, builder.weixinAppID, true/*是否需要检查签名*/);
                    // 将应用的appId注册到微信
                    wxApi.registerApp(builder.weixinAppID);
                } else {
                    Log.e("SSS","请勿重复初始化微信");
                }
            }
        }

    }


    public static class Builder {
        private String weixinAppID = "";//微信AppId
        private String weixinAppSecret = "";//微信秘钥
        private String qqAppId = "";//QQAppId
        private String qqAppSecret = "";//QQ秘钥
        private String miniProgramId = "";//小程序ID
        private Context context;//你懂的
        private ShareRegisterConstant[] registerShareType;//注册分享的类型，参考ShareRegisterConstant

        public Builder setWeixinAppID(String weixinAppID) {
            this.weixinAppID = weixinAppID;
            return this;
        }

        public Builder setWeixinAppSecret(String weixinAppSecret) {
            this.weixinAppSecret = weixinAppSecret;
            return this;
        }

        public Builder setQQAppId(String qqAppId) {
            this.qqAppId = qqAppId;
            return this;
        }

        public Builder setQQAppSecret(String qqAppSecret) {
            this.qqAppSecret = qqAppSecret;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setRegisterShareType(ShareRegisterConstant... registerShareType) {
            this.registerShareType = registerShareType;
            return this;
        }

        public Builder setMiniProgramId(String miniProgramId) {
            this.miniProgramId = miniProgramId;
            return this;
        }
    }
}
