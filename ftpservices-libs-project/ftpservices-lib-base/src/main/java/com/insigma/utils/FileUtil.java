package com.insigma.utils;

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

    public static File UrlToInputStream(String urls,String path)throws Exception{
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
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(path);
        FileOutputStream fos;
        fos = new FileOutputStream(saveDir);
        fos.write(getData);
        return saveDir;
    }
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
