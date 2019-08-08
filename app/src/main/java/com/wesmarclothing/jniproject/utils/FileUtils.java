package com.wesmarclothing.jniproject.utils;

import android.Manifest;
import android.util.Log;
import com.wesmarclothing.jniproject.aop.Premission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName FileUtils
 * @Date 2019/7/12 19:06
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
public class FileUtils {

    /**
     * 文件复制.
     */
    public static boolean copy(File srcFile, File destFile) {


        try {
            FileInputStream in = new FileInputStream(srcFile);
            FileOutputStream out = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024 * 5];
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Premission(Manifest.permission.CAMERA)
    public void test() {
        Log.d("test", "申请权限");
    }


    public static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> componentType = arrayLhs.getClass().getComponentType();

        int i = Array.getLength(arrayLhs);

        int j = i + Array.getLength(arrayRhs);


        Object result = Array.newInstance(componentType, j);

        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }

        }
        return result;
    }

}
