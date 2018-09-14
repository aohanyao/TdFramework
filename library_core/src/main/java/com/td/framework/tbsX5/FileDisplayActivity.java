package com.td.framework.tbsX5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.td.framework.R;
import com.td.framework.global.app.Constant;
import com.td.framework.utils.FileUtil;
import com.td.framework.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//@Route(path = RouteX5.ROUTE_FILE_DISPLAY)
public class FileDisplayActivity extends AppCompatActivity {

    private String TAG = "FileDisplayActivity";
    private SuperFileView2 mSuperFileView;
    private String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_display);
        init();
    }


    public void init() {
        Intent intent = this.getIntent();
        filePath = (String) intent.getSerializableExtra(Constant.IDK);
        L.d(TAG, "文件path:" + filePath);
        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(this, "filePath==null", Toast.LENGTH_SHORT).show();
            return;
        }

        mSuperFileView = (SuperFileView2) findViewById(R.id.mSuperFileView);
        mSuperFileView.setOnGetFilePathListener(new SuperFileView2.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(SuperFileView2 mSuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2);
            }
        });

        mSuperFileView.show();
    }


    private void getFilePathAndShowFile(SuperFileView2 mSuperFileView2) {
        if (filePath.contains("http")) {//网络地址要先下载

            downLoadFromNet(filePath, mSuperFileView2);

        } else {
            mSuperFileView2.displayFile(new File(filePath));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("FileDisplayActivity-->onDestroy");
        if (mSuperFileView != null) {
            mSuperFileView.onStopDisplay();
        }
    }

    private void downLoadFromNet(final String url, final SuperFileView2 mSuperFileView2) {

        //1.网络下载、存储路径、
        File cacheFile = getCacheFile(url);
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                L.d(TAG, "删除空文件！！");
                cacheFile.delete();
            } else {
                mSuperFileView2.displayFile(cacheFile);
                return;
            }
        }

        LoadFileModel.loadPdfFile(url, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                L.d(TAG, "下载文件-->onResponse");
                boolean flag;
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    ResponseBody responseBody = response.body();
                    is = responseBody.byteStream();
//                    long total = responseBody.contentLength();

                    //fileN : /storage/emulated/0/pdf/kauibao20170821040512.pdf
                    File fileN = getCacheFile(url);//new File(getCacheDir(url), getFileName(url))

                    L.d(TAG, "创建缓存文件： " + fileN.toString());
                    if (!fileN.exists()) {
                        boolean mkdir = fileN.createNewFile();
                    }
                    fos = new FileOutputStream(fileN);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
//                        int progress = (int) (sum * 1.0f / total * 100);
//                        L.d(TAG, "写入缓存文件" + fileN.getName() + "进度: " + progress);
                    }
                    fos.flush();
                    L.d(TAG, "文件下载成功,准备展示文件。");
                    //2.ACache记录文件的有效期
                    mSuperFileView2.displayFile(fileN);
                } catch (Exception e) {
                    L.d(TAG, "文件下载异常 = " + e.toString());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                L.d(TAG, "文件下载失败");
                File file = getCacheFile(url);
                if (!file.exists()) {
                    L.d(TAG, "删除下载失败文件");
                    file.delete();
                }
            }
        });
    }

    /***
     * 获取缓存目录
     *
     * @param url
     * @return
     */
//    private File getCacheDir(String url) {
//        return new File(FileUtil.getFileDownloadDir());
//
//    }

    /***
     * 绝对路径获取缓存文件
     *
     * @param url
     * @return
     */
    private File getCacheFile(String url) {
        File cacheFile = new File(FileUtil.getFileDownloadDir()+File.separator
                + getFileName(url));
        L.d(TAG, "缓存文件 = " + cacheFile.toString());
        return cacheFile;
    }

    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     *
     * @param url
     * @return
     */
    private String getFileName(String url) {
        String fileName = Md5Tool.hashKey(url) + "." + getFileType(url);
        return fileName;
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            L.d(TAG, "paramString---->null");
            return str;
        }
        L.d(TAG, "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            L.d(TAG, "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        L.d(TAG, "paramString.substring(i + 1)------>" + str);
        return str;
    }


}
