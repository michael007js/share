package com.sss.share.lib.bean;


/**
 * 分享内容基类
 * Created by Administrator on 2019/1/11.
 */

public abstract class BaseShareContent {
    /**
     * 分享方式
     * @return
     */
    public abstract int getShareWay();

    /**
     * 分享内容
     * @return
     */
    public abstract String getContent();

    /**
     * 分享标题
     * @return
     */
    public abstract String getTitle();

    /**
     * 分享链接
     * @return
     */
    public abstract String getURL();

    /**
     * 分享图片
     * @return
     */
    public abstract String getPictureResource();

    /**
     * 默认的分享图片（当getPictureResource调用失败后调用的本地资源，微信专用，QQ不存在这个东西）
     * @return
     */
    public abstract int getDefaultPictureResource();

    /**
     * 小程序页面路径
     * @return
     */
    public abstract String miniProgramPagePath();
}
