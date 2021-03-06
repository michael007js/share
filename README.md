# share
支持QQ、微信、小程序的分享，可一键调用

# 划重点:请记得配置各平台的AppID。
 
# 使用方式：
      
      1.在Application中加入如下代码：
                ShareHelper.getInstance().init(new ShareHelper.Builder()
                                .setContext(this)
                                .setRegisterShareType(ShareRegisterConstant.WEIXIN, ShareRegisterConstant.QQ,ShareRegisterConstant.MINI_PROGRAM)
                                .setWeixinAppID(微信AppId)
                                .setQQAppId(QQAppId)
                                .setMiniProgramId(小程序Id));

     2.在包名根目录下新建一个名为wxapi的包，再在该包下新建一个名为WXEntryActivity的类，并继承本库内的ShareHelperActivity

     3.在AndroidManifest.xml中加入如下代码：
         <activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:configChanges="orientation|keyboardHidden"
            android:screenOrientation="behind"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:launchMode="singleTask"
            android:noHistory="true"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data android:scheme="tencent1104984820" />
                <!-- 100380359 100381104 222222 -->
            </intent-filter>
        </activity>

        <activity
            android:name="包名，必须与AndroidManifest.xml中的一致.WXEntryActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />

     4.在activity中加入如下代码：
            
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                  Tencent.onActivityResultData(requestCode, resultCode, data, ShareHelper.getInstance().getUiListener());
                }


     至此，所有需要开发者动手的部分已经完成，后面就可以安心撸码了

# 调用demo：

    /**
     * QQ分享
     */
    public void qq(View view) {
        ShareHelper.getInstance().shareByQQ(
        this, 
        new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), 
        ShareConstant.SHARE_TYPE_QQ_SESSION, 
        new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 空间分享
     */
    public void qzone(View view) {
        ShareHelper.getInstance().shareByQQ(
        this, 
        new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), 
        ShareConstant.SHARE_TYPE_QQ_QZONE, 
        new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 微信分享
     */
    public void weixin(View view) {
        ShareHelper.getInstance().shareByWeChat(
        this, 
        new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), 
        ShareConstant.SHARE_TYPE_WECHAT_SESSION, 
        new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 微信收藏
     */
    public void favorites(View view) {
        ShareHelper.getInstance().shareByWeChat(
        this, 
        new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), 
        ShareConstant.SHARE_TYPE_WECHAT_FAVORITE, 
        new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 朋友圈分享
     */
    public void friendGroup(View view) {
        ShareHelper.getInstance().shareByWeChat(
        this, 
        new ShareContentWebpage("标题", "介绍", url, imageUrl, R.mipmap.ic_launcher), 
        ShareConstant.SHARE_TYPE_WECHAT_FRENDS_GROUP, 
        new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 小程序分享
     */
    public void miniProgram(View view) {
        ShareHelper.getInstance().shareByMiniProgram(
        this, 
        new ShareContentMiniProgram("标题", "介绍","兼容低版本的网页链接",imageUrl,"小程序页面地址，需要与小程序的程序猿沟通一下",R.mipmap.ic_launcher), 
        ShareConstant.SHARE_TYPE_WECHAT_SESSION, 
        new OnShareHelperListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onComplete() {

            }
        });
    }

# 调用方法:
     
     ShareHelper.getInstance().shareByWeChat//微信分享
     ShareHelper.getInstance().shareByQQ//QQ分享
     ShareHelper.getInstance().shareByMiniProgram//小程序分享（小程序分享的信息载体必须是ShareContentMiniProgram类型的对象，否则将无法唤起微信）


# 说明:
      
       1.分享类型
            BaseShareContent为所有分享类型的基类

            ShareContentMedia为媒体分享类型
            ShareContentMiniProgram为小程序分享类型
            ShareContentPicture为图片分享类型
            ShareContentText为文本分享类型
            ShareContentWebpage为网页分享类型

         2.回调
            这里要说的是微信分享，微信回调本库中采用的是eventBus事件，所以默认会采用compile方式集成一个eventBus的库，如有需要，开发者不需要再另外导入了

            特别强调一下：
                微信对新版微信客户端（6.7.2及以上版本）的返回规则做了调整，详情请参考 
                https://open.weixin.qq.com/cgi-bin/announce?spm=a311a.9588098.0.0&action=getannouncement&key=11534138374cE6li&version=
                所以本库对cancel和success的事件做了整合，如果有业务需要，则要开发者在WXEntryActivity中通过activity的生命周期或其他条件做判断

         3.图片logo
            这里采用了Glide的图片下载功能，具体封装在了BaseShareHelperImageGetter中，所以本库采用compile方式集成了Glide的库，如有需要，开发者不需要再另外导入了

# 混淆：
        -keep class com.eagersoft.youzy.youzy.share.bean.**{*;}

# By SSS
