package com.insigma.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtil {

    private static String paramString="{\n" +
            "  \"ver\": \"V1.0\",\n" +
            "  \"orgNo\": \"330000101011\",\n" +
            "  \"orgName\": \"浙江省立同德医院\",\n" +
            "  \"id\": \"\",\n" +
            "  \"inPut\": [\n" +
            "    {\n" +
            "      \"tradeNum\": \"\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    public static void main(String[] args) {
        paramString.toCharArray();
    }

    public static String StingPut(String key,String param){
        String temp=paramString.substring(0,paramString.lastIndexOf("}"));

        return null;
    }
}
