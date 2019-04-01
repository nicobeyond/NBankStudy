package ico.ico.camera.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 身份证扫描的预览框
 */
@SuppressLint("InlinedApi")
public class IdCardDetectView extends DetectView {
    public static float[] IDCARD_WIDGET = new float[]{540, 856};

    public IdCardDetectView(Context context) {
        super(context);
    }

    public IdCardDetectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IdCardDetectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IdCardDetectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init() {
        paint = new Paint();
        paint.setColor(0xffff0000);
        mBackgroundColor = 0x66000000;
        mRadius = 12;
        mCornerSize = 140;
        mCornerStrokeWidth = dip2px(4);
    }

    //region 更新检测区域的四边位置

    /**
     * 功能：根据当前屏幕的方向还有 宽高，还有证件的比例比如身份证高宽比 0.618来算出
     * 预览框的位置和大小，可以更改此处来更改预览框的方向位置还有大小
     */
    public float[] getPositionWithArea(int newWidth, int newHeight, float scaleW, float scaleH) {
//        Logs.i("scaleW:", scaleW + "");
        //计算身份证的宽高比
        float rate = IDCARD_WIDGET[0] / IDCARD_WIDGET[1];
//        float rate = 0.618f;

        float left, top, right, bottom;


        //决定检测区域的显示方式，优先选用 用户设置，如果用户未设置则根据宽高大小比设置
        boolean vertical = false;
        if (mManuVertical == null) {
            mAutoVertical = newWidth < newHeight;
            vertical = mAutoVertical;
        } else {
            vertical = mManuVertical;
        }
        if (vertical) {// vertical
            float dis = 1 / 8f;// 10
            left = newWidth * dis;
            right = newWidth - left;
            top = (newHeight - (newWidth - left - left) / rate) / 2;
            bottom = newHeight - top;
        } else {// horizental
            float dis = 1 / 20f;
            left = newWidth * dis;
            right = newWidth - left;
            top = 200f * scaleH;
            bottom = top + (newWidth - left - left) * rate;
            // 注解部分是：竖直模式居中，如果是竖直模式相对位置 则需要注意 不同分辨率手机的缩放比例
            // top = (newHeight - (newWidth - left - left) * 0.618f) / 2;
            // bottom = newHeight - top;
        }

        float[] region = new float[4];
        region[0] = left;
        region[1] = top;
        region[2] = right;
        region[3] = bottom;
        return region;
    }

    @Override
    protected void drawOther(Canvas c, float scaleW, float scaleH) {
        // TODO: 2018/10/29
    }
    //endregion
}
