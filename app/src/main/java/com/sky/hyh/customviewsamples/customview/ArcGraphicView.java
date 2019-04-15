package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import com.hyh.base_lib.utils.SizeUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 曲线图表控件
 */
public class ArcGraphicView extends View {
    private static final String DEFAULT_COLOR_LINE = "#24979797";
    private static final String DEFAULT_COLOR_POINT = "#4affffff";
    private static final int LINE_WIDTH = SizeUtils.dp2px(1);
    private static final int POINT_RADIUS = SizeUtils.dp2px(2);
    private static final int DEFAULT_VALID_HEIGHT = SizeUtils.dp2px(40);
    private static final int DEFAULT_VALID_WIDTH = SizeUtils.dp2px(180);
    private static final int DEFAULT_LEFT_OFFSET = SizeUtils.dp2px(2);
    private static final int DEFAULT_RIGHT_OFFSET = SizeUtils.dp2px(2);
    private static final int DEFAULT_TOP_OFFSET = SizeUtils.dp2px(2);
    private static final int DEFAULT_BOTTOM_OFFSET = SizeUtils.dp2px(2);
    /**
     * 控制曲线平滑度的参数，0-0.5，数值越大，数值点之间的曲线弧度越大
     */
    private static final float SMOOTHNESS = 0.3f;
    private String mLineColor = DEFAULT_COLOR_LINE;
    private String mPointColor = DEFAULT_COLOR_POINT;
    /**
     * 用于占位的数值，表示该位置没有点，曲线将断开
     */
    public static final float VALUE_SEIZE = -1f;
    /**
     * 升序，y轴数据从下到上递增
     */
    public static final int ORDER_AES = 0;
    /**
     * 降序，y轴数据从下到上递减
     */
    public static final int ORDER_DES = 1;
    /**
     * y轴从底部到顶部排列的数字顺序
     */
    private int mYOrder = ORDER_AES;

    private Context mContext;
    /**
     * 普通画笔
     */
    private Paint mPaint;
    private Path mPath;
    /**
     * 曲线画笔
     */
    private Paint mDashPaint;
    private Path mDashPath;

    private int mCanvasHeight;
    private int mCanvasWidth;
    private int mValidWidth = DEFAULT_VALID_WIDTH;
    private int mValidHeight = DEFAULT_VALID_HEIGHT;
    private int mLeftOffset = DEFAULT_LEFT_OFFSET;
    private int mRightOffset = DEFAULT_RIGHT_OFFSET;
    private int mBottomOffset = DEFAULT_BOTTOM_OFFSET;
    private int mTopOffset = DEFAULT_TOP_OFFSET;
    private boolean isMeasure = true;
    /**
     * Y轴最大值
     */
    private float mYMaxValue;
    /**
     * y轴最小值
     */
    private float mYMinValue;
    /**
     * 曲线上总点数
     */
    private List<PointF> mAllPoints;
    /**
     * 曲线点数据列表，因为曲线是有可能断开的会有多段曲线，所以需要保存多段点数据列表分别绘制各自的贝塞尔曲线
     */
    private List<List<PointF>> mPointsList;
    /**
     * 用于调整贝塞尔曲线光滑度的控制点
     */
    private SparseArray<List<PointF>> mControlPointMap;
    /**
     * 纵坐标值数据
     */
    private List<Float> mYRawData;
    /**
     * 横坐标值数据
     */
    private List<Integer> mXRawData;

    public ArcGraphicView(Context context) {
        this(context, null);
    }

    public ArcGraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();

        mDashPaint = new Paint();
        mDashPath = new Path();
        mDashPaint.setColor(Color.parseColor(mLineColor));
        mDashPaint.setStyle(Paint.Style.STROKE);
        mDashPaint.setStrokeWidth(LINE_WIDTH);
        mDashPaint.setAntiAlias(true);
        mDashPaint.setPathEffect(
            new DashPathEffect(new float[] { SizeUtils.dp2px(4), SizeUtils.dp2px(3) }, 0));

