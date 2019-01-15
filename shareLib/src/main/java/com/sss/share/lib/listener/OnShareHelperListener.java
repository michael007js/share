package com.sss.share.lib.listener;


/**
 * 分享回调
 * Created by Administrator on 2019/1/14.
 */

public interface OnShareHelperListener {

    void onStart();

    void onError(int errorCode, String errorMessage);

    void onCancel();

    void onComplete();

}
