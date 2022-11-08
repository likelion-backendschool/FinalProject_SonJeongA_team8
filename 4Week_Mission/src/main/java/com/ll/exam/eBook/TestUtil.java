package com.ll.exam.eBook;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestUtil {

    public static <T> T callMethod(Object obj, String methodName) {
        Method method = null;

        try {
            method = obj.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        method.setAccessible(true);

        try {
            return (T) method.invoke(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}


