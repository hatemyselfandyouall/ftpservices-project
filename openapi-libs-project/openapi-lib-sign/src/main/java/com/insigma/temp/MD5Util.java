package com.insigma.temp;

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
            // ?????????????
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes("UTF-8"));
            StringBuffer buffer = new StringBuffer();
            // ??????byte ??????????? 0xff;
            for (byte b : result) {
                // ??????
                int number = b & 0xff;// ????
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // ?????md5????????
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 16λMD5????
     *
     * @param s
     * @return
     */
    public final static String MD5TO16(String s) {
       return md5Password(s).substring(8,24);
    }

    public static void main(String[] args) {
        String temp1 = "{\"ver\":\"V1.0\",\"orgNo\":\"330000101007\",\"orgName\":\"????\",\"trade\":\"7011\",\"id\":\"\",\"inPut\":[{\"COUNT\":\"1\",\"LS_DT1\":[{\"AKC190\":\"587082\",\"BKC022\":\"3000000327\",\"AKA077\":\"0\",\"AAC003\":\"????\",\"AAC002\":\"33032519790707094X\",\"BKE100\":\"02-10086-O0011717\",\"AAE030\":\"2019-06-12\",\"AKA078\":\"1\",\"AKE024\":\"1\",\"AKA120\":\"2\",\"AKA121\":\"1\",\"AKC264\":\"10\",\"LS_DT2\":[{\"BKE100\":\"02-10086-O0011717\",\"BKA100\":\"1\",\"BKA101\":\"1\",\"BKA102\":\"1\",\"BKA104\":\"330000101007\",\"BKA105\":\"10086\",\"AAE036\":\"2019-06-12\",\"AAC003\":\"????\",\"AAC002\":\"33032519790707094X\",\"AKC264\":\"10\"}],\"LS_DT3\":[{\"BKA120\":\"05522\",\"BKA121\":\"????\"}]}]}],\"appKey\":\"22883ff53a7644a2aef0bf0c8a714c63\",\"time\":\"20190731 10:28:30\",\"nonceStr\":\"249a96aaad554bd8bafe818426b7b474\",\"secret\":\"2fc1504ece144c619cf9b1bddd929e8c\"}";
        String temp2 = "{\"ver\":\"V1.0\",\"orgNo\":\"330000101009\",\"orgName\":\"???????\",\"id\":\"\",\"inPut\":[{\"tradeNum\":\"500\"}],\"appKey\":\"c5f57b410fb5495f948da0255f239dce\",\"time\":\"20190731 14:49:45\",\"nonceStr\":\"1fd09993b3764a958847b0987a8849ff\",\"secret\":\"09d835206b3e4ecf94c689d45277dd9d\"}";
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
