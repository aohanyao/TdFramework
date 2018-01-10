package com.td.framework.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * @author jc
 * @date 2014-08-10
 */
public class FileUtil {


    /**
     * 默认APP根目录.
     */
    private static String downloadRootDir = null;
    /**
     * 默认下载图片文件目录.
     */
    private static String imageDownloadDir = null;
    private static String TAG = "FileUtils";

    /**
     * 复制文件
     * @param form
     * @param to
     */
    public static void fileChannelCopy(File form, File to) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        try {
            fi = new FileInputStream(form);
            fo = new FileOutputStream(to);
            FileChannel in = fi.getChannel();//得到对应的文件通道
            FileChannel out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fo != null) fo.close();
                if (fi != null) fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * //取得文件夹大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     * @param fileLen
     * @return
     */
    public static String formatFileSizeToString(long fileLen) {//
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLen < 1024) {
            fileSizeString = df.format((double) fileLen) + "B";
        } else if (fileLen < 1048576) {
            fileSizeString = df.format((double) fileLen / 1024) + "K";
        } else if (fileLen < 1073741824) {
            fileSizeString = df.format((double) fileLen / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileLen / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /***
     * 根据路径删除文件
     */
    public static boolean deleteFile(File file) throws IOException {
        return file != null && file.delete();
    }

    /***
     * 获取文件扩展名
     *
     * @param filename
     * @return 返回文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }




    /**
     * Gets the image download dir.
     *
     * @param context the context
     * @return the image download dir
     */
    public static String getImageDownloadDir(Context context) {
        initFileDir(context);
        return imageDownloadDir;
    }

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public static String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 描述：SD卡是否能用.
     *
     * @return true 可用,false不可用
     */
    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the file download dir.
     *
     * @param context the context
     * @return the file download dir
     */
    public static String getFileDownloadDir(Context context) {
        if (downloadRootDir == null) {
            initFileDir(context);
        }
        return downloadRootDir;
    }

    /**
     * 描述：初始化存储目录.
     *
     * @param context the context
     */
    public static void initFileDir(Context context) {

        //默认下载文件根目录.
        String downloadRootPath = File.separator + "YuanST" + File.separator + "Download" + File.separator;

        delete(new File(downloadRootPath));
        //默认下载图片文件目录.
        String imageDownloadPath = File.separator + "YuanST" + File.separator + "Image" + File.separator;
        try {
            if (!isCanUseSD()) {
                return;
            } else {
                File root = Environment.getExternalStorageDirectory();
                File downloadDir = new File(root.getAbsolutePath() + downloadRootPath);
                if (!downloadDir.exists()) {
                    downloadDir.mkdirs();
                }
                downloadRootDir = downloadDir.getPath();


                File imageDownloadDirFile = new File(root.getAbsolutePath() + imageDownloadPath);
                if (!imageDownloadDirFile.exists()) {
                    imageDownloadDirFile.mkdirs();
                }
                imageDownloadDir = imageDownloadDirFile.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //递归删除文件及文件夹
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * 读取指定文件的输出
     */
    public static String getFileOutputString(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path), 8192);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append("\n").append(line);
            }
            bufferedReader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回一个byte数组
     * @param file
     * @return
     * @throws IOException
     */
    public  static   byte[] getBytesFromFile(File file){

        byte[] bytes = null;
        try {
            InputStream is = new FileInputStream(file);

            // 获取文件大小
            long length = file.length();

            if (length > Integer.MAX_VALUE) {

                // 文件太大，无法读取
                throw new IOException("File is to large " + file.getName());

            }

            // 创建一个数据来保存文件数据

            bytes = new byte[(int) length];

            // 读取数据到byte数组中

            int offset = 0;

            int numRead = 0;

            while (offset < bytes.length

                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {

                offset += numRead;

            }

            // 确保所有数据均被读取

            if (offset < bytes.length) {

                throw new IOException("Could not completely read file "
                        + file.getName());

            }

            // Close the input stream and return bytes

            is.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return bytes;

    }
}
