package ico.ico.camera.helper;


import ico.ico.camera.util.IcoThread;

/**
 * 通过{@link OnDetectListener#requestPreviewFrame()}向所有者请求帧数据
 * <p>
 * 所有者获取到帧数据后通过{@link OnDetectListener#obtainDetectRegionForPreivew()}回传回来，进行识别
 * <p>
 * 如果识别失败，重新通过{@link OnDetectListener#requestPreviewFrame()}向所有者请求帧数据
 * <p>
 * 如果识别成功，通过{@link OnDetectListener#onDetectSuccess(Object)}将结果回调给所有者
 * <p>
 * 整个流程通过{@link #startDetect()}和{@link #stopDetect()}来启动和关闭
 */
public abstract class BaseDetectHelper {
    /** 用来解析帧数据的线程 */
    protected IcoThread mDetectThread;
    /** 临时存储的帧数据 */
    protected byte[] mData;
    /** 帧数据所对应的预览分辨率宽度 */
    protected int mPreviewWidth;
    /** 帧数据所对应的预览分辨率高度 */
    protected int mPreviewHeight;

    /** 构造函数，只保存监听器，不做任何操作 */
    public BaseDetectHelper(OnDetectListener onDetectListener) {
        if (onDetectListener == null)
            throw new NullPointerException("mOnDetectListener not be null");
        this.mOnDetectListener = onDetectListener;
    }

    /** 启动帧数据解析线程 */
    public void startDetect() {
        if (mDetectThread == null) {
            initDetectThread();
            mDetectThread.start();
        } else {
            if (mDetectThread.getState() != Thread.State.RUNNABLE) {
                synchronized (mDetectThread) {
                    mDetectThread.notify();
                }
            }
        }
    }

    /** 初始化sdk */
    protected abstract void initSdk();

    /** 释放sdk */
    protected abstract void releaseSdk();

    /** 初始化用于解析数据的线程 */
    protected abstract void initDetectThread();

    /** 停止线程 */
    public void stopDetect() {
        if (mDetectThread == null) return;
        mDetectThread.close();
        mDetectThread = null;
    }

    /** 添加帧数据，继续运行解析线程进行解析 */
    public void addDetect(byte[] data, int width, int height) {
        mData = data;
        mPreviewWidth = width;
        mPreviewHeight = height;
        mDetectThread.notifyContinue();
    }

    //region listener
    protected void notifyDetectSuccess(Object result) {
        if (mOnDetectListener == null) return;
        mOnDetectListener.onDetectSuccess(result);
        stopDetect();
    }

    protected void notifySdkError(int result) {
        if (mOnDetectListener == null) return;
        mOnDetectListener.onSdkError(result);
    }

    protected float[] obtainDetectRegionForPreivew() {
        if (mOnDetectListener == null) return null;
        return mOnDetectListener.obtainDetectRegionForPreivew();
    }

    protected void requestPreviewFrame() {
        if (mOnDetectListener == null) return;
        mOnDetectListener.requestPreviewFrame();
    }

    OnDetectListener mOnDetectListener;

    public interface OnDetectListener {
        /** 身份证识别组件初始化失败 */
        void onSdkError(int result);

        /** 基于预览分辨率的检测区域 */
        float[] obtainDetectRegionForPreivew();

        /** 请求预览帧数据 */
        void requestPreviewFrame();

        /** 识别成功 */
        void onDetectSuccess(Object result);
    }
    //endregion

    /** 销毁时调用，工作包括停止检测线程，释放sdk */
    public void onDestroy() {
        stopDetect();
//        releaseSdk();
        this.mOnDetectListener = null;
    }
}
