package com.insigma.util;


import com.alibaba.fastjson.JSONObject;
import com.taobao.diamond.extend.DynamicProperties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import star.util.StringUtil;
import sun.swing.StringUIClientPropertyKey;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public final class HttpUtil {


    public static final String tempFile="temp.txt";

    public static String upload(String code) throws Exception{
        File file = new File(tempFile);
        file.createNewFile();
        FileWriter fileWriter=new FileWriter(file);
        fileWriter.write(code);
        fileWriter.flush();
        fileWriter.close();
        PostMethod filePost = new PostMethod(DynamicProperties.staticProperties.getProperty("oss.upload.http.url"));
        HttpClient client = new HttpClient();

        try {
            // 通过以下方法可以模拟页面参数提交
//            filePost.setParameter("isPublic", "1");

            Part[] parts = { new FilePart("file", file),new StringPart("isPublic", "1") };
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                String response = new String(filePost.getResponseBodyAsString().getBytes("utf-8"));;
                JSONObject responJSON=JSONObject.parseObject(response);
                System.out.println("上传成功"+response);
                return responJSON.getJSONObject("result").getString("key");
            } else {
                System.out.println("上传失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            filePost.releaseConnection();
        }
        return "";
    }
    public static void main(String[] args) throws Exception {
    }
}
