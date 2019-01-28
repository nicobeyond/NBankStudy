/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ico.ico.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Camera;
import android.preference.PreferenceManager;
import android.util.Log;

import ico.ico.camera.setting.PreferencesActivity;


/**
 * A class which deals with reading, parsing, and setting the camera parameters which are used to
 * configure the camera hardware.
 */
final class CameraConfigurationManager {

    private static final String TAG = "CameraConfiguration";

    private final Context context;
    /**
     * 根据当前屏幕的旋转角度得到的屏幕分辨率，由于act锁定了竖屏，所以这里假想可以认为是1080*1920
     * 这个原来直接使用屏幕的分辨率，但是在全面屏里，屏幕分辨率不包括底部导航栏，所以实际预览控件大小比获取到的屏幕分辨率大
     * 所以这里实际使用的是预览控件大小
     */
    private Point screenResolution;
    /* 根据计算，从摄像头支持的分辨率列表中选择的最适配当前屏幕的一个分辨率 */
    private Point cameraResolution;

    CameraConfigurationManager(Context context) {
        this.context = context;
    }

    /**
     * Reads, one time, values from the camera that are needed by the app.
     * 在全面屏中，以华为nova2s举例，控件的宽高是2160*1080，但是在获取屏幕的宽度高度时，是不计算底部虚拟导航栏的，所以会获取到2040*1080
     */
    void initFromCameraParameters(Camera camera, int width, int height) {
        //摄像头参数对象
        Camera.Parameters parameters = camera.getParameters();

        //获取当前屏幕的宽度高度
//        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        Point theScreenResolution = new Point(display.getWidth(), display.getHeight());
        Point theScreenResolution = new Point(width, height);

        screenResolution = theScreenResolution;
        Log.i(TAG, "Screen resolution: " + screenResolution);
//    cameraResolution = CameraConfigurationUtils.findBestPreviewSizeValue(parameters, screenResolution);

        //屏幕摄像头分辨率
        Point screenResolutionForCamera = new Point();
        screenResolutionForCamera.x = screenResolution.x;
        screenResolutionForCamera.y = screenResolution.y;
        if (screenResolution.x < screenResolution.y) {
            screenResolutionForCamera.x = screenResolution.y;
            screenResolutionForCamera.y = screenResolution.x;
        }
        //根据摄像头参数和屏幕摄像头参数，获取摄像头支持的分辨率中最接近完美的分辨率
        cameraResolution = CameraConfigurationUtils.findBestPreviewSizeValue(parameters, screenResolutionForCamera);
        Log.i(TAG, "Camera resolution: " + cameraResolution);
    }

    void setDesiredCameraParameters(Camera camera, boolean safeMode) {
        Camera.Parameters parameters = camera.getParameters();

        if (parameters == null) {
            Log.w(TAG, "Device error: no camera parameters are available. Proceeding without configuration.");
            return;
        }

        Log.i(TAG, "Initial camera parameters: " + parameters.flatten());

        if (safeMode) {
            Log.w(TAG, "In camera config safe mode -- most settings will not be honored");
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        initializeTorch(parameters, prefs, safeMode);

        CameraConfigurationUtils.setFocus(
                parameters,
                prefs.getBoolean(PreferencesActivity.KEY_AUTO_FOCUS, true),
                prefs.getBoolean(PreferencesActivity.KEY_DISABLE_CONTINUOUS_FOCUS, true),
                safeMode);

        if (!safeMode) {
            if (prefs.getBoolean(PreferencesActivity.KEY_INVERT_SCAN, false)) {
                CameraConfigurationUtils.setInvertColor(parameters);
            }

            if (!prefs.getBoolean(PreferencesActivity.KEY_DISABLE_BARCODE_SCENE_MODE, true)) {
                CameraConfigurationUtils.setBarcodeSceneMode(parameters);
            }

            if (!prefs.getBoolean(PreferencesActivity.KEY_DISABLE_METERING, true)) {
                CameraConfigurationUtils.setVideoStabilization(parameters);
                CameraConfigurationUtils.setFocusArea(parameters);
                CameraConfigurationUtils.setMetering(parameters);
            }

        }

        parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
        camera.setDisplayOrientation(90);
        camera.setParameters(parameters);

        Camera.Parameters afterParameters = camera.getParameters();
        Camera.Size afterSize = afterParameters.getPreviewSize();
        if (afterSize != null && (cameraResolution.x != afterSize.width || cameraResolution.y != afterSize.height)) {
            Log.w(TAG, "Camera said it supported preview size " + cameraResolution.x + 'x' + cameraResolution.y +
                    ", but after setting it, preview size is " + afterSize.width + 'x' + afterSize.height);
            cameraResolution.x = afterSize.width;
            cameraResolution.y = afterSize.height;
        }
    }

    Point getCameraResolution() {
        return cameraResolution;
    }

    Point getScreenResolution() {
        return screenResolution;
    }

    boolean getTorchState(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters != null) {
                String flashMode = camera.getParameters().getFlashMode();
                return flashMode != null &&
                        (Camera.Parameters.FLASH_MODE_ON.equals(flashMode) ||
                                Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode));
            }
        }
        return false;
    }

    void setTorch(Camera camera, boolean newSetting) {
        Camera.Parameters parameters = camera.getParameters();
        doSetTorch(parameters, newSetting, false);
        camera.setParameters(parameters);
    }

    private void initializeTorch(Camera.Parameters parameters, SharedPreferences prefs, boolean safeMode) {
        boolean currentSetting = FrontLightMode.readPref(prefs) == FrontLightMode.ON;
        doSetTorch(parameters, currentSetting, safeMode);
    }

    private void doSetTorch(Camera.Parameters parameters, boolean newSetting, boolean safeMode) {
        CameraConfigurationUtils.setTorch(parameters, newSetting);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (!safeMode && !prefs.getBoolean(PreferencesActivity.KEY_DISABLE_EXPOSURE, true)) {
            CameraConfigurationUtils.setBestExposure(parameters, newSetting);
        }
    }
}
