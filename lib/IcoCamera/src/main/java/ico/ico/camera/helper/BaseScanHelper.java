package ico.ico.camera.helper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import ico.ico.camera.widget.CameraPreviewView;
import ico.ico.camera.widget.DetectView;

/**
 * 对{@link CameraPreviewView}和{@link DetectView}进行关联的帮助工具
 * <p>
 * 所有者通过{@link #requestPreviewFrame()}向{@link CameraPreviewView}请求帧数据
 * <p>
 * {@link CameraPreviewView}通过{@link #previewFrameHandler}将帧数据发送回来
 * <p>
 * {@link #previewFrameHandler}接收到帧数据后通过{@link OnScanListener#obtainPreviewFrame(byte[], int, int)}回传给所有者
 * <p>
 * 如果需要在UI上添加标签图片（比如人像、国徽）或别的东西，可以继承并重写{@link BaseScanHelper#onRegionChanged(boolean, float, float, float, float)}
 */
public class BaseScanHelper implements CameraPreviewView.OnCameraPreviewViewListener, DetectView.OnDetectRegionListener {
    /** 摄像头预览 */
    protected CameraPreviewView mCameraPreviewView;
    /** 检测方框 */
    protected DetectView mDetectView;

    /** 身份证扫描的总监听 */
    protected OnScanListener mOnScanListener;

    private final static int MSG_PREVIEW_FRAME = 0X100;

    /** 接收预览帧数据并调用回调 */
    Handler previewFrameHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            log.w("===previewFrameHandler.handleMessage");
            if (msg.what == MSG_PREVIEW_FRAME) {
                byte[] data = (byte[]) msg.obj;
                int width = msg.arg1;
                int height = msg.arg2;
                if (mOnScanListener != null)
                    mOnScanListener.obtainPreviewFrame(data, width, height);
            }
        }
    };

    public BaseScanHelper(CameraPreviewView cameraPreviewView, DetectView detectView, OnScanListener onScanListener) {
        this.mCameraPreviewView = cameraPreviewView;
        this.mDetectView = detectView;
        this.mOnScanListener = onScanListener;

        mCameraPreviewView.setOnCameraPreviewViewListener(this);
        mDetectView.setOnDetectRegionListener(this);
    }

    /**
     * 请求一次预览的帧数据
     */
    public void requestPreviewFrame() {
        if (mCameraPreviewView != null) {
            mCameraPreviewView.requestPreviewFrame(previewFrameHandler, MSG_PREVIEW_FRAME);
        }
    }

    //region DetectView.OnDetectRegionListener

    /**
     * 当检测框的边框坐标更改时回调，然后可以通过参数中4个边框坐标来放置要添加的标签图片(比如人像、国徽)的位置
     *
     * @param vertical 代表检测框是以什么方向显示的
     * @param l        左边框的x坐标
     * @param t        上边框的y坐标
     * @param r        右边框的x坐标
     * @param b        下边框的y坐标
     */
    @Override
    public void onRegionChanged(boolean vertical, float l, float t, float r, float b) {
    }
    //endregion

    //region CameraPreviewView.OnCameraPreviewViewListener
    @Override
    public void onError() {
        if (mOnScanListener != null) mOnScanListener.onError();
    }

    @Override
    public void onPreviewSizeChanged(int width, int height) {
        mDetectView.setPreviewSize(width, height);
    }
    //endregion

    public interface OnScanListener {
        /**
         * 摄像头预览开启失败回调
         */
        void onError();

        /**
         * 通过{@link BaseScanHelper#requestPreviewFrame()}，获取到预览帧后回调给后台
         *
         * @param data   帧数据
         * @param width  摄像头预览分辨率的宽度
         * @param height 摄像头预览分辨率的高度
         */
        void obtainPreviewFrame(byte[] data, int width, int height);
    }

    public void onDestroy() {
        // TODO: 2018/10/24
        this.mCameraPreviewView = null;
        this.mDetectView = null;
        this.mOnScanListener = null;
    }
}
