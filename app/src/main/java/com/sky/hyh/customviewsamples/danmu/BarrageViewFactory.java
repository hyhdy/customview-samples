package com.sky.hyh.customviewsamples.danmu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.ref.SoftReference;
import java.util.LinkedList;

/**
 * created by curdyhuang on 2020/11/24
 * 弹幕工厂，用于构建和回收弹幕控件
 */
public class BarrageViewFactory {
    private ViewCachePool<BarrageView> mPool = new ViewCachePool<>();
    private LayoutInflater mInflater;

    public BarrageViewFactory(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public BarrageView build(){
        BarrageView v = getViewFromPool();
        if(v!=null){
            Log.d("hyh","BarrageViewFactory: build: 从缓存池获取弹幕");
            return v;
        }
        Log.d("hyh","BarrageViewFactory: build: 创建新的弹幕");
        return (BarrageView) mInflater.inflate(BarrageView.RES_ID,null);
    }

    public void recycle(BarrageView view){
        mPool.put(view);
    }

    private BarrageView getViewFromPool(){
        BarrageView v = mPool.get();
        if(v!=null){
            if(v.getParent()==null){
                return v;
            }else{
                return getViewFromPool();
            }
        }
        return null;
    }

    public void clear(){
        mPool.clear();
    }

    class ViewCachePool<T> {
        private static final String TAG = "ViewCachePool";
        private static final int DEFAULT_CACHE_SIZE = 20;
        private LinkedList<SoftReference<T>> mCache;
        private int mMaxCacheSize = DEFAULT_CACHE_SIZE;

        public ViewCachePool() {
            mCache = new LinkedList<>();
        }

        public void put(T object) {
            if (mCache.size() >= mMaxCacheSize) {
                return;
            }
            mCache.add(new SoftReference<T>(object));
        }

        public T get() {
            if (mCache.size() <= 0) {
                return null;
            }
            SoftReference<T> object = mCache.remove(0);
            if(object.get()!=null){
                return object.get();
            }
            return null;
        }

        public int getCacheSize(){
            return mCache.size();
        }

        public void clear(){
            mCache.clear();
        }
    }
}
