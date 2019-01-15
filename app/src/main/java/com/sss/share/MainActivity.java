package com.sss.share;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sss.share.lib.ShareConstant;
import com.sss.share.lib.ShareHelper;
import com.sss.share.lib.bean.ShareContentMiniProgram;
import com.sss.share.lib.bean.ShareContentWebpage;
import com.sss.share.lib.listener.OnShareHelperListener;

public class MainActivity extends AppCompatActivity {
    private String imageUrl="http://img.desktx.com:8089/d/file/wallpaper/comic/20180528/5860af8d5f8ee137e2ecb4f455f0a4fa.jpg";
    private String url="https://www.baidu.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * QQ分享
     */
    public void qq(View view) {
        ShareHelper.getInstance().shareByQQ(this, new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), ShareConstant.SHARE_TYPE_QQ_SESSION, new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 空间分享
     */
    public void qzone(View view) {
        ShareHelper.getInstance().shareByQQ(this, new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), ShareConstant.SHARE_TYPE_QQ_QZONE, new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 微信分享
     */
    public void weixin(View view) {
        ShareHelper.getInstance().shareByWeChat(this, new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), ShareConstant.SHARE_TYPE_WECHAT_SESSION, new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 微信收藏
     */
    public void favorites(View view) {
        ShareHelper.getInstance().shareByWeChat(this, new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), ShareConstant.SHARE_TYPE_WECHAT_FAVORITE, new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 朋友圈分享
     */
    public void friendGroup(View view) {
        ShareHelper.getInstance().shareByWeChat(this, new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), ShareConstant.SHARE_TYPE_WECHAT_FRENDS_GROUP, new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 小程序分享
     */
    public void miniProgram(View view) {
        ShareHelper.getInstance().shareByMiniProgram(this, new ShareContentMiniProgram("标题", "介绍","兼容低版本的网页链接",imageUrl,"小程序页面地址，需要与小程序的程序猿沟通一下",R.mipmap.ic_launcher), ShareConstant.SHARE_TYPE_WECHAT_SESSION, new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
