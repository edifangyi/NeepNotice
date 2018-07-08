package com.fangyi.component_library.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;

import com.fangyi.component_library.BuildConfig;
import com.fangyi.component_library.config.AppConfig;
import com.fangyi.component_library.utils.kalle.JsonConverter;
import com.tencent.smtt.sdk.QbSdk;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;
import com.yanzhenjie.kalle.connect.BroadcastNetwork;
import com.yanzhenjie.kalle.connect.http.LoggerInterceptor;
import com.yanzhenjie.kalle.cookie.DBCookieStore;
import com.yanzhenjie.kalle.simple.cache.DiskCacheStore;
import com.yanzhenjie.kalle.urlconnect.URLConnectionFactory;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/5/22
 * ================================================
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication mInstance;
    public static Context mContext;

    public static BaseApplication getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mContext = this.getApplicationContext();

        if (!BuildConfig.isRelease) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);


        Kalle.setConfig(KalleConfig.newBuilder()
                .connectFactory(URLConnectionFactory.newBuilder().build())
                .cookieStore(DBCookieStore.newBuilder(this).build())
                .cacheStore(DiskCacheStore.newBuilder(AppConfig.get().PATH_APP_CACHE).build())
                .network(new BroadcastNetwork(this))
                .addInterceptor(new LoggerInterceptor("KalleSample", BuildConfig.DEBUG))
                .converter(new JsonConverter(this))
                .build());

//
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is " + arg0);
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
//        //x5内核初始化接口
//        QbSdk.initX5Environment(getApplicationContext(), cb);

    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
