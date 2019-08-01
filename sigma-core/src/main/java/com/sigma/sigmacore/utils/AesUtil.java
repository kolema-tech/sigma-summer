package com.sigma.sigmacore.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Aes加密工具类
 * <p>
 * sigma 里面的AesUtil Linux中会有javax.crypto.BadPaddingException: Given final block not properly padded问题
 * 1、如果不引入local_policy 跟 Us_export_policy 两个Jar 到JRE中，而为保险还是不引入
 * 2、此处采用 getSecureRandom 方法的实现
 *
 * @author ware zhang
 * @version 1.0.0
 * @date 2018/12/5 15:54
 */
public class AesUtil {

    private static final String AES_PWD = "123456";
    private static final Integer BINARY_BIT = 2;

    public AesUtil() {
    }

    public static String encrypt(String content) throws Exception {
        return encrypt(content, "123456");
    }

    public static String encrypt(String content, String password) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = getSecureRandom(password);
        keygen.init(128, secureRandom);
        SecretKey originalKey = keygen.generateKey();
        byte[] raw = originalKey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, skeySpec);
        byte[] byteContent = content.getBytes("utf-8");
        byte[] bytes = cipher.doFinal(byteContent);
        return bytesToHexString(bytes);
    }

    public static String decrypt(String content) throws Exception {
        return decrypt(content, AES_PWD);
    }

    public static String decrypt(String hexString, String password) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = getSecureRandom(password);
        keygen.init(128, secureRandom);
        SecretKey originalKey = keygen.generateKey();
        byte[] raw = originalKey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, skeySpec);
        byte[] content = hexStringToBytes(hexString);
        byte[] bytes = cipher.doFinal(content);
        return new String(bytes);
    }

    private static SecureRandom getSecureRandom(String password) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());
        return secureRandom;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src != null && src.length > 0) {
            for (int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv.toUpperCase());
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString != null && !"".equals(hexString)) {
            int length = hexString.length() / 2;
            if (hexString.length() % BINARY_BIT != 0) {
                return null;
            } else {
                byte[] d = new byte[length];

                for (int i = 0; i < length; ++i) {
                    int pos = i * 2;
                    d[i] = (byte) (charToByte(hexString.charAt(pos)) << 4 | charToByte(hexString.charAt(pos + 1)));
                }

                return d;
            }
        } else {
            return null;
        }
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
