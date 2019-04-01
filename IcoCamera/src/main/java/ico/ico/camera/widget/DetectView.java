package ico.ico.camera.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 检测框控件，用于放置在摄像头预览控件上方
 * <p>
 * 子控件有身份证扫描，营业执照扫描
 */
public abstract class DetectView extends View {
    //画笔
    protected Paint paint = null;
    /** 边框显示有普通色和匹配成功时的颜色，该值设置是否显示为成功时的颜色 */
    protected boolean borderMatch = false;
    protected int mPreviewWidth;
    protected int mPreviewHeight;

    /** 蒙层位置路径 */
    Path mClipPath = new Path();
    /** 四个边角的位置 */
    RectF mClipRect = new RectF();

    /** 当证件匹配时的边框颜色 */
    protected int mColorMatch = 0xffff0000;
    /** 未匹配状态下的边框颜色 */
    protected int mColorNormal = 0xffff0000;
    /** 除中间方框以外的背景色 */
    protected int mBackgroundColor = 0xAA666666;

    /** 检测区域的监听 */
    protected ExpCardDetectView.OnDetectRegionListener mOnDetectRegionListener;

    /** 用户设置的检测区域显示方式 */
    protected Boolean mManuVertical = true;
    /** 控件自己决定检测区域的显示方式 */
    protected boolean mAutoVertical = false;

    /** 控件宽高和预览分辨率宽高的比值,用户确定切图的准确性，因为传到引擎是用preview的尺寸，但是界面画图是屏幕的尺寸，所以需要缩放 */
    protected float scaleW, scaleH;

    /** 基于控件实际大小，检测区域的四边位置，用于绘制检测区域的边框，l，t，r，b */
    protected float[] mDetectRegionForUi = null;
    /** 基于预览分辨率大小，检测区域的四边位置，用于身份证识别时用来定位身份证区域，l，t，r，b */
    protected float[] mDetectRegionForPreview = null;

    protected float mRadius = 8;
    /** 4个角的大小 */
    protected float mCornerSize = 40;
    /** 绘制的边框粗细 */
    protected float mCornerStrokeWidth = 8;


    public DetectView(Context context) {
        super(context);
        init();
    }

    public DetectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DetectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /** 初始化，会在所有构造函数中调用 */
    abstract void init();

    /** 预览框的位置和大小，可以更改此处来更改预览框的方向位置还有大小 */
    abstract float[] getPositionWithArea(int newWidth, int newHeight, float scaleW, float scaleH);

    /** 计算蒙层位置 */
    protected void upateClipRegion() {
        mRadius = 0;
        //设置为软件加速
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mClipPath.reset();
        mClipRect.set(mDetectRegionForUi[0], mDetectRegionForUi[1], mDetectRegionForUi[2], mDetectRegionForUi[3]);
        mClipPath.addRoundRect(mClipRect, mRadius, mRadius, Path.Direction.CW);
    }

