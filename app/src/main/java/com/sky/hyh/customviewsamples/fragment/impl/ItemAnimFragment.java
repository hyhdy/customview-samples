package com.sky.hyh.customviewsamples.fragment.impl;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.recyclerviewdemo.itemanim.FadeItemAnimator;
import com.sky.hyh.customviewsamples.recyclerviewdemo.ItemAnimatorAdapter;
import com.sky.hyh.customviewsamples.recyclerviewdemo.itemanim.RotateItemAnimator;
import com.sky.hyh.customviewsamples.recyclerviewdemo.itemanim.ScaleItemAnimator;
import com.sky.hyh.customviewsamples.recyclerviewdemo.itemanim.SlideItemAnimator;

import java.util.ArrayList;
import java.util.List;

@InjectFragment()
//recyclerview动画
public class ItemAnimFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView mRv;
    private Button btnAdd,btnRemove,btnUpdate;
    private List<String> mData=new ArrayList<>();
    private ItemAnimatorAdapter mAdapter;
    private boolean isFirst=true;
    private Spinner mSpinner;

    @Override
    protected int getResId() {
        return R.layout.fragment_item_animator;
    }

    @Override
    protected void initViews(View rootView) {
        mRv= (RecyclerView) rootView.findViewById(R.id.rv);
        mSpinner= (Spinner) rootView.findViewById(R.id.spinner);
        btnAdd= (Button) rootView.findViewById(R.id.btn_add);
        btnRemove= (Button) rootView.findViewById(R.id.btn_remove);
        btnUpdate= (Button) rootView.findViewById(R.id.btn_update);

        mRv.setItemAnimator(new FadeItemAnimator());
        mRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0,10,0,10);
            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mRv.setItemAnimator(new FadeItemAnimator());
                        break;
                    case 1:
                        mRv.setItemAnimator(new SlideItemAnimator());
                        break;
                    case 2:
                        mRv.setItemAnimator(new RotateItemAnimator());
                        break;
                    case 3:
                        mRv.setItemAnimator(new ScaleItemAnimator());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter=new ItemAnimatorAdapter(getContext(),mData);
        mRv.setAdapter(mAdapter);
        for(int i=0;i<50;i++){
            mData.add("我是"+i);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                if (isFirst){
                    mData.add(1,"我是添加的");
                    mAdapter.notifyItemInserted(1);
                }else{
                    mData.add(1,"我是添加的");
                    mData.add(2,"i am add");
                    mAdapter.notifyItemRangeInserted(1,2);
                }
                break;
            case R.id.btn_remove:
                if (isFirst){
                    mData.remove(1);
                    mAdapter.notifyItemRemoved(1);
                }else{
                    mData.remove(1);
                    mData.remove(1);
                    mAdapter.notifyItemRangeRemoved(1,2);
                }
                break;
            case R.id.btn_update:
                if (isFirst) {
                    mData.set(1, "我是更新的");
                    mAdapter.notifyItemChanged(1);
                }else{
                    mData.set(1, "我是更新的");
                    mData.set(2, "我是更新的2");
                    mAdapter.notifyItemRangeChanged(1,2);
                }
                break;
            default:
                break;
        }
    }
}
