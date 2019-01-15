package com.sss.share.lib.base;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.sss.share.lib.listener.SimpleCallBack;

import java.io.File;
import java.util.concurrent.Future;

/**
 * 本层处理图片下载，主要为微信服务，借用的Glide下载，需要在子线程中调用，故new了一个Thread
 * Created by Administrator on 2019/1/14.
 */

public abstract class BaseShareHelperImageGetter extends BaseShareHelperAccept {
    private Thread thread;

    /**
     * 下载图片
     *
     * @param activity 你懂的
     * @param url      下载地址
     * @param width    下载到本地的图片宽度
     * @param height   下载到本地的图片高度
     * @param callBack 回调
     */
    protected void downloadImage(final Activity activity, final String url, final int width, final int height, final SimpleCallBack callBack) {
        clearThread();
        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Future<File> futureTarget = Glide.with(activity)
                        .load(url)
                        .downloadOnly(width, height);
                try {
                    final File file= futureTarget.get();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onNext(file);
                            }
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onError(e);
                            }
                        }
                    });
                }
            }
        };
        thread.start();
    }

    /**
     * 清理线程
     */
    private void clearThread() {
        if (thread != null && !thread.isInterrupted()) {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
        thread = null;
    }

}
