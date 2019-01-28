package ico.ico.camera.widget;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ico.ico.camera.CameraManager;


public class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreviewView";

    public final static int ERROR_OPEN = 0x001;
    public final static int ERROR_AUTOOCUS = 0x001;
    OnCameraPreviewViewListener mOnCameraPreviewViewListener;

    public CameraPreviewView(Context context) {
        super(context);
        init();
    }

    public CameraPreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraPreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CameraPreviewView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
    }

    //region 请求获取预览帧数据
    /**
     * 在{@link CameraManager#requestPreviewFrame}时可能因为{@link CameraManager}还没有初始化导致获取不到
     * <p>
     * 所以设置一个标记用来在初始化{@link CameraManager}后再一次调用{@link CameraManager#requestPreviewFrame}
     */
    boolean isRestartRequestPreviewFrame = false;
    Runnable restartRequestPreviewFrameTask;

    /**
     * 请求一次预览的帧数据，通过handler进行传递数据，arg0和arg1是预览分辨率大小，obj是具体的帧数据
     *
     * @param handler 接收数据的handler
     * @param message 标识帧数据的消息标识
     */
    public void requestPreviewFrame(final Handler handler, final int message) {
        if (mCameraManager == null || !mCameraManager.isOpen()) {
            isRestartRequestPreviewFrame = true;
            restartRequestPreviewFrameTask = new Runnable() {
                @Override
                public void run() {
                    requestPreviewFrame(handler, message);
                }
            };
            return;
        }
        mCameraManager.requestPreviewFrame(handler, message);
    }
    //endregion

    //region surface listener
    CameraManager mCameraManager;

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        CameraManager cameraManager = null;
        if (mCameraManager == null) {
            cameraManager = new CameraManager(getContext().getApplicationContext());
        } else {
            cameraManager = mCameraManager;
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            cameraManager.startPreview();
            mCameraManager = cameraManager;
            if (isRestartRequestPreviewFrame) {
                isRestartRequestPreviewFrame = false;
                Runnable run = restartRequestPreviewFrameTask;
                restartRequestPreviewFrameTask = null;
                post(run);
            }
            notifyPreviewSizeChanged(mCameraManager.getPreviewSize().width, mCameraManager.getPreviewSize().height);
        } catch (Exception e) {
//            e.printStackTrace();
            notifyError();
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
        if (mCameraManager != null) {
            mCameraManager.initCameraParameter(surfaceHolder);
            notifyPreviewSizeChanged(mCameraManager.getPreviewSize().width, mCameraManager.getPreviewSize().height);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCameraManager != null && mCameraManager.isOpen()) {
            mCameraManager.stopPreview();
            mCameraManager.closeDriver();
            mCameraManager = null;
        }
    }

    //endregion

    //region OnCameraPreviewViewListener

    private void notifyError() {
        if (mOnCameraPreviewViewListener != null) mOnCameraPreviewViewListener.onError();
    }

    private void notifyPreviewSizeChanged(int width, int height) {
        if (mOnCameraPreviewViewListener != null)
            mOnCameraPreviewViewListener.onPreviewSizeChanged(width, height);
    }

    public OnCameraPreviewViewListener getOnCameraPreviewViewListener() {
        return mOnCameraPreviewViewListener;
    }

    public void setOnCameraPreviewViewListener(OnCameraPreviewViewListener onCameraPreviewViewListener) {
        this.mOnCameraPreviewViewListener = onCameraPreviewViewListener;
    }

    public interface OnCameraPreviewViewListener {
        /**
         * 摄像头开始失败时回调
         */
        void onError();

        /**
         * 当预览的分辨率大小更改时触发
         *
         * @param width
         * @param height
         */
        void onPreviewSizeChanged(int width, int height);
    }
    //endregion
}
