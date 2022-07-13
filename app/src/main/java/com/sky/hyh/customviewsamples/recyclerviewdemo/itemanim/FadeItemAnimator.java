package com.sky.hyh.customviewsamples.recyclerviewdemo.itemanim;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;

import com.sky.hyh.customviewsamples.recyclerviewdemo.itemanim.BaseItemAnimator;

/**
 * Created by ivy on 2017/3/22.
 * Description：
 */

public class FadeItemAnimator extends BaseItemAnimator {
    /**
     * 执行移除动画
     * @param holder 被移除的ViewHolder
     * @param animator 被移动的ViewHolder对应动画对象
     */
    @Override
    public void setRemoveAnimation(RecyclerView.ViewHolder holder,ViewPropertyAnimatorCompat animator) {
        //透明度渐隐效果
        animator.alpha(0);
    }

    /**
     * 执行移除动画结束，执行还原，因为该ViewHolder会被复用
     * @param view 被移除的ViewHolder
     */
    @Override
    public void removeAnimationEnd(RecyclerView.ViewHolder view) {
        view.itemView.setAlpha(1);
    }

    /**
     * 设置添加动画的初始值
     * @param holder 添加的ViewHolder
     */
    @Override
    public void addAnimationInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(0);
    }

    /**
     * 执行添加动画
     * @param holder 添加的ViewHolder
     * @param animator 添加的ViewHolder对应动画对象
     */
    @Override
    public void setAddAnimation(RecyclerView.ViewHolder holder,ViewPropertyAnimatorCompat animator) {
        //因为在addAnimationInit中设置期alpha初始为0，所以添加动画的执行效果是透明度从0到1渐显。
        animator.alpha(1);
    }

    /**
     * 添加动画取消时调用
     * @param holder 添加的ViewHolder
     */
    @Override
    public void addAnimationCancel(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(1);
    }

    /**
     * 更新时旧的ViewHolder动画
     * @param holder 旧的ViewHolder
     * @param animator ViewHolder对应动画对象
     */
    @Override
    public void setOldChangeAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        //透明度渐隐效果
        animator.alpha(0);
    }

    /**
     * 更新时旧的ViewHolder动画结束，执行还原，因为该ViewHolder会被复用
     * @param holder
     */
    @Override
    public void oldChangeAnimationEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(1);
    }

    /**
     * 更新时新的ViewHolder初始化
     * @param holder 更新时新的ViewHolder
     */
    @Override
    public void newChangeAnimationInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(0);
    }

    /**
     * 更新时新的ViewHolder动画执行
     * @param holder 新的ViewHolder
     * @param animator ViewHolder对应动画对象
     */
    @Override
    public void setNewChangeAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        animator.alpha(1);
    }

    /**
     * 更新时新的ViewHolder动画结束，执行还原
     * @param holder
     */
    @Override
    public void newChangeAnimationEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(1);
    }
}