        mAllPoints = new ArrayList();
        mPointsList = new ArrayList<>();
        mControlPointMap = new SparseArray<>();
        mYRawData = new ArrayList<>();
        mXRawData = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (isMeasure) {
            mCanvasHeight = getHeight();
            mCanvasWidth = getWidth();

            if (mValidHeight == 0) {
                mValidHeight = mCanvasHeight - mTopOffset - mBottomOffset;
            }

            if (mValidWidth == 0) {
                mValidWidth = mCanvasWidth - mLeftOffset - mRightOffset;
            }

            getXRawData();
            getPoints();
            mControlPointMap.clear();
            for (int i = 0; i < mPointsList.size(); i++) {
                calculateControlPoint(mPointsList.get(i));
            }
            isMeasure = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画虚线（y轴中心位置）
        drawDashLine(canvas);

        //画弧线
        mPaint.setColor(Color.parseColor(mLineColor));
        mPaint.setStrokeWidth(LINE_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < mPointsList.size(); i++) {
            drawScrollLine(mPointsList.get(i), canvas);
        }
        //drawScrollLine(canvas);

        //画圆点
        mPaint.setColor(Color.parseColor(mPointColor));
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mAllPoints.size(); i++) {
            float cx = mAllPoints.get(i).x;
            float cy = mAllPoints.get(i).y;
            canvas.drawCircle(cx, cy, POINT_RADIUS, mPaint);
        }
    }

    private void getXRawData() {
        int yRawDataSize = mYRawData.size();

        int aveWidth = mValidWidth / (yRawDataSize - 1);
        Log.d("hyh", "LineGraphicView: drawAllYLine: aveWidth="
            + aveWidth
            + " ,yRawDataSize="
            + yRawDataSize);

        for (int i = 0; i < yRawDataSize; i++) {
            mXRawData.add(mLeftOffset + aveWidth * i);
        }
    }

    /**
     * 画虚线
     */
    private void drawDashLine(Canvas canvas) {
        mDashPath.reset();
        mDashPath.moveTo(mLeftOffset, mValidHeight / 2 + mTopOffset);
        mDashPath.lineTo(mLeftOffset + mValidWidth, mValidHeight / 2 + mTopOffset);
        canvas.drawPath(mDashPath, mDashPaint);
    }

    /**
     * 实现光滑贝塞尔曲线
     */
    private void drawScrollLine(List<PointF> pointList, Canvas canvas) {
        if (pointList == null) {
            return;
        }
        int size = pointList.size();
        if (size <= 1) {
            return;
        }
        List<PointF> controlList = mControlPointMap.get(pointList.hashCode());
        //连接各部分曲线
        for (int i = 0; i < size - 1; i++) {
            mPath.reset();
            PointF firstPoint = pointList.get(i);
            PointF rightPoint = pointList.get(i + 1);

            PointF leftControlPoint = controlList.get(2 * i);
            PointF rightControlPoint = controlList.get(2 * i + 1);

            mPath.moveTo(firstPoint.x, firstPoint.y);
            //安卓实现三阶贝塞尔曲线的api
            mPath.cubicTo(leftControlPoint.x, leftControlPoint.y, rightControlPoint.x,
                rightControlPoint.y, rightPoint.x, rightPoint.y);
            canvas.drawPath(mPath, mPaint);
        }
    }

    /**
     * 计算用于调整贝塞尔曲线光滑度的控制点
     */
    private void calculateControlPoint(List<PointF> pointList) {
        if (pointList == null) {
            return;
        }
        int size = pointList.size();
        if (size <= 1) {
            return;
        }
        List<PointF> controPointList = new ArrayList<>();
        mControlPointMap.put(pointList.hashCode(), controPointList);
        for (int i = 0; i < size; i++) {
            PointF point = pointList.get(i);
            if (i == 0) {
                //第一项
                //添加后控制点
                PointF nextPoint = pointList.get(i + 1);
                float controlX = point.x + (nextPoint.x - point.x) * SMOOTHNESS;
                float controlY = point.y;
                controPointList.add(new PointF(controlX, controlY));
            } else if (i == (size - 1)) {
                //添加前控制点
                PointF lastPoint = pointList.get(i - 1);
                float controlX = point.x - (point.x - lastPoint.x) * SMOOTHNESS;
                float controlY = point.y;
                controPointList.add(new PointF(controlX, controlY));
            } else {
                //中间项
                PointF lastPoint = pointList.get(i - 1);
                PointF nextPoint = pointList.get(i + 1);
                float k = (nextPoint.y - lastPoint.y) / (nextPoint.x - lastPoint.x);
                float b = point.y - k * point.x;
                //添加前控制点
                float lastControlX = point.x - (point.x - lastPoint.x) * SMOOTHNESS;
                float lastControlY = k * lastControlX + b;
                controPointList.add(new PointF(lastControlX, lastControlY));
                //添加后控制点
                float nextControlX = point.x + (nextPoint.x - point.x) * SMOOTHNESS;
                float nextControlY = k * nextControlX + b;
                controPointList.add(new PointF(nextControlX, nextControlY));
            }
        }
    }

    private void getPoints() {
        int size = mYRawData.size();
        List<PointF> pointFList = null;
        for (int i = 0; i < size; i++) {
            float pointX = mXRawData.get(i);
            float yRawData = mYRawData.get(i);
            Log.d("hyh", "LineGraphicView: getPoints: yRawData=" + yRawData);
            if (yRawData == VALUE_SEIZE) {
                pointFList = null;
                continue;
            }
            if (pointFList == null) {
                pointFList = new ArrayList<>();
                mPointsList.add(pointFList);
            }
            float ph;
            if (mYOrder == ORDER_DES) {
                ph = ((yRawData - mYMinValue) / (mYMaxValue - mYMinValue)) * mValidHeight;
            } else {
                ph = ((mYMaxValue - yRawData) / (mYMaxValue - mYMinValue)) * mValidHeight;
            }
            Log.d("hyh", "LineGraphicView: getPoints: pointX="
                + pointX
                + " ,ph="
                + ph
                + " ,mBottomOffset="
                + mBottomOffset);
            mAllPoints.add(new PointF(pointX, ph + mTopOffset));
            pointFList.add(new PointF(pointX, ph + mTopOffset));
        }
    }

    public void setData(List<Float> yData, float yMaxValue, float yMinValue, int yOrder) {
        mYRawData = yData;
        mYMaxValue = yMaxValue;
        mYMinValue = yMinValue;
        mYOrder = yOrder;
    }

    public void setValidWidth(int validWidth) {
        mValidWidth = SizeUtils.dp2px(validWidth);
    }

    public void setValidHeight(int validHeight) {
        mValidHeight = SizeUtils.dp2px(validHeight);
    }

    public void setLeftOffset(int leftOffset) {
        mLeftOffset = SizeUtils.dp2px(leftOffset);
    }

    public void setRightOffset(int rightOffset) {
        mRightOffset = SizeUtils.dp2px(rightOffset);
    }

    public void setBottomOffset(int bottomOffset) {
        mBottomOffset = SizeUtils.dp2px(bottomOffset);
    }

    public void setTopOffset(int topOffset) {
        mTopOffset = SizeUtils.dp2px(topOffset);
    }

    public void setLineColor(String lineColor) {
        mLineColor = lineColor;
    }

    public void setPointColor(String pointColor) {
        mPointColor = pointColor;
    }
}
