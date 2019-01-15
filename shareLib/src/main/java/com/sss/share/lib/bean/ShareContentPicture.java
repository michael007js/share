package com.sss.share.lib.bean;


import com.sss.share.lib.ShareConstant;

/**
 * 图片分享
 * Created by Administrator on 2019/1/11.
 */

public class ShareContentPicture extends BaseShareContent {
    private String pictureResource;
    private int defaultThumbnail;

    public ShareContentPicture(String pictureResource, int defaultThumbnail) {
        this.pictureResource = pictureResource;
        this.defaultThumbnail = defaultThumbnail;
    }

    @Override
    public int getShareWay() {
        return ShareConstant.SHARE_WAY_PICTURE;
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
        return null;
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
