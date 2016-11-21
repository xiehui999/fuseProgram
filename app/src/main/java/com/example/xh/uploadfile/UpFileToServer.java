package com.example.xh.uploadfile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiehui on 2016/10/16.
 */
public class UpFileToServer {

    private Map<String, String> params = new HashMap<String, String>();
   	private String actionUrl = "http://192.168.5.127:8080/receive/";

    private Context context;
    private ChunkInfo chunkInfo;

    public UpFileToServer(Context context) {
        this.context = context;
    }

    public String upToServer(ChunkInfo chunkInfo, String upSort) {
        // 返回值0、成功1、网络原因2、调用相机并上传图片出现问题3、上传失败，服务器端问题
        this.chunkInfo = chunkInfo;
        // 异常处理，部分机型可能存在无法读取存储卡中照片路径
        if (upSort.equals("upchunks")) {
            params.put("gguid", chunkInfo.getGguid());
            params.put("chunk", chunkInfo.getChunk() + "");
            params.put("chunks", chunkInfo.getChunks() + "");
            params.put("id", chunkInfo.getMd5());
             actionUrl = "http://192.168.5.127:808/receive/slicefiler";
        } else if (upSort.equals("upchunk")) {
            params.put("gguid", chunkInfo.getGguid());
            actionUrl += "fulfilepath";
        }
        // 执行文件流上传
        String result = "";
        result = uploadFile2(upSort);
        // 上传成功删除文件
        return result;
    }

    public String uploadFile2(String upSort) {
        String retMsg = "1";

        CustomMultipartEntity mpEntity = new CustomMultipartEntity(
                new CustomMultipartEntity.ProgressListener() {
                    @Override
                    public void transferred(long num) {
                        Intent intent2 = new Intent();
                        ChunkInfo chunkIntent = new ChunkInfo();
                        chunkIntent.setChunks(chunkInfo.getChunks());
                        chunkIntent.setChunk(chunkInfo.getChunk());
                        chunkIntent.setProgress((int) num);
                        intent2.putExtra("chunkIntent", chunkIntent);
                        intent2.setAction("ACTION_UPDATE");
                        context.sendBroadcast(intent2);
                    }
                });
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {

                StringBody par = null;
                try {
                    if (entry.getValue() == null) {
                        continue;
                    }
                    par = new StringBody(java.net.URLEncoder.encode(
                            entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mpEntity.addPart(entry.getKey(), par);
            }
        }
            if(upSort.equals("upchunks")){//分片上传
                CustomFileBody customFileBody = new CustomFileBody(chunkInfo);
                mpEntity.addPart("file", customFileBody);
            }else{//单文件上传
                FileBody fileBody = new FileBody(new File(chunkInfo.getFilePath()));
                mpEntity.addPart("file", fileBody);
            }
        long max = mpEntity.getContentLength();
        Intent intent = new Intent();
        intent.putExtra("max", (int) max);
        intent.setAction("upfile");
        context.sendBroadcast(intent);
        HttpPost post = new HttpPost(actionUrl);
        // 发送请求体
        post.setEntity(mpEntity);
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            dhc.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
            HttpResponse response = dhc.execute(post);
            int res = response.getStatusLine().getStatusCode();
            Log.e("图片上传返回相应码", res + ",");
            switch (res) {
                case 200:
                    //流形式获得
                    StringBuilder builder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
                        builder.append(s);
                    }
                    retMsg = builder.toString();
                    break;
                case 404:
                    retMsg = "-1";
                    break;
                default:
                    retMsg = "500";
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retMsg;

    }

    /**
     * 查询图片文件，查询重传日志
     *
     * @return
     */
    public String getHttpPost() {
        String retMsg = "-1";
        try {
            URL targetUrl = new URL(actionUrl);
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (entry.getValue() == null) {
                        continue;
                    }
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            HttpEntity httpEntity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
            HttpPost post = new HttpPost(actionUrl);
            // 发送请求体
            post.setEntity(httpEntity);
            DefaultHttpClient httpclient = new DefaultHttpClient();
            //取得HttpResponse
            HttpResponse httpResponse = httpclient.execute(post);
            int res = httpResponse.getStatusLine().getStatusCode();
            Log.e("图片上传返回相应码", res + ",");
            switch (res) {
                case 200:
                    //流形式获得
                    StringBuilder builder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
                        builder.append(s);
                    }
                    retMsg = builder.toString();
                    break;
                case 404:
                    retMsg = "-1";
                    break;
                default:
                    retMsg = "500";
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return retMsg;
    }

    /**
     * HttpURLConnection请求
     * @param chunkInfo
     * @return
     */
    public String get(ChunkInfo chunkInfo) {
        String retMsg = "-1";
        try {
            URL targetUrl = new URL(actionUrl);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) targetUrl.openConnection();
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setRequestProperty("Content-Type", "text/plain");
            OutputStream os = httpUrlConnection.getOutputStream();
            String param = new String();
            param = "gguid=" + chunkInfo.getGguid() +
                    "&accessCode=" + chunkInfo.getMd5();
            os.write(param.getBytes());
            int res = httpUrlConnection.getResponseCode();
            Log.e("图片上传返回相应码", res + ",");
            switch (res) {
                case 200:
                    retMsg = httpUrlConnection.getResponseMessage();
                    break;
                case 404:
                    retMsg = "-1";
                    break;
                default:
                    retMsg = "500";
            }
            httpUrlConnection.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return retMsg;
    }
}
