package com.insigma.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by YS-GZD-1495 on 2018/2/7.
 */
public class MD5Util {

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String md5Password(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes("UTF-8"));
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 16位MD5加密
     *
     * @param s
     * @return
     */
    public final static String MD5TO16(String s) {
       return md5Password(s).substring(8,24);
    }

    public static void main(String[] args) {
        String temp1 = "{\"ver\":\"V1.0\",\"orgNo\":\"330200100014\",\"orgName\":\"宁波市中医院\",\"id\":\"\",\"inPut\":[{\"AAC002\":\"41018119910601455X\"}]}f750df4aaef0deb5c35a039b403220c220190819 16:49:19CM8YOVBJUBN71WXMWSRX9J5X1TQP9WN43db0b5132dbbf4e958034defaa3162d2";
        String temp2 = "{\"ver\":\"V1.0\",\n" +
                "\"orgNo\":\"331000100001\",\n" +
                "\"orgName\":\"浙江省台州医院\",\n" +
                "\"id\":\"\",\n" +
                "\"inPut\":\n" +
                "[{\"tradeNum\":\"\"}],\"appKey\":\"cffff7a14e21494d837762f3bce4d42e\",\"time\":\"20190820 14:53:09\",\"nonceStr\":\"cr78i3r0giudrd1jmz10cizbglnxaxu0\",\"secret\":\"a8b4718ef5464633b43f8bd09cc489dd\"}";
        System.out.println(temp2);
        System.out.println(md5Password(temp1).toUpperCase());
        System.out.println(md5Password(temp2).toUpperCase());
        for (int i = 0; i < temp1.length(); i++) {
            if (temp1.charAt(i) != temp2.charAt(i)) {
                System.out.println(i + " " + temp1.charAt(i) + " " + temp2.charAt(i));
            }
        }
    }
}
