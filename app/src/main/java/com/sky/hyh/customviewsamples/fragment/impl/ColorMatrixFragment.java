package com.sky.hyh.customviewsamples.fragment.impl;

import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.ColorMatrixView;

/**
 * Created by hyh on 2018/10/27 20:45
 * E-Mail Address：fjnuhyh122@gmail.com
 */
@InjectFragment()
public class ColorMatrixFragment extends BaseFragment {
    private SeekBar sb_red, sb_green, sb_blue;
    private ColorMatrixView mColorMatrixView;

    @Override
    protected int getResId() {
        return R.layout.fragment_colormatrix;
    }

    @Override
    protected void initViews(View rootView) {
        sb_red =  rootView.findViewById(R.id.sb_red);
        sb_green = rootView.findViewById(R.id.sb_green);
        sb_blue =  rootView. findViewById(R.id.sb_blue);

        sb_red.setOnSeekBarChangeListener(seekBarChange);
        sb_green.setOnSeekBarChangeListener(seekBarChange);
        sb_blue.setOnSeekBarChangeListener(seekBarChange);

        mColorMatrixView = rootView.findViewById(R.id.cmv_image);
    }

    private SeekBar.OnSeekBarChangeListener seekBarChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
                int progressR = sb_red.getProgress();
                int progressG = sb_green.getProgress();
                int progressB = sb_blue.getProgress();
                Log.d("hyh", "ColorMatrixFragment: onStopTrackingTouch: progressR="+progressR+" ,progressG="+progressG+" ,progressB="+progressB);
                int color = getIntFromColor(progressR, progressG, progressB);
                Log.d("hyh", "ColorMatrixFragment: onStopTrackingTouch: color ="+color);
                mColorMatrixView.setColor(color);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
        }
    };

    private int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
