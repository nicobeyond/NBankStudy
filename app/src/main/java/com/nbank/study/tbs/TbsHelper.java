package com.nbank.study.tbs;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsReaderView;
import com.tencent.smtt.sdk.ValueCallback;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import ico.ico.util.Common;
import ico.ico.util.log;

/**
 * Tbs服务帮助工具
 * <p>
 * Tbs接入相关笔记：﻿http://2d7eb962.wiz03.com/share/s/0JvHBy3NOASk2pFO7C39hO7i0OayWP0pqkyE2HYJrx0yF4v2
 */
public class TbsHelper {
    final static String TAG = TbsHelper.class.getName();
    /** 所需要的服务 */
    public final static String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    /**
     * 当前调用环境的上下文对象
     */
    private Context mContext;
    /**
     * 所有状态码集合
     * <p>
     * 以便通过int类型快速查询对应的状态文本
     */
    private HashMap<Integer, String> errCodeMap;
    /**
     * 监听本工具类使用的监听器
     */
    private TbsListener mTbsListener;

    /** 用于初始化的回调函数 调用者无需关心 */
    QbSdk.PreInitCallback mQbPreInitCallback;
    /** 用于初始化的回调函数 调用者无需关心 */
    com.tencent.smtt.sdk.TbsListener mQbTbsListener;

    public TbsHelper(Context context, TbsListener tbsListener) {
        this.mContext = context;
        this.mTbsListener = tbsListener;
        initErrCode();
        mQbPreInitCallback = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                log.w("onCoreInitFinished", TAG);
            }

