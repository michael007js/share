package com.sss.share.lib.bean;


import com.sss.share.lib.ShareConstant;

/**
 * 小程序分享
 * Created by Administrator on 2019/1/14.
 */

public class ShareContentMiniProgram extends BaseShareContent {
    private String title;
    private String content;
    private String url;
    private String pictureResource;
    private String miniProgramPagePath;
    private int defaultThumbnail;

    public ShareContentMiniProgram(String title, String content, String url, String pictureResource, String miniProgramPagePath,int defaultThumbnail) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.pictureResource = pictureResource;
        this.miniProgramPagePath = miniProgramPagePath;
        this.defaultThumbnail=defaultThumbnail;
    }

    @Override
    public int getShareWay() {
        return ShareConstant.SHARE_WAY_MINI_PROGRAM;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getPictureResource() {
        return pictureResource;
    }

    @Override
    public int getDefaultPictureResource() {
        return defaultThumbnail;
    }

    @Override
    public String miniProgramPagePath() {
        return miniProgramPagePath;
    }
}
