package com.sss.share.lib.bean;


import com.sss.share.lib.ShareConstant;

/**
 * 链接分享
 * Created by Administrator on 2019/1/11.
 */

public class ShareContentWebpage extends BaseShareContent {
    private String title;
    private String content;
    private String url;
    private String pictureResource;
    private int defaultThumbnail;

    public ShareContentWebpage(String title, String content, String url, String pictureResource, int defaultThumbnail) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.pictureResource = pictureResource;
        this.defaultThumbnail = defaultThumbnail;
    }

    @Override
    public int getShareWay() {
        return ShareConstant.SHARE_WAY_WEBPAGE;
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
        return null;
    }
}
