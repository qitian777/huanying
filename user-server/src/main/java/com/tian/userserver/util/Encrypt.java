package com.tian.userserver.util;

import com.tian.serverapi.util.RespBeanEnum;
import com.tian.userserver.config.exception.GlobalException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description: AES加密解密
 * @Author QiGuang
 * @Date 2022/6/21
 * @Version 1.0
 */
public class Encrypt {

    //--------------AES---------------
    private static final String KEY = "f4k9f5w7f8g4er26";  // 密匙，必须16位
    private static final String ENCODING = "UTF-8"; // 编码
    private static final String ALGORITHM = "AES"; //算法
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding"; // 默认的加密算法，ECB模式

    /**
     *  AES加密
     * @param data
     * @return String
     */
    public static String AESencrypt(String data) throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(KEY.getBytes(),"AES"));
        byte[] b = cipher.doFinal(data.getBytes("utf-8"));
        //采用base64算法进行转码,避免出现中文乱码
        return Base64.encodeBase64String(b);
    }

    /**
     * AES解密
     * @param data
     * @return String
     */
    public static String AESdecrypt(String data) {
        try{
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"));
            byte[] b = cipher.doFinal(Base64.decodeBase64(data));
            return new String(b);
        }catch (Exception e){
            throw new GlobalException(RespBeanEnum.DECODE_ERROR);
        }
        //采用base64算法进行转码,避免出现中文乱码
    }
}
