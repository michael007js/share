package com.sss.share.lib.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.bumptech.glide.util.Util;
import com.sss.share.lib.ShareConstant;
import com.sss.share.lib.ShareUtil;
import com.sss.share.lib.bean.BaseShareContent;
import com.sss.share.lib.listener.OnShareHelperListener;
import com.sss.share.lib.listener.SimpleCallBack;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.io.File;
import java.util.ArrayList;

/**
 * 本层主要调用第三方SDK
 * Created by Administrator on 2019/1/11.
 */

public class BaseShareHelperThirdSDK extends BaseShareHelperImageGetter {
    protected IWXAPI wxApi;//和微信通信的openApi接口
    protected Tencent tencent;//和QQ通信的openApi接口
    protected IUiListener iUiListener;//QQ回调
    protected String miniProgramId = "";//小程序ID

    public IUiListener getUiListener() {
        return iUiListener;
    }

    public IWXAPI getWxApi() {
        return wxApi;
    }

    /**
     * 通过微信分享
     *
     * @param shareContent 分享的方式（文本、图片、链接）
     * @param shareType    分享的类型（朋友圈，会话）
     */
    public void shareByWeChat(final Activity activity, final BaseShareContent shareContent, final int shareType, OnShareHelperListener onShareHelperListener) {
        super.onShareHelperListener = onShareHelperListener;
        if (super.onShareHelperListener != null) {
            super.onShareHelperListener.onStart();
        }
        if (wxApi == null) {
            if (super.onShareHelperListener != null) {
                super.onShareHelperListener.onError(ShareConstant.ERROR_WITHOUT_INIT, "未初始化微信分享");
            }
            return;
        }
        if (!wxApi.isWXAppInstalled()) {
            if (super.onShareHelperListener != null) {
                super.onShareHelperListener.onError(ShareConstant.ERROR_WITHOUT_CLIENT, "您手机尚未安装微信，请安装后再试");
            }
            return;
        }
        if (wxApi.getWXAppSupportAPI() < Build.TIMELINE_SUPPORTED_SDK_INT) {
            if (super.onShareHelperListener != null) {
                super.onShareHelperListener.onError(ShareConstant.ERROR_WITHOUT_VERSION_SUPPORT, "您安装的微信版本不支持分享，请更新后再试");
            }
            return;
        }
        registerAcceptMessageForWeixin();
        switch (shareContent.getShareWay()) {
            case ShareConstant.SHARE_WAY_TEXT:
                shareTextWX(shareContent, shareType);
                break;
            case ShareConstant.SHARE_WAY_PICTURE:


                downloadImage(activity, shareContent.getPictureResource(), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE, new SimpleCallBack() {
                    @Override
                    public void onError(Throwable throwable) {
                        sharePictureWX(activity, shareContent, shareType, null);
                    }

                    @Override
                    public void onNext(File file) {
                        sharePictureWX(activity, shareContent, shareType, file);
                    }
                });
                break;
            case ShareConstant.SHARE_WAY_WEBPAGE:
                downloadImage(activity, shareContent.getPictureResource(), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE, new SimpleCallBack() {
                    @Override
                    public void onError(Throwable throwable) {
                        shareWebPageWX(activity, shareContent, shareType, null);
                    }

                    @Override
                    public void onNext(File file) {
                        shareWebPageWX(activity, shareContent, shareType, file);
                    }
                });
                break;
            case ShareConstant.SHARE_WAY_MEDIA:
                downloadImage(activity, shareContent.getPictureResource(), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE, new SimpleCallBack() {
                    @Override
                    public void onError(Throwable throwable) {
                        shareVideoWX(activity, shareContent, shareType, null);
                    }

                    @Override
                    public void onNext(File file) {
                        shareVideoWX(activity, shareContent, shareType, file);
                    }
                });
                break;
        }
    }

    /**
     * 通过小程序分享
     *
     * @param activity              你懂的
     * @param shareContent          分享的方式（文本、图片、链接）
     * @param shareType             分享的类型（朋友圈，会话）
     * @param onShareHelperListener
     */
    public void shareByMiniProgram(final Activity activity, final BaseShareContent shareContent, final int shareType, OnShareHelperListener onShareHelperListener) {
        super.onShareHelperListener = onShareHelperListener;
        if (super.onShareHelperListener != null) {
            super.onShareHelperListener.onStart();
        }
        if (wxApi == null) {
            if (super.onShareHelperListener != null) {
                super.onShareHelperListener.onError(ShareConstant.ERROR_WITHOUT_INIT, "未初始化微信分享");
            }
            return;
        }
        if (!wxApi.isWXAppInstalled()) {
            if (super.onShareHelperListener != null) {
                super.onShareHelperListener.onError(ShareConstant.ERROR_WITHOUT_CLIENT, "您手机尚未安装微信，请安装后再试");
            }
            return;
        }
        if (wxApi.getWXAppSupportAPI() < Build.TIMELINE_SUPPORTED_SDK_INT) {
            if (super.onShareHelperListener != null) {
                super.onShareHelperListener.onError(ShareConstant.ERROR_WITHOUT_VERSION_SUPPORT, "您安装的微信版本不支持分享，请更新后再试");
            }
            return;
        }
        registerAcceptMessageForWeixin();
        downloadImage(activity, shareContent.getPictureResource(), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE, new SimpleCallBack() {
            @Override
            public void onError(Throwable throwable) {
                shareToMiniProgram(activity, shareContent, shareType, null);
            }

            @Override
            public void onNext(File file) {
                shareToMiniProgram(activity, shareContent, shareType, file);
            }
        });
    }


