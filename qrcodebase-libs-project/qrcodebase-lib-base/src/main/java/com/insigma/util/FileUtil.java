package com.insigma.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {

    public  static File getFileByUrl(String path,InputStream inputStream)throws Exception{
        InputStream initialStream = inputStream;
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File(path);
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        return targetFile;
    }

    public static InputStream UrlToInputStream(String urls)throws Exception{
        URL url = new URL(urls);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        return inputStream;
    }
}
