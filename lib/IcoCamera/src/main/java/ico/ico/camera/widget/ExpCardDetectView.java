package ico.ico.camera.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 营业执照扫描的预览框
 */
@SuppressLint("InlinedApi")
public class ExpCardDetectView extends DetectView {
    private int[] border = null;

    public ExpCardDetectView(Context context) {
        super(context);
    }

    public ExpCardDetectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpCardDetectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExpCardDetectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init() {
        paint = new Paint();
        paint.setColor(0xffff0000);
        mBackgroundColor = 0xAA666666;
        mRadius = 8;
        mCornerSize = 40;
        mCornerStrokeWidth = dip2px(2);
    }

    //region 更新检测区域的四边位置
    float borderHeightVar = 60;// 预览框的高度值，如果需要改变为屏幕高度的比例值，需要初始化的时候重新赋值
    float borderHeightFromTop = 230;// 预览框离顶点的距离，也可以变为屏幕高度和预览宽高度的差值，需要初始化的时候重新赋值

    /**
     * 预览框的位置和大小，可以更改此处来更改预览框的方向位置还有大小
     */
    protected float[] getPositionWithArea(int newWidth, int newHeight, float scaleW, float scaleH) {
        float borderHeight = dip2px(borderHeightVar) * scaleH;

        float left, top, right, bottom;

        //决定检测区域的显示方式，优先选用 用户设置，如果用户未设置则根据宽高大小比设置
        boolean vertical = false;
        if (mManuVertical == null) {
            mAutoVertical = newWidth < newHeight;
            vertical = mAutoVertical;
        } else {
            vertical = mManuVertical;
        }
        // 注意：机打号的预览框高度设置建议是 屏幕高度的1/10,宽度 尽量与屏幕同宽
        if (vertical) {// vertical
            int padding_leftright = dip2px(13);
            int padding_top = dip2px(borderHeightFromTop);
            left = padding_leftright * scaleW;
            right = newWidth - left;
            top = padding_top * scaleH;
            bottom = borderHeight + top;
        } else {
            borderHeight = dip2px(borderHeightVar) * scaleW;
            left = (newWidth - borderHeight) / 2;
            right = newWidth - left;
            float borderWidth = 1000 * scaleH;
            top = (newHeight - borderWidth) / 2;
            bottom = newHeight - top;
        }

        float[] region = new float[4];
        region[0] = left;
        region[1] = top;
        region[2] = right;
        region[3] = bottom;
        return region;
    }
    //endregion

    //region 绘制
    @Override
    protected void drawOther(Canvas c, float scaleW, float scaleH) {
        drawBorder(c, scaleW);
        drawCenterLine(c);
    }


    /** 绘制边框？这个border一直为null，没有设置的地方 */
    private void drawBorder(Canvas c, float scale) {
        if (border != null) {
            paint.setStrokeWidth(3);
            c.drawLine(border[0] * scale, border[1] * scale, border[2] * scale, border[3] * scale, paint);
            c.drawLine(border[2] * scale, border[3] * scale, border[4] * scale, border[5] * scale, paint);
            c.drawLine(border[4] * scale, border[5] * scale, border[6] * scale, border[7] * scale, paint);
            c.drawLine(border[6] * scale, border[7] * scale, border[0] * scale, border[1] * scale, paint);
        }
    }


    /** 画动态的中心线 */
    private void drawCenterLine(Canvas c) {
        paint.setColor(0xf6b26b);
        paint.setStrokeWidth(1);

        float left = mDetectRegionForUi[0], top = mDetectRegionForUi[1], right = mDetectRegionForUi[2], bottom = mDetectRegionForUi[3];

        if (isVertical()) {
            c.drawLine(left, top + (bottom - top) / 2, right, top + (bottom - top) / 2, paint);
        } else {
            c.drawLine(left + (right - left) / 2, top, left + (right - left) / 2, bottom, paint);
        }
    }
    //endregion
}
