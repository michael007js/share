package com.sss.share.lib.bean;


import com.sss.share.lib.ShareConstant;

/**
 * 文字分享
 * Created by Administrator on 2019/1/11.
 */

public class ShareContentText extends BaseShareContent {
    private String text;

    public ShareContentText(String text) {
        this.text = text;
    }

    @Override
    public int getShareWay() {
        return ShareConstant.SHARE_WAY_TEXT;
    }

    @Override
    public String getContent() {
        return text;
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
        return null;
    }

    @Override
    public int getDefaultPictureResource() {
        return 0;
    }

    @Override
    public String miniProgramPagePath() {
        return null;
    }
}
