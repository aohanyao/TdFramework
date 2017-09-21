package com.td.framework.utils.cache;

import android.content.Context;

import com.td.framework.utils.L;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 江俊超 on 2017/1/19 0019.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>序列化对象的工具类</p>
 */
public class SerializableUtil<T> {
    private Context mContext;

    public SerializableUtil(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 序列化数据
     *
     * @param serializable
     * @param fileName
     * @return
     */
    public boolean saveSerializableObject(T serializable, String fileName) {
        File file = new File(mContext.getFilesDir().getAbsoluteFile() + File.separator + fileName);
        L.e("保存成功："+file.getAbsolutePath());
        return saveSerializableObject(serializable, file);
    }

    /**
     * 保存数据
     *
     * @param serializable
     * @param file
     * @return
     */
    private boolean saveSerializableObject(T serializable, File file) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file.toString());
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(serializable);
            return true;
        } catch (IOException e) {
            if (L.isDebug)
                e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存缓存数据
     *
     * @param serializable
     * @param fileName
     * @return
     */
    public boolean cacheSerializableObject(T serializable, String fileName) {
        File file = new File(mContext.getFilesDir().getAbsoluteFile() + File.separator + "cache" + File.separator + fileName);
        return saveSerializableObject(serializable, file);
    }

    /**
     * 删除序列化的文件
     *
     * @param fileName
     */
    public void clearSerializable(String fileName) {
        File file = new File(mContext.getFilesDir().getAbsoluteFile() + File.separator + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.delete();
            //删除缓存的序列化文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        File file = new File(mContext.getFilesDir().getAbsoluteFile() + File.separator + "cache");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.delete();
            //删除缓存的序列化文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取存储的对象
     *
     * @param fileName
     * @return
     */
    public T getSerializable(String fileName) {
        File file = new File(mContext.getFilesDir().getAbsoluteFile() + File.separator + fileName);
        return getSerializable(file);
    }

    /**
     * 获取序列化的对象
     *
     * @param file
     * @return
     */
    private T getSerializable(File file) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        //存入数据
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            //L.e(file.getAbsolutePath());
            //取出数据
            fileInputStream = new FileInputStream(file.toString());
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (T) objectInputStream.readObject();

        } catch (Exception e) {//为空的情况
//            if (L.isDebug)
//                e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取缓存对象
     *
     * @param fileName
     * @return
     */
    public T getCache(String fileName) {
        //存入数据
        File file = new File(mContext.getFilesDir().getAbsoluteFile() + File.separator + "cache" + File.separator + fileName);
        try {
            return getSerializable(file);

        } catch (Exception e) {
            return null;
        }
    }
}