    //region 绘制
    boolean boolUpdateClip = true;// 保证没有正反面业务时只会更新一次

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        boolUpdateClip = true;
    }

    @Override
    public void onDraw(Canvas c) {
        if (mPreviewHeight == 0 || mPreviewWidth == 0) {
            mPreviewHeight = getWidth();
            mPreviewWidth = getHeight();
        }
        //初始化方框区域的位置和坐标
        if (boolUpdateClip) {
            boolUpdateClip = false;
            scaleW = getWidth() / (float) mPreviewHeight;
            scaleH = getHeight() / (float) mPreviewWidth;
            //根据控件大小，计算检测区域的四边坐标，然后保存，
            mDetectRegionForUi = getPositionWithArea(getWidth(), getHeight(), scaleW, scaleH);
            //通知 检测区域 坐标变更，然后通过给定的坐标可以定位人像或者国徽的位置
            notifyDetectRegionChanged(isVertical(), mDetectRegionForUi[0], mDetectRegionForUi[1], mDetectRegionForUi[2], mDetectRegionForUi[3]);
            upateClipRegion();
        }
        c.save();
        //将除中间方框以外的区域，设置背景色
        c.clipPath(mClipPath, Region.Op.DIFFERENCE);
        c.drawColor(mBackgroundColor);
//        c.drawRoundRect(mClipRect, mRadius, mRadius, paint);
        c.restore();

        drawBorderEdge(c);

        drawOther(c, scaleW, scaleH);
    }

    /** 绘制预览框的四个角，根据预览是否匹配改变角的颜色 */
    protected void drawBorderEdge(Canvas c) {
        if (borderMatch) {// 设置颜色
            paint.setColor(mColorMatch);
        } else {
            paint.setColor(mColorNormal);
        }
        paint.setStrokeWidth(mCornerStrokeWidth);
        // 左上
        c.drawLine(mClipRect.left, mClipRect.top + mCornerStrokeWidth / 2, mClipRect.left + mCornerSize + mCornerStrokeWidth / 2, mClipRect.top + mCornerStrokeWidth / 2, paint);
        c.drawLine(mClipRect.left + mCornerStrokeWidth / 2, mClipRect.top + mCornerStrokeWidth / 2, mClipRect.left + mCornerStrokeWidth / 2, mClipRect.top + mCornerSize + mCornerStrokeWidth / 2, paint);
        // 右上
        c.drawLine(mClipRect.right - mCornerSize - mCornerStrokeWidth / 2, mClipRect.top + mCornerStrokeWidth / 2, mClipRect.right, mClipRect.top + mCornerStrokeWidth / 2, paint);
        c.drawLine(mClipRect.right - mCornerStrokeWidth / 2, mClipRect.top + mCornerStrokeWidth / 2, mClipRect.right - mCornerStrokeWidth / 2, mClipRect.top + mCornerSize + mCornerStrokeWidth / 2, paint);
        // 右下
        c.drawLine(mClipRect.right - mCornerSize - mCornerStrokeWidth / 2, mClipRect.bottom - mCornerStrokeWidth / 2, mClipRect.right, mClipRect.bottom - mCornerStrokeWidth / 2, paint);
        c.drawLine(mClipRect.right - mCornerStrokeWidth / 2, mClipRect.bottom - mCornerSize - mCornerStrokeWidth / 2, mClipRect.right - mCornerStrokeWidth / 2, mClipRect.bottom - mCornerStrokeWidth / 2, paint);
        // 左下
        c.drawLine(mClipRect.left, mClipRect.bottom - mCornerStrokeWidth / 2, mClipRect.left + mCornerSize + mCornerStrokeWidth / 2, mClipRect.bottom - mCornerStrokeWidth / 2, paint);
        c.drawLine(mClipRect.left + mCornerStrokeWidth / 2, mClipRect.bottom - mCornerSize - mCornerStrokeWidth / 2, mClipRect.left + mCornerStrokeWidth / 2, mClipRect.bottom - mCornerStrokeWidth / 2, paint);
    }


    abstract void drawOther(Canvas c, float scaleW, float scaleH);
    //endregion

    /**
     * 设置摄像头预览的分辨率
     *
     * @param width
     * @param height
     */

    //region GETSET
    public void setPreviewSize(int width, int height) {
        this.mPreviewWidth = width;
        this.mPreviewHeight = height;
        boolUpdateClip = true;
        //根据预览分辨率大小，计算出检测区域的四边左边，进行保存，用于sdk进行识别前的区域匹配检查
        mDetectRegionForPreview = getPositionWithArea(mPreviewHeight, mPreviewWidth, 1f, 1f);
        postInvalidate();
    }

    public int[] getPreviewSize() {
        return new int[]{mPreviewWidth, mPreviewHeight};
    }

    public float[] getDetectRegionForUi() {
        return mDetectRegionForUi;
    }

    public float[] getDetectRegionForPreview() {
        return mDetectRegionForPreview;
    }

    public void setColorNormal(int colorNormal) {
        this.mColorNormal = colorNormal;
    }

    public void setColorMatch(int colorMatch) {
        this.mColorMatch = colorMatch;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
    }

    public void setBorderMatch(boolean match) {
        this.borderMatch = match;
        postInvalidate();
    }

    public Boolean isVertical() {
        return mManuVertical != null ? mManuVertical : mAutoVertical;
    }

    /**
     * 设置检测方框以垂直显示还是以水平显示
     *
     * @param vertical
     */
    public void setVertical(Boolean vertical) {
        mManuVertical = vertical;
    }

    //endregion

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    protected int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //region OnDetectRegionListener
    private void notifyDetectRegionChanged(boolean vertical, float l, float t, float r, float b) {
        if (mOnDetectRegionListener != null)
            mOnDetectRegionListener.onRegionChanged(vertical, l, t, r, b);
    }

    public void setOnDetectRegionListener(OnDetectRegionListener onDetectRegionListener) {
        this.mOnDetectRegionListener = onDetectRegionListener;
    }

    public interface OnDetectRegionListener {

        void onRegionChanged(boolean vertical, float l, float t, float r, float b);
    }
    //endregion
}
