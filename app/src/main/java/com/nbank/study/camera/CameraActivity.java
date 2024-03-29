package com.nbank.study.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.nbank.study.ParcelTest;
import com.nbank.study.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import ico.ico.helper.WindowHelper;
import ico.ico.ico.BaseFragActivity;
import ico.ico.util.FileUtil;
import ico.ico.util.log;
import pub.devrel.easypermissions.EasyPermissions;

public class CameraActivity extends BaseFragActivity implements EasyPermissions.PermissionCallbacks, SurfaceHolder.Callback, Camera.PreviewCallback {
    private static final String TAG = CameraActivity.class.getSimpleName();
    @BindView(R.id.preview)
    SurfaceView preview;
    SurfaceHolder holder;


    WindowHelper windowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        windowHelper = new WindowHelper(getWindow());
        windowHelper
                .setWindowImmersive()
//                .setNaviHide()
//                .setNaviTranslucent()
//                .setStatusTranslucent()
//                .setWindowFull()
        ;

        log.w("onCreate: " + getResources().getDisplayMetrics().widthPixels + "|" + getResources().getDisplayMetrics().heightPixels);

        holder = preview.getHolder();
        holder.addCallback(this);

        log.w("===" + EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE));
        log.w("===" + hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE));
        log.w("===" + checkCameraPermission(this));


        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "", 0x001, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else if (!hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "", 0x001, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else if (!checkCameraPermission(this)) {
            EasyPermissions.requestPermissions(this, "", 0x001, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
//            }
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            }
//
//            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//            }
//        }
    }

    private boolean check() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "", 0x001, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
            return true;
        } else {
            if (!hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                EasyPermissions.requestPermissions(this, "", 0x001, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                return true;
            }
        }
        return false;
    }

    public static boolean checkCameraPermission(Context context) {  //检查拍照权限 ,写入文件，读出文件权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //CAMERA  WRITE_EXTERNAL_STORAGE  READ_EXTERNAL_STORAGE 权限
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //CAMERA  WRITE_EXTERNAL_STORAGE  READ_EXTERNAL_STORAGE 权限
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //CAMERA  WRITE_EXTERNAL_STORAGE  READ_EXTERNAL_STORAGE 权限
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            return true;
        }
        return false;
    }

    /**
     * 系统层的权限判断
     *
     * @param context     上下文
     * @param permissions 申请的权限 Manifest.permission.READ_CONTACTS
     * @return 是否有权限 ：其中有一个获取不了就是失败了
     */
    public static boolean hasPermissions(@NonNull Context context, @NonNull String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            String op = AppOpsManagerCompat.permissionToOp(permission);
            if (TextUtils.isEmpty(op)) {
                continue;
            }
            int result = AppOpsManagerCompat.noteProxyOp(context, op, context.getPackageName());
            if (result == AppOpsManagerCompat.MODE_IGNORED) {
                return false;
            }
            result = ContextCompat.checkSelfPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    //region permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        log.w("onPermissionsGranted");
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        log.w("onPermissionsDenied");
    }
    //endregion

    @OnClick(R.id.preview)
    public void clickFocus() {
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                log.w("onAutoFocus: ");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    Camera camera;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        fixPreviewRotation();
    }

    //region 校准摄像头角度

    /**
     * 根据屏幕旋转角度，校准摄像头角度
     */
    public void fixPreviewRotation() {
        if (camera == null) {
            return;
        }
        //﻿手机中竖屏状态下的角度为0，以逆时针旋转为正值
        int angle = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        Camera.Parameters param = camera.getParameters();
        switch (angle) {
            case Surface.ROTATION_0:
                log.w("Rotation_0");
                camera.setDisplayOrientation(90);
                break;
            case Surface.ROTATION_90:
                log.w("ROTATION_90");
                camera.setDisplayOrientation(0);
                break;
            case Surface.ROTATION_180:
                log.w("ROTATION_180");
                camera.setDisplayOrientation(90);
                break;
            case Surface.ROTATION_270:
                log.w("ROTATION_270");
                camera.setDisplayOrientation(180);
                break;
            default:
                log.w("Default Rotation!");
                camera.setDisplayOrientation(90);
                break;
        }
        camera.setParameters(param);
    }
    //endregion

    //region  校准摄像头预览分辨率
    public void fixPreviewResolution() {
        if (camera == null) {
            return;
        }
        //摄像头参数对象
        Camera.Parameters parameters = camera.getParameters();

        //获取当前屏幕的宽度高度
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point screenResolution = new Point(display.getWidth(), display.getHeight());


        //屏幕摄像头分辨率
        Point screenResolutionForCamera = new Point();
        screenResolutionForCamera.x = screenResolution.x;
        screenResolutionForCamera.y = screenResolution.y;
        if (screenResolution.x < screenResolution.y) {
            screenResolutionForCamera.x = screenResolution.y;
            screenResolutionForCamera.y = screenResolution.x;
        }
        log.d("22222==" + screenResolutionForCamera.x + "|" + screenResolutionForCamera.y);


        int width = preview.getWidth();
        int height = preview.getHeight();
        screenResolutionForCamera.x = width > height ? width : height;
        screenResolutionForCamera.y = width > height ? height : width;
        log.w("33333==" + width + "|" + height);

        //根据摄像头参数和屏幕摄像头参数，获取摄像头支持的分辨率中最接近完美的分辨率
        Point cameraResolution = findBestPreviewSizeValue(parameters, screenResolutionForCamera);

        Camera.Parameters param = camera.getParameters();
        param.setPreviewSize(cameraResolution.x, cameraResolution.y);
        log.w("44444==" + cameraResolution.x + "|" + cameraResolution.y);
        camera.setParameters(param);
    }

    /**
     * 根据摄像头参数和屏幕摄像头分辨率，挑选最适配当前屏幕的一个分辨率
     *
     * @param parameters
     * @param screenResolution
     * @return
     */
    public Point findBestPreviewSizeValue(Camera.Parameters parameters, Point screenResolution) {
        /* 这个函数目前只有一个地方调用了，形参2 screenResolution 是 假想为1920*1080 是横屏的分辨率 屏幕摄像头分辨率 */
        //获取摄像头 支持的分辨率列表
        List<Camera.Size> rawSupportedSizes = parameters.getSupportedPreviewSizes();
        //如果 支持的分辨率列表 为null，则获取预览的大小，直接返回预览大小
        if (rawSupportedSizes == null) {
            log.w("Device returned no supported preview sizes; using default");
            Camera.Size defaultSize = parameters.getPreviewSize();
            if (defaultSize == null) {
                throw new IllegalStateException("Parameters contained no preview size!");
            }
            return new Point(defaultSize.width, defaultSize.height);
        }

        //region Sort by size, descending
        List<Camera.Size> supportedPreviewSizes = new ArrayList<Camera.Size>(rawSupportedSizes);
        Collections.sort(supportedPreviewSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size a, Camera.Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });
        //endregion

        //region print size

        StringBuilder previewSizesString = new StringBuilder();
        for (Camera.Size supportedPreviewSize : supportedPreviewSizes) {
            previewSizesString.append(supportedPreviewSize.width).append('x')
                    .append(supportedPreviewSize.height).append(' ');
        }
        log.i("Supported preview sizes: " + previewSizesString);
        //endregion

        //计算 屏幕摄像头分辨率 的xy比
        double screenAspectRatio = (double) screenResolution.x / (double) screenResolution.y;

        int MIN_PREVIEW_PIXELS = 480 * 320;
        double MAX_ASPECT_DISTORTION = 0.15;

        //region Remove sizes that are unsuitable 移除不合适的size，同时如果有与 屏幕摄像头分辨率完全符合的size，则直接就return
        Iterator<Camera.Size> it = supportedPreviewSizes.iterator();
        //2048x1536 1920x1440 2160x1080 2048x1080 1920x1080 1920x960 1440x1080 1280x960 1280x720 1280x640 864x480 640x640 800x480 720x480 768x432 640x480 480x640 576x432 640x360 480x360 480x320 384x288 352x288 320x240 240x320 240x160 176x144 144x176 160x120
        while (it.hasNext()) {
            Camera.Size supportedPreviewSize = it.next();
            int realWidth = supportedPreviewSize.width;
            int realHeight = supportedPreviewSize.height;
            //移除 像素总值 不符合要求的
            if (realWidth * realHeight < MIN_PREVIEW_PIXELS) {
                it.remove();
                continue;
            }
            //2048 1536 false
            boolean isCandidatePortrait = realWidth < realHeight;
            int maybeFlippedWidth = isCandidatePortrait ? realHeight : realWidth;
            int maybeFlippedHeight = isCandidatePortrait ? realWidth : realHeight;
            //maybeFlippedWidth和maybeFlippedHeight依旧是宽屏下的

            //获取当前size的xy比
            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;

            //计算两个xy比的差值
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            log.w("===" + distortion + "|" + aspectRatio + "|" + screenAspectRatio + "|" + realWidth + "|" + realHeight);
            //移除 差值 不符合要求的
            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }

            //如果 当前size的xy 和 屏幕摄像头分辨率相同，则直接返回
            if (maybeFlippedWidth == screenResolution.x && maybeFlippedHeight == screenResolution.y) {
                Point exactPoint = new Point(realWidth, realHeight);
                log.i("Found preview size exactly matching screen size: " + exactPoint);
                return exactPoint;
            }
        }
        //endregion

        // If no exact match, use largest preview size. This was not a great idea on older devices because
        // of the additional computation needed. We're likely to get here on newer Android 4+ devices, where
        // the CPU is much more powerful.
        //如果筛选后的 sizes 还不为空，则直接使用列表第一个
        //我尝试将preview的高度设置为高度的一半，1920*540，由于比例问题，sizes中没有一个符合，所以只能获取默认size，为2048*1536
        log.w("======" + supportedPreviewSizes.size() + "|" + parameters.getPreviewSize().width + "|" + parameters.getPreviewSize().height);
        if (!supportedPreviewSizes.isEmpty()) {
            Camera.Size largestPreview = supportedPreviewSizes.get(0);
            Point largestSize = new Point(largestPreview.width, largestPreview.height);
            log.i("Using largest suitable preview size: " + largestSize);
            return largestSize;
        }

        //如果筛选后的 sizes 没有值了，则直接才有默认的预览size

        // If there is nothing at all suitable, return current preview size
        Camera.Size defaultPreview = parameters.getPreviewSize();
        if (defaultPreview == null) {
            throw new IllegalStateException("Parameters contained no preview size!");
        }
        Point defaultSize = new Point(defaultPreview.width, defaultPreview.height);
        log.i("No suitable preview sizes, using default: " + defaultSize);
        return defaultSize;
    }
    //endregion

    //region surfaceview listener
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(holder);
            fixPreviewRotation();
            fixPreviewResolution();
            camera.setPreviewCallback(this);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        log.w("===onPreviewFrame");

        YuvImage yuvImage = new YuvImage(data,
                camera.getParameters().getPreviewFormat(),
                camera.getParameters().getPreviewSize().width,
                camera.getParameters().getPreviewSize().height,
                null
        );
        Rect rect = new Rect(0, 0, camera.getParameters().getPreviewSize().width,
                camera.getParameters().getPreviewSize().height);
        File file = new File(Environment.getExternalStorageDirectory() + "/test/ddd" + System.currentTimeMillis() + ".jpg");
        try {
            //确保文件存在，如果不存在就修改文件名然后创建，然后返回对应的文件对象
            File _file = FileUtil.createFileOrRename(file);
            if (_file == null) {
                return;
            }
            yuvImage.compressToJpeg(rect, 100, new FileOutputStream(_file));
            log.w("==" + _file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //region 获取屏幕宽高

    /**
     * 获取当前手机屏幕的宽度，px值
     *
     * @param context
     * @return int
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取当前手机屏幕的高度，px值
     *
     * @param context
     * @return int
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    //endregion

}
