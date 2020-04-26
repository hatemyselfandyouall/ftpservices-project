package com.insigma.utils;

public class NumbersUtil {

    /**
     * 获取指定位数的序列号
     * @return
     */
    public static String  getSortNumber(int sortNumber,int digit){
        String result=sortNumber+"";
        if (result.length()>=digit){
            return result;
        }else {
            while (result.length()<digit){
                result="0"+result;
            }
            return result;
        }
    }

    public static void main(String[] args) {
        System.out.println(getSortNumber(1999,3));
    }
}
