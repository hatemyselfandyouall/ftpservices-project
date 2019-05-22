package com.insigma.util;

import star.util.StringUtil;

public class PasswordUtils {

    public static String encodePassWord(String passwd,String salt) {
        //todo 我知道md5已经被破解了
        String result= StringUtil.getMD5(passwd);
        for (int i=0;i<100;i++){
            result=StringUtil.getMD5(result);
        }
        result+=salt;
        for (int i=0;i<100;i++){
            result=StringUtil.getMD5(result);
        }
        return result;
    }
}
