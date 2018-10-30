package com.example.wsq.library.okhttp.ssl;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * 处理https加载图片问题
 * 需配置
 * <meta-data
 * android:name="com.example.wsq.library.okhttp.OkHttpGlideModule"
 * android:value="GlideModule"/>
 */
@Deprecated
public class OkHttpGlideModule implements GlideModule {

    OkHttpClient mOkHttpClient;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Do nothing.
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {


//        HttpsUtils httpsUtils = new HttpsUtils();
        mOkHttpClient = HttpsUtils.getClient(mOkHttpClient);

        // builder.connectTimeout(20, TimeUnit.SECONDS);
        //  builder.readTimeout(20, TimeUnit.SECONDS);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(mOkHttpClient));
    }

}