    /**
     * 通过QQ分享
     *
     * @param activity     你懂的
     * @param shareContent 分享的方式（文本、图片、链接）
     * @param shareType    分享的类型（QQ空间，会话）
     */
    public void shareByQQ(Activity activity, BaseShareContent shareContent, int shareType, OnShareHelperListener onShareHelperListener) {
        super.onShareHelperListener = onShareHelperListener;
        if (super.onShareHelperListener != null) {
            super.onShareHelperListener.onStart();
        }
        if (tencent == null) {
            if (super.onShareHelperListener != null) {
                super.onShareHelperListener.onError(ShareConstant.ERROR_WITHOUT_INIT, "未初始化QQ分享");
            }
            return;
        }
        if (!ShareUtil.isQQClientAvailable(activity)) {
            if (super.onShareHelperListener != null) {
                super.onShareHelperListener.onError(ShareConstant.ERROR_WITHOUT_CLIENT, "您手机尚未安装QQ，请安装后再试");
            }
            return;
        }
        switch (shareContent.getShareWay()) {
            case ShareConstant.SHARE_WAY_TEXT:
                shareTextQQ(activity, shareContent, shareType);
                break;
            case ShareConstant.SHARE_WAY_PICTURE:
                sharePictureQQ(activity, shareContent, shareType);
                break;
            case ShareConstant.SHARE_WAY_WEBPAGE:
                shareWebPageQQ(activity, shareContent, shareType);
                break;
            case ShareConstant.SHARE_WAY_MEDIA:
                shareMediaQQ(activity, shareContent, shareType);
                break;
        }
    }


