package com.sky.hyh.customviewsamples.fans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;
import com.hyh.base_lib.utils.SizeUtils;
import com.sky.hyh.customviewsamples.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static com.sky.hyh.customviewsamples.fans.FpSpan.RequestParam.FType.Style1;
import static com.sky.hyh.customviewsamples.fans.FpSpan.RequestParam.FType.Style3;
import static com.sky.hyh.customviewsamples.fans.FpSpan.RequestParam.FType.Normal;
import static com.sky.hyh.customviewsamples.fans.FpSpan.RequestParam.FType.Style2;

/**
 * created by curdyhuang on 2020/11/27
 */
public class FpSpan extends ReplacementSpan {
    private RequestParam mRequestParam;
    private Drawable mIcon;
    private Paint mPaint;
    private SimpleLayoutParams mIconParam;
    private SimpleLayoutParams mNameParam;
    private SimpleLayoutParams mLevelParam;
    private int mWidth;
    private int mHeight;
    private RectF mDrawArea;
    private int mBgColor=0xffC566EE;
    private int mLevelBgColor=0xffB544DD;
    private int mRadius;

    public FpSpan(Context context, RequestParam param) {
        mRequestParam = param;
        init(context,param);
    }

    private void init(Context context,RequestParam param){
        if(param == null){
            return;
        }
        mRadius = SizeUtils.dp2px(2);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(SizeUtils.dp2px(9));
        mPaint.setColor(Color.WHITE);
        mDrawArea = new RectF();
        initIcon(context,param.mType);
        initName(param.mName);
        initLevel(param.mLevel);
    }

    private void initIcon(Context context,int type){
        mIcon = getIcon(context,type);
        if(mIcon!=null){
            mIconParam = new SimpleLayoutParams();
            mIconParam.setSize(mIcon.getIntrinsicWidth(),mIcon.getIntrinsicHeight());
            mIconParam.setPadding(SizeUtils.dp2px(2f),0,0,0);
        }
    }

    private void initName(String name){
        int width = (int) mPaint.measureText(name);
        Paint.FontMetricsInt pm = mPaint.getFontMetricsInt();
        int height = pm.bottom - pm.top;
        mNameParam = new SimpleLayoutParams();
        mNameParam.setSize(width,height);
        int pl = SizeUtils.dp2px(1f);
        if(mIcon==null){
            pl+= SizeUtils.dp2px(2f);
        }
        int pr = SizeUtils.dp2px(2f);
        int pv = SizeUtils.dp2px(1.5f);//竖直padding
        mNameParam.setPadding(pl,pv,pr,pv);
    }

    private void initLevel(int level){
        int width = (int) mPaint.measureText(String.valueOf(level));
        Paint.FontMetricsInt pm = mPaint.getFontMetricsInt();
        int height = pm.bottom - pm.top;
        mLevelParam = new SimpleLayoutParams();
        mLevelParam.setSize(width,height);
        int ph = SizeUtils.dp2px(2f);//水平padding
        int pv = SizeUtils.dp2px(1.5f);//竖直padding
        mLevelParam.setPadding(ph,pv,ph,pv);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        mWidth = calculateWitdth(mIconParam, mNameParam, mLevelParam);
        int iconH = calculateHeight(mIconParam);
        int nameH = calculateHeight(mNameParam);
        int levelH = calculateHeight(mLevelParam);
        int maxH = iconH;
        if(nameH>maxH){
            maxH = nameH;
        }
        if(levelH>maxH){
            maxH = levelH;
        }
        mHeight = maxH;
        return mWidth;
    }

    private int calculateWitdth(SimpleLayoutParams...params){
        if (params==null){
            return 0;
        }
        int width = 0;
        for(SimpleLayoutParams param: params){
            if(param!=null) {
                width += param.mPaddingLeft + param.mWidth + param.mPaddingRight;
            }
        }
        return width;
    }

    private int calculateHeight(SimpleLayoutParams params){
        if (params==null){
            return 0;
        }
        return params.mPaddingTop+params.mHeight+params.mPaddingBottom;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        if (mRequestParam==null){
            return;
        }
        //文本高度
        int textHeight = bottom - top;

        //1.绘制背景
        paint.setColor(mBgColor);
        mDrawArea.set(x,(textHeight-mHeight)/2f,x+mWidth,(textHeight+mHeight)/2f);
        canvas.drawRoundRect(mDrawArea,mRadius,mRadius,paint);

        //2.绘制背景
        paint.setColor(mLevelBgColor);
        mDrawArea.set(x + calculateWitdth(mIconParam, mNameParam),mDrawArea.top,x + calculateWitdth(mIconParam, mNameParam, mLevelParam),mDrawArea.bottom);
        canvas.drawRoundRect(mDrawArea,mRadius,mRadius,paint);

        //3.绘制icon
        if (mIcon != null&& mIconParam !=null) {
            int l = (int) (x + mIconParam.mPaddingLeft);
            int t = (textHeight- mIconParam.mHeight)/2;
            mIcon.setBounds(l,t, l+mIconParam.mWidth, t+mIconParam.mHeight);
            mIcon.draw(canvas);
        }

        //4.绘制名字
        if(mNameParam!=null) {
            y = (int) ((textHeight - mNameParam.mHeight) / 2 - mPaint.getFontMetrics().top);
            canvas.drawText(mRequestParam.mName, x + calculateWitdth(mIconParam)+mNameParam.mPaddingLeft, y, mPaint);
        }

        //5.绘制数字
        if(mLevelParam !=null){
            y = (int) ((textHeight - mLevelParam.mHeight) / 2 - mPaint.getFontMetrics().top);
            canvas.drawText(String.valueOf(mRequestParam.mLevel), x + calculateWitdth(mIconParam,mNameParam)+mLevelParam.mPaddingLeft, y, mPaint);
        }
    }

    private Drawable getIcon(Context context,@RequestParam.FType int type){
        Drawable icon = null;
         switch (type) {
              case Style1:{
                //icon = context.getResources().getDrawable(R.drawable.icon_1_26x28);
              }
              break;
              case Style2:{
                   //icon = context.getResources().getDrawable(R.drawable.icon_2_34x28);
               }
               break;
              case Style3:{
                   //icon = context.getResources().getDrawable(R.drawable.icon_3_40x28);
               }
               break;
              default:
                 break;
         }
         return icon;
    }

    public static class SimpleLayoutParams{
        public int mPaddingLeft;
        public int mPaddingRight;
        public int mPaddingTop;
        public int mPaddingBottom;
        public int mWidth;
        public int mHeight;

        public void setSize(int w,int h) {
            mWidth = w;
            mHeight = h;
        }

        public void setPadding(int l,int t,int r,int b) {
            mPaddingLeft = l;
            mPaddingTop = t;
            mPaddingRight = r;
            mPaddingBottom = b;
        }
    }

    public static class RequestParam{
        @IntDef({Normal, Style1, Style2, Style3})
        @Retention(RetentionPolicy.SOURCE)
        public @interface FType {
            int Normal = 0;
            int Style1 = 1;
            int Style2 = 2;
            int Style3 = 3;
        }
        public int mLevel;
        public @FType int mType;
        public String mName;

        public RequestParam(int level, @FType int type, String name) {
            mLevel = level;
            mType = type;
            mName = name;
        }
    }
}