            @Override
            public void onViewInitFinished(boolean b) {
                log.w("onViewInitFinished:" + b, TAG);
                if (b) {
                    notifyInitSuccess();
                } else {
                    notifyInitFail(-1);
                }
            }
        };
        mQbTbsListener = new com.tencent.smtt.sdk.TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                //日志打印
                if (errCodeMap == null) {
                    log.w("onDownloadFinish:" + i, TAG);
                } else {
                    log.w("onDownloadFinish:" + i + "|" + errCodeMap.get(Integer.valueOf(i)), TAG);
                }
                if (i != 100) {//﻿ERROR_CODE_DOWNLOAD_BASE
                    notifyInitFail(i);
                }
            }

            @Override
            public void onInstallFinish(int i) {
                //日志打印
                if (errCodeMap == null) {
                    log.w("onInstallFinish:" + i, TAG);
                } else {
                    log.w("onInstallFinish:" + i + "|" + errCodeMap.get(Integer.valueOf(i)), TAG);
                }
                if (i != 200) {//﻿﻿ERROR_CODE_INSTALL_BASE
                    notifyInitFail(i);
                }
            }

            @Override
            public void onDownloadProgress(int i) {
                log.w("onDownloadProgress:" + i, TAG);
                notifyDownloadProgress(i);
            }
        };
    }

    /**
     * 清除sdk内的下载标记
     * <p>
     * QbSdk在initX5Environment时会检查手机环境有没有微信或者Q浏览器，如果有共享使用他们的X5内核，如果没有将下载
     * <p>
     * 如果在wifi以外的环境下，下载将被停止，并在onDownloadFinish函数中返回111|NETWORK_NOT_WIFI_ERROR状态码
     * <p>
     * 再下次执行initX5Environment时将不会再进行X5内核的检查和下载工作
     * <p>
     * 清除下载标记可以在调用initX5Environment时再次对X5内核进行检查和下载
     */
    public void clearCoreDownloadFlag() {
        TbsDownloader.stopDownload();
        //TbsDownloader.b(mActivity);

        try {
            Method method = TbsDownloader.class.getDeclaredMethod("b", Context.class);
            method.setAccessible(true);
            method.invoke(TbsDownloader.class, mContext);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化QbSdk
     */
    public void init() {
        init(true);
    }

    /**
     * 初始化QbSdk
     *
     * @param onlyWifi 是否只在wifi状态下执行x5内核下载的工作
     */
    public void init(boolean onlyWifi) {
        log.w("initQbSdk is start", TAG);

        //检测是否已初始化，若已经初始化成功直接返回
        if (QbSdk.isTbsCoreInited()) {
            notifyInitSuccess();
            return;
        }
        //该函数将会同时清除已下载的x5内核
//        QbSdk.reset(mActivity);

        //清除标记以再次检查X5内核
        clearCoreDownloadFlag();

        //设置下载是否只在Wi-Fi下进行
        if (onlyWifi) {
            QbSdk.setDownloadWithoutWifi(false);
        } else {
            QbSdk.setDownloadWithoutWifi(true);
        }

        QbSdk.setTbsListener(mQbTbsListener);

        //初始化X5内核环境
        QbSdk.initX5Environment(mContext, mQbPreInitCallback);
    }

    /**
     * 应用外打开文件
     *
     * @param filePath 文件的位置
     */
    public void openFileOuter(String filePath) {
        QbSdk.openFileReader(mContext, filePath, null, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                log.w("onReceiveValue：" + s, TAG);
            }
        });
    }

    /** 应用内打开本地文件需要使用TbsReaderView，由于构造函数原因只能在code创建然后addView */
    TbsReaderView mTbsReaderView;

    /**
     * 应用内打开文档
     *
     * @param filePath
     */
    public void openFileInner(ViewGroup viewGroup, String filePath, String tempDir) throws IllegalAccessException {
        /* 初始化TbsReaderView */
        if (mTbsReaderView == null) {
            TbsReaderView _tbsReaderView = new TbsReaderView(mContext, new TbsReaderView.ReaderCallback() {
                @Override
                public void onCallBackAction(Integer integer, Object o, Object o1) {
                    log.w("onCallBackAction", TAG);
                }
            });
            viewGroup.addView(_tbsReaderView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mTbsReaderView = _tbsReaderView;
        } else {
            //如果TbsReaderView已存在，检测它的父容器是否与给定的容器相同
            if (mTbsReaderView.getParent() != viewGroup) {
                ((ViewGroup) mTbsReaderView.getParent()).removeView(mTbsReaderView);
                viewGroup.addView(mTbsReaderView);
            }
        }
        //检查文件是否受到支持, 不支持直接抛出异常
        String suffix = Common.getSuffix(filePath);
        boolean flag = mTbsReaderView.preOpen(Common.getSuffix(filePath), false);
        if (!flag) throw new IllegalAccessException(suffix + "文件格式不支持");
        /* 执行打开操作 */
        //构造数据包
        Bundle data = new Bundle();
        data.putString(TbsReaderView.KEY_FILE_PATH, filePath);
        data.putString(TbsReaderView.KEY_TEMP_PATH, tempDir);
        mTbsReaderView.openFile(data);
        //java.io.FileNotFoundException: /data/user/0/com.nbank.study/cache/optlist.ser (No such file or directory)
    }

    //region notify
    void notifyInitSuccess() {
        if (mTbsListener != null) {
            mTbsListener.onInitSuccess();
        }
    }

    void notifyInitFail(int statusCode) {
        if (mTbsListener != null) {
            mTbsListener.onInitFail(statusCode);
        }
    }

    void notifyDownloadProgress(int progress) {
        if (mTbsListener != null) {
            mTbsListener.onDownloadProgress(progress);
        }
    }
    //endregion


    /**
     * 返回状态码对应的文字信息
     *
     * @param statusCode 状态码
     * @return 对应的文字信息
     */
    public String getErrCodeMessage(int statusCode) {
        return errCodeMap.get(Integer.valueOf(statusCode));
    }


    /**
     * 初始化监听器的函数参数状态码，定义在{@link TbsListener.ErrorCode}内
     * <p>
     * 将ErrorCode中所有的错误代码全部转化存储到errCodeMap中
     */
    private void initErrCode() {
        if (errCodeMap == null) {
            //将ErrorCode中所有的错误代码全部存储到errCodeMap中
            try {
                Field[] _fields = com.tencent.smtt.sdk.TbsListener.ErrorCode.class.getDeclaredFields();
                if (_fields == null) {
                    throw new NullPointerException("无法通过反射获取到ErrorCode下所有的静态变量");
                }
                errCodeMap = new HashMap<>();
                for (int j = 0; j < _fields.length; j++) {
                    _fields[j].setAccessible(true);
                    errCodeMap.put(Integer.valueOf(_fields[j].getInt(com.tencent.smtt.sdk.TbsListener.ErrorCode.class)), _fields[j].getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
                errCodeMap = null;
            }
        }
    }

    public interface TbsListener {

        void onInitSuccess();

        void onInitFail(int statusCode);

        void onDownloadProgress(int progress);
    }

    /** 上下文销毁时请调用此函数 */
    public void onDestory() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