    /**
     * 分享QQ文字
     */
    private void shareTextQQ(Activity activity, BaseShareContent shareContent, int shareType) {
        Bundle params = new Bundle();
        switch (shareType) {
            case ShareConstant.QQ_SHARE_TYPE_SESSION:
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());// 标题
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.getContent());// 摘要
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.getURL());// 内容地址
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, ShareUtil.getAppName(activity));//应用名称
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareContent.getPictureResource());// 网络图片地址
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);//1是qq空间，0是好友
                break;
            case ShareConstant.QQ_SHARE_TYPE_QZONE:
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());// 标题
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.getContent());// 摘要
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.getURL());// 内容地址
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 1);//1是qq空间，0是好友
                ArrayList<String> imgUrlList = new ArrayList<>();
                imgUrlList.add(shareContent.getPictureResource());
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
                break;
        }
        tencent.shareToQQ(activity, params, iUiListener);
    }

    /**
     * 分享QQ图片
     */
    private void sharePictureQQ(Activity activity, BaseShareContent shareContent, int shareType) {
        Bundle params = new Bundle();
        switch (shareType) {
            case ShareConstant.QQ_SHARE_TYPE_SESSION:
                params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);// 设置分享类型为纯图片分享
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareContent.getPictureResource());// 需要分享的本地图片URL
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);//1是qq空间，0是好友
                break;
            case ShareConstant.QQ_SHARE_TYPE_QZONE:
                ArrayList<String> imgUrlList = new ArrayList<>();
                imgUrlList.add(shareContent.getPictureResource());
                params = new Bundle();
                params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
                params.putString(QzonePublish.PUBLISH_TO_QZONE_SUMMARY, shareContent.getContent());
                params.putStringArrayList(QzonePublish.PUBLISH_TO_QZONE_IMAGE_URL,
                        imgUrlList);// 图片地址ArrayList
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 1);//1是qq空间，0是好友
                break;
        }
        tencent.shareToQQ(activity, params, iUiListener);
    }

    /**
     * 分享QQ链接
     */
    private void shareWebPageQQ(Activity activity, BaseShareContent shareContent, int shareType) {
        Bundle params = new Bundle();
        ArrayList<String> imageUrls = new ArrayList<>();
        switch (shareType) {
            case ShareConstant.QQ_SHARE_TYPE_SESSION:
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());//分享标题
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.getContent());//要分享的内容摘要
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.getURL());//内容地址
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, ShareUtil.getAppName(activity));//应用名称
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);//1是qq空间，0是好友
                //分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）
                imageUrls.add(shareContent.getPictureResource());//添加一个图片地址
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);//分享的图片URL
                break;
            case ShareConstant.QQ_SHARE_TYPE_QZONE:
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE);//分享的类型
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());//分享标题
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, ShareUtil.getAppName(activity));//应用名称
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.getContent());//分享的内容摘要
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.getURL());//分享的链接
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 1);//1是qq空间，0是好友
                //分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）
                imageUrls.add(shareContent.getPictureResource());//添加一个图片地址
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);//分享的图片URL
                break;
        }
        tencent.shareToQQ(activity, params, iUiListener);
    }

    /**
     * 分享QQ媒体
     */
    private void shareMediaQQ(Activity activity, BaseShareContent shareContent, int shareType) {
        Bundle params = new Bundle();
        ArrayList<String> imageUrls = new ArrayList<>();
        switch (shareType) {
            case ShareConstant.QQ_SHARE_TYPE_SESSION:
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());//分享标题
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.getContent());//要分享的内容摘要
                params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, shareContent.getURL());//媒体地址
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, ShareUtil.getAppName(activity));//应用名称
                //分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）
                imageUrls.add(shareContent.getPictureResource());//添加一个图片地址
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);//分享的图片URL
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);//1是qq空间，0是好友
                break;
            case ShareConstant.QQ_SHARE_TYPE_QZONE:
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO);//分享的类型
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());//分享标题
                params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, shareContent.getURL());//媒体地址
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, ShareUtil.getAppName(activity));//应用名称
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.getContent());//分享的内容摘要
                //分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）
                imageUrls.add(shareContent.getPictureResource());//添加一个图片地址
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);//分享的图片URL
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 1);//1是qq空间，0是好友
                break;
        }
        tencent.shareToQQ(activity, params, iUiListener);
    }

    /**
     * 分享微信文字
     */
    private void shareTextWX(BaseShareContent shareContent, int shareType) {
        String text = shareContent.getContent();
        //初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        //用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求
        req.transaction = ShareUtil.buildTransaction("textshare");
        req.message = msg;
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
        req.scene = shareType;
        wxApi.sendReq(req);
    }

    /**
     * 分享微信图片
     */
    private void sharePictureWX(Activity activity, BaseShareContent shareContent, int shareType, File file) {
        Bitmap bitmap = file == null ? ShareUtil.getThumbnail(null, activity, shareContent.getDefaultPictureResource()) : ShareUtil.decodeSampledBitmapFromFile(file.getAbsolutePath(), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE);
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.thumbData = ShareUtil.bmpToByteArray(bitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = ShareUtil.buildTransaction("imgshareappdata");
        req.message = msg;
        req.scene = shareType;
        wxApi.sendReq(req);
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    /**
     * 分享微信链接
     */
    private void shareWebPageWX(Activity activity, BaseShareContent shareContent, int shareType, File file) {
        Bitmap bitmap = file == null ? ShareUtil.getThumbnail(null, activity, shareContent.getDefaultPictureResource()) : Bitmap.createScaledBitmap(ShareUtil.decodeSampledBitmapFromFile(file.getAbsolutePath(), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE, true);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareContent.getURL();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareContent.getContent();
        msg.description = shareContent.getTitle();
        if (shareContent.getPictureResource() != null) {
            msg.thumbData = ShareUtil.bmpToByteArray(bitmap, true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = ShareUtil.buildTransaction("webpage");
        req.message = msg;
        req.scene = shareType;
        wxApi.sendReq(req);
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    /**
     * 分享微信媒体
     */
    private void shareVideoWX(Activity activity, BaseShareContent shareContent, int shareType, File file) {
        Bitmap bitmap = file == null ? ShareUtil.getThumbnail(null, activity, shareContent.getDefaultPictureResource()) : Bitmap.createScaledBitmap(ShareUtil.decodeSampledBitmapFromFile(file.getAbsolutePath(), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE, true);
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = shareContent.getURL();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();
        /**
         * 测试过程中会出现这种情况，会有个别手机会出现调不起微信客户端的情况。造成这种情况的原因是微信对缩略图的大小、title、description等参数的大小做了限制，所以有可能是大小超过了默认的范围。
         * 一般情况下缩略图超出比较常见。Title、description都是文本，一般不会超过。
         */
        msg.setThumbImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = ShareUtil.buildTransaction("video");
        req.message = msg;
        req.scene = shareType;
        wxApi.sendReq(req);
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    /**
     * 分享到小程序
     */
    private void shareToMiniProgram(Activity activity, BaseShareContent shareContent, int shareType, File file) {
        Bitmap bitmap = file == null ? ShareUtil.getThumbnail(null, activity, shareContent.getDefaultPictureResource()) : ShareUtil.decodeSampledBitmapFromFile(file.getAbsolutePath(), ShareConstant.THUMB_SIZE, ShareConstant.THUMB_SIZE);
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "https://m.youzy.cn";                             // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = miniProgramId;                                      // 小程序原始id,在微信平台查询
        miniProgramObj.path = shareContent.miniProgramPagePath();                     //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = shareContent.getTitle();                                          // 小程序消息title
        msg.description = shareContent.getContent();                                  // 小程序消息desc
        msg.thumbData = ShareUtil.bmpToByteArray(bitmap, true);               // 小程序消息封面图片，小于128k
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = ShareUtil.buildTransaction("webpage");                          //transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = shareType;                                                        // 目前仅支持会话，无论传什么样的类型，微信都会默认为session
        wxApi.sendReq(req);
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

}
