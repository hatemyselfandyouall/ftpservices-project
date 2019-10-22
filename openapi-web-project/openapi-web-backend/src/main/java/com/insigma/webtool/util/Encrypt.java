package com.insigma.webtool.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt
{
  private static final Logger logger = LoggerFactory.getLogger(Encrypt.class);
  public static final String KEY = "epsoftep400-8671";
  public static final String IV = "epsoftep400-8671";
  
  public static String encrypt(String data)
  {
    try
    {
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      int blockSize = cipher.getBlockSize();
      byte[] dataBytes = data.getBytes();
      int plaintextLength = dataBytes.length;
      if (plaintextLength % blockSize != 0) {
        plaintextLength += blockSize - plaintextLength % blockSize;
      }
      byte[] plaintext = new byte[plaintextLength];
      System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
      SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(), "AES");
      IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
      cipher.init(1, keyspec, ivspec);
      byte[] encrypted = cipher.doFinal(plaintext);
      return new String(Base64.encodeBase64(encrypted));
    }
    catch (Exception e)
    {
      logger.info("AES加密错误  data={}", data);
    }
    return null;
  }
  
  public static String desEncrypt(String data)
  {
    try
    {
      byte[] encrypted1 = Base64.decodeBase64(data);
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(), "AES");
      IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
      cipher.init(2, keyspec, ivspec);
      byte[] original = cipher.doFinal(encrypted1);
      String originalString = new String(original);
      return originalString.trim();
    }
    catch (Exception e)
    {
      logger.info("AES解密错误  data={}", data);
    }
    return null;
  }
  
  public static String desEncrypt2(String data)
  {
    String ret = desEncrypt(data);
    if (ret == null) {
      return data;
    }
    return ret;
  }
  
  public static void main(String[] args)
  {
    String password = "yVe2JYhCh+q8NYPGuiti+w==";
    
    System.out.println(encrypt("123"));
    System.out.println(desEncrypt2(password));
  }
}