package com.nbank.study;

import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.dianping.logan.Logan;
import com.dianping.logan.LoganConfig;
import com.yitong.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.yitong.universalimageloader.core.DisplayImageOptions;
import com.yitong.universalimageloader.core.ImageLoader;
import com.yitong.universalimageloader.core.ImageLoaderConfiguration;
import com.yitong.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

import ico.ico.ico.AuthImageDownloader;
import ico.ico.ico.BaseApplication;
import ico.ico.util.Common;
import ico.ico.util.FileUtil;
import ico.ico.util.log;

public class MyApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        String filePath = FileUtil.genFilePath(Environment.getExternalStorageDirectory().getAbsolutePath(), "ico.trace");
        String filePath = "/storage/emulated/0/ico.trace";
//        log.w("===" + filePath);
//        Debug.startMethodTracing(filePath);
        LoganConfig config = new LoganConfig.Builder()
                .setCachePath(getApplicationContext().getFilesDir().getAbsolutePath())
                .setPath(getApplicationContext().getExternalFilesDir(null).getAbsolutePath()
                        + File.separator + "logan_v1")
                .setEncryptKey16("0123456789012345".getBytes())
                .setEncryptIV16("0123456789012345".getBytes())
                .build();
        Logan.init(config);

        long curr = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 999; i++) {
            sb.append("sad");
        }
        log.w("===" + (System.currentTimeMillis() - curr));
        curr = System.currentTimeMillis();
        FileUtil.writeFile(FileUtil.genFile(Environment.getExternalStorageDirectory().getAbsolutePath(), "test", "ss.txt"),
                sb.toString().getBytes(), true);
        log.w("===" + (System.currentTimeMillis() - curr));
//        Debug.stopMethodTracing();
//        Common.refreshMediaSync(this, filePath);

        initImageLoader();
    }

    private void initImageLoader() {
        //初始化imageloader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
//                .memoryCacheExtraOptions(480, 800) // 保存每个缓存图片的最大长和宽
                .threadPoolSize(3) // 线程池的大小 这个其实默认就是3
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置线程优先级
                .denyCacheImageMultipleSizesInMemory() // 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)// 设置缓存的最大字节
                .tasksProcessingOrder(QueueProcessingType.LIFO)//设置图片下载和显示的工作队列排序
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//                .imageDownloader(
//                        new BaseImageDownloader(getApplicationContext(),
//                                5 * 1000, 30 * 1000))// connectTimeout 超时时间
//                .writeDebugLogs()
//                .imageDownloader(new AuthImageDownloader(getApplicationContext()))//支持HTTPS协议
                .build();// 开始构建
        ImageLoader.getInstance().init(config);// 全局初始化此配置
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
    }
}
