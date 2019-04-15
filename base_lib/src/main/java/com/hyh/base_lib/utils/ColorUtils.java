package com.hyh.base_lib.utils;

public class ColorUtils {
    public static final int A_MASK = 0xff000000;
    public static final int R_MASK = 0x00ff0000;
    public static final int G_MASK = 0x0000ff00;
    public static final int B_MASK = 0x000000ff;

    public static final int A_SHIFT= 24;
    public static final int R_SHIFT= 16;
    public static final int G_SHIFT = 8;

    public static int getA(int color){
        return (color & A_MASK) >> A_SHIFT;
    }

    public static int getR(int color){
        return (color & R_MASK) >> R_SHIFT;
    }

    public static int getG(int color){
        return (color & G_MASK) >> G_SHIFT;
    }

    public static int getB(int color){
        return color & B_MASK;
    }
}
