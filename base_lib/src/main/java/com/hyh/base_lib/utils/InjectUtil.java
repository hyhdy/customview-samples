package com.hyh.base_lib.utils;

import com.hyh.base_lib.annotation.FindViewByIdAno;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class InjectUtil {

    public static void injectView(Object activityOrFragment) {
        Class<?> activityOrFragmentClass = activityOrFragment.getClass();
        if (activityOrFragmentClass != null) {
            Field[] declaredFields = activityOrFragmentClass.getDeclaredFields();
            if (declaredFields != null) {
                for (Field field : declaredFields) {
                    FindViewByIdAno findViewByIdAno = field.getAnnotation(FindViewByIdAno.class);
                    if (findViewByIdAno != null) {
                        int id = findViewByIdAno.value();
                        Object fieldObj = null;
                        try {
                            fieldObj = activityOrFragmentClass.getMethod("findViewById", int.class)
                                    .invoke(activityOrFragment, id);
                            field.setAccessible(true);
                            field.set(activityOrFragment, fieldObj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
