package com.yenso.yensoserver.Service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


@Component("Crypto")
public class Crypto implements InitializingBean  {

    @Value("${key}")
    private  String dataKey;
    private byte[] keyData;

    public String encode(String str) throws Exception {

        SecretKey secretKey = new SecretKeySpec(keyData,"AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey,new IvParameterSpec(keyData));

        byte[] encodeData = cipher.doFinal(str.getBytes("UTF-8"));
        return new String(Base64.encodeBase64(encodeData));
    }

    public String decode(String str) throws Exception{
        SecretKey secretKey = new SecretKeySpec(keyData,"AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,secretKey,new IvParameterSpec(keyData));

        byte[] decodeData = Base64.decodeBase64(str.getBytes());
        return new String(cipher.doFinal(decodeData));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        keyData = dataKey.substring(0,16).getBytes();
    }
}
