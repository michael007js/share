package com.sss.share.lib.listener;

import java.io.File;

/**
 * Created by Administrator on 2019/1/14.
 */

public interface SimpleCallBack    {

     void onNext(File file);

    void onError(Throwable throwable);
}
