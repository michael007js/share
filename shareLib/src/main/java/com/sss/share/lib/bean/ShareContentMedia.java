package com.sss.share.lib.bean;


import com.sss.share.lib.ShareConstant;

/**
 * 媒体分享
 * Created by Administrator on 2019/1/11.
 */

public class ShareContentMedia extends BaseShareContent {
    private String url;
    private String thumbnail;
    private int defaultThumbnail;

    public ShareContentMedia(String url, String thumbnail,int defaultThumbnail) {
        this.url = url;
        this.thumbnail = thumbnail;
        this.defaultThumbnail=defaultThumbnail;
    }

    @Override
    public int getShareWay() {
        return ShareConstant.SHARE_WAY_MEDIA;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getPictureResource() {
        return thumbnail;
    }

    @Override
    public int getDefaultPictureResource() {
        return defaultThumbnail;
    }

    @Override
    public String miniProgramPagePath() {
        return null;
    }
}
