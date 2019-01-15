package com.sss.share.lib;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

/**
 * 分享常量
 * Created by Administrator on 2019/1/11.
 */

public class ShareConstant {
    public static final int ACTION_SHARE_RESULT_WEIXIN = -0X01001A;//微信分享结果
    public static final int ERROR_WITHOUT_INIT = -10010;//没有初始化
    public static final int ERROR_WITHOUT_CLIENT = -10086;//没有安装客户端
    public static final int ERROR_WITHOUT_VERSION_SUPPORT = -10011;//版本不支持
    public static final int THUMB_SIZE = 100;//微信小logo大小
    public static final int SHARE_WAY_TEXT = 1;   //文字
    public static final int SHARE_WAY_PICTURE = 2; //图片
    public static final int SHARE_WAY_WEBPAGE = 3;  //链接
    public static final int SHARE_WAY_MEDIA = 4; //媒体
    public static final int SHARE_WAY_MINI_PROGRAM = 5; //小程序 TODO（注意：如果选中该类型，则不会有文字图片链接媒体的区分！）
    public static final int WECHAT_SHARE_TYPE_SESSION = SendMessageToWX.Req.WXSceneSession;  //微信会话
    public static final int WECHAT_SHARE_TYPE_FRENDS_GROUP = SendMessageToWX.Req.WXSceneTimeline; //微信朋友圈
    public static final int WECHAT_SHARE_TYPE_FAVORITE = SendMessageToWX.Req.WXSceneFavorite;  //微信收藏
    public static final int QQ_SHARE_TYPE_SESSION = 6;//QQ会话
    public static final int QQ_SHARE_TYPE_QZONE = 7;//QQ空间
}
