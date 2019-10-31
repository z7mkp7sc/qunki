package com.wjj.wm.common.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @ClassName AESUtil
 * @Description AES加密解密工具类
 * @Author weng_jun_ji_
 * @Date 2019/9/27 15:04
 * @Vervsion 1.0
 */
@Slf4j
public class AESUtil {

    private static final String encodeRules = "qunKi2019";

    /**
     * @Description:加密
     * @Date&Author 2019/9/27 by weng_jun_ji_
     * @Param [content 需要加密的明文]
     * @Return 密文
     **/
    public static String AESEncode(String content) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            //10.加密后的数据转换为字符串并返回
            return new BASE64Encoder().encode(byte_AES);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException
                | UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }

        //如果有错就返加nulll
        return null;
    }

    /**
     * @Description:
     * @Date&Author 2019/9/27 by weng_jun_ji_
     * @Param [content 需要解密的密文]
     * @Return 明文
     **/
    public static String AESDecode(String content) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            //解密
            byte[] byte_decode = cipher.doFinal(byte_content);
            return new String(byte_decode, "utf-8");
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | IOException
                | BadPaddingException e) {
            log.error(e.getMessage(), e);
        }

        //如果有错就返加nulll
        return null;
    }

    public static void main(String[] args) {
        String aesEncode = AESEncode("123456789asd");
        System.out.println("AES加密: " + aesEncode);
        System.out.println("AES解密: " + AESDecode(aesEncode));

        //AES加密后进行MD5加密
        String key = "jjj";
        String md5Aes = MD5Util.md5Encode(aesEncode, key);
        System.out.println("aes加密后再md5加密: " + md5Aes);
        System.out.println(MD5Util.md5verify(aesEncode, key, md5Aes));
    }
}
