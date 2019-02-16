package com.arthur.gc;

import org.springframework.util.StringUtils;

import java.io.PrintStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;

public class CryptographUtil
{
    static String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkMvMYw6DdWFceuhqLvUHd06pkdMQ7SPnIzALnuo94lW/UtiKmam3wI7d2QdzCYA0RlCxjyye6q6D3guZHelHl6PMEvD5iOredYV+Rqvi5hbCZJoBBW5XKy2LBOe3aTcXAc3AtE+3D8va1CCYGcaH1ca1lrtyGlI75RSgRR9G9iSeQukmFd4hR4QCBwoV+xKmIlf3dKy7/YK/ngddqLCdJlTeItCx5kB2gMG3hIZscvJZxlUSrBaikNmi2Re36z8FrZ7WGiiCsqPtUE7XBtgbvGZp+gkPUGgAKIbgNZFnCtaat9h+mteiSDezwq/LFqicHPPT/QurI1/fCujGYdlcDQIDAQAB";
    static String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCQy8xjDoN1YVx66Gou9Qd3TqmR0xDtI+cjMAue6j3iVb9S2IqZqbfAjt3ZB3MJgDRGULGPLJ7qroPeC5kd6UeXo8wS8PmI6t51hX5Gq+LmFsJkmgEFblcrLYsE57dpNxcBzcC0T7cPy9rUIJgZxofVxrWWu3IaUjvlFKBFH0b2JJ5C6SYV3iFHhAIHChX7EqYiV/d0rLv9gr+eB12osJ0mVN4i0LHmQHaAwbeEhmxy8lnGVRKsFqKQ2aLZF7frPwWtntYaKIKyo+1QTtcG2Bu8Zmn6CQ9QaAAohuA1kWcK1pq32H6a16JIN7PCr8sWqJwc89P9C6sjX98K6MZh2VwNAgMBAAECggEALhVOtXAXErKr9komEU/W0nfsrAaa6n50v9Q4vpkanQBhZgKZChLj8psFrF8ck7eD66Ue/cxbv6FPQso6LzThnjd5acXcBWGK76hO6jsmechgkhgZnxrhD1LtXeBJlWj9H4vWewyr3JEq0jyVbA6tiLI3dQOvawUlHuGTqKwtr4S5YDA6WW6mNj1OKX9LyEvce591f/fBnqaKq1d8UB2q+TIlLoscdgXsGvLUJp2wsznRiMltH2JgEbnHIAMjrdj637GXaqIw2V2bNJTS7zK3/lmRwZR+zhI7H9N8RKZKvr+KLBrxhC91cTMK2XPuTW+LwbfWR4VuZZ2Z9pjZpI+uAQKBgQDHlZ78f/WFFJNqRF3SirJmBAQTOIb/BZqq5J4Nq2dQDuPLd75ebusasnlc/MHiaKbsXNtxi3WbmynJG6Y8xrn1mLX5bP3KXZ2uzYiQOzEbCI2QcHjXCWSP/UZdN7qR/A/rrB3X+5T+v5uGvnBdqeMbO9sXsZPvc6VKnECS6s4EzQKBgQC5uZFqZMcoNCxPgkr+JeFh4HhawS0oCYE2jSa+0t6VDXY/aKskl5VZ1NNAUi8IP9M8e4ellcXtjWnVDdsV8rS5X6Y6XoxAWiYsOJfjhwX8GWDpgNiaoUjLCn+4CSxJnkxtwVzclkOgEH724m/i1zV4z/fE3psIW7RmLvKf2NK0QQKBgQC2kemjdLH1EMNg3DUvGWzj4RyRMI5czpWTahoiyv5wv44lW94qqDAsdcKLCzOYlr7LTI34qefldeSdcMt6oRcO0Kj2B3uJGGXbt2wK8/qRjhthW2FO4+q3xKoPL6FdVUSDAykxn5PGDSSJ9772DygOi4x5ehmnWxSC0bhILL5UqQKBgCFAvm6EOLxUCxL5SyAR/DagECx/pewjN4Vi5GsKKlwZmvBjdzEICu+YkXJUxKL/eny7yV+2X0fXJ/nDj1Rwrjb6jzKoNwhWm7sTLu7IACRRDu7/6MhW1Ee2S4PR8KIFu71y/4NnTBhem7zscgf1bFeDNOOEz9FBSh15jmtcUO2BAoGBALlHCY2Wn/TjdSCnEWZkOGpoI84A2YAoKHx7TsXDUrXE4pvA9+x9dxxkvmM31pfBmexP3m2XiFz0zmTxMR21Mjhto6LvC0exaZvCa0SXYSVvrS4zpKeug1xlschjaF/G2cxIqqCpYiHIla2UejAcSG1hGkFUml1nRx+BZnUu6PwQ";

    /*public static String deciphering(String data, String aesKeyStr)
            throws Exception
    {
        PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);

        byte[] publicEncrypt = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), publicKey);

        String publicEncryptStr = RSAUtil.byte2Base64(publicEncrypt);

        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);

        byte[] publicEncrypt2 = RSAUtil.base642Byte(publicEncryptStr);

        byte[] aesKeyStrBytes = RSAUtil.privateDecrypt(publicEncrypt2, privateKey);

        String aesKeyStr2 = new String(aesKeyStrBytes);

        SecretKey aesKey2 = AESUtil.loadKeyAES(aesKeyStr2);

        byte[] encryptAES2 = AESUtil.base642Byte(data);

        byte[] decryptAES = AESUtil.decryptAES(encryptAES2, aesKey2);

        System.out.println("解密后的实际内容: " + new String(decryptAES, "UTF-8"));
        return new String(decryptAES, "UTF-8");
    }*/

    public static String deciphering(String data, String aesKeyStr)
            throws Exception
    {
        PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);

        //byte[] publicEncrypt = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), publicKey);

        //String publicEncryptStr = RSAUtil.byte2Base64(publicEncrypt);

        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);

        //byte[] publicEncrypt2 = RSAUtil.base642Byte(publicEncryptStr);

        byte[] aesKeyStrBytes = RSAUtil.privateDecrypt(aesKeyStr.getBytes(), privateKey);

        String aesKeyStr2 = new String(aesKeyStrBytes);

        SecretKey aesKey2 = AESUtil.loadKeyAES(aesKeyStr2);

        byte[] encryptAES2 = AESUtil.base642Byte(data);

        byte[] decryptAES = AESUtil.decryptAES(encryptAES2, aesKey2);

        System.out.println("解密后的实际内容: " + new String(decryptAES, "UTF-8"));
        return new String(decryptAES, "UTF-8");
    }

    public static CryptographVO encrypt(String data)
            throws Exception
    {
        String aesKeyStr = AESUtil.genKeyAES();

        SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);

        byte[] encryptAES = AESUtil.encryptAES(data.getBytes("UTF-8"), aesKey);

        String encryptAESStr = AESUtil.byte2Base64(encryptAES);
        CryptographVO vo = new CryptographVO();
        vo.setKey(aesKeyStr);
        vo.setData(encryptAESStr);
        return vo;
    }

    public static String decipheringIOS(String data)
            throws Exception
    {
        String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALuCf4dhtjr52vjMqCYO+pbKtdS3CK7XG4B5bSfnZte6huEXh5gXfMiVfPjLmRN8GYQOTvFrghR682Rlm6EhPkwU6ZibXIA0K03h6L2WELkPNteJfY1Klhqf0VEoF6JD6Ew2yLsuEkeva1eZnUrMHDf0axp4mfsASiHgXQ1PFUKfAgMBAAECgYA4kINcGbPZUqolx8cpw/cAKiJ0CssJw0Y+mWK+WOYDSfeKS4jR7rPr5nyOqQyCfjtMROy3k1DoULa/oI80Kk0aLifZBGIgPZi7Z+agGuXlN+51PT0tb2SxPwSRfuj3L4PqGwZDjETW+RXBMIIn+9Dr/wCLRh4HqO5WboeKFTliMQJBAOL4hfAPSkoI9Cw9CIJi10ec3FzbsppWXCbUIKtN0F71Lp9kCtym0vbZX4Qxr7XECCAfzggeJG1wAjhVxVWS360CQQDTffEg44oWC1cV9VxUyU5lvOBSfU2+wAiYj5VJ3gTa4xb5q7gUm0yN7tXKosmm5PhHiE9vWm7Qg8nfHQ2kkkT7AkALDRzAZo4wcqUo7kPWzWc8Bmg4YT4eA6xy+4snrB9EMF33xtA8lCbbfBQJhL3QdN4MamAGxyjw5y5EffCgzQj1AkEAsd6EA5MauXuEHHWk17IOe0Ykq3uy/TBoilaHyf4tuyGuWwDu6nXKJrytlaTfT7vWi+K6W/6EoF2WVE0NAlH6LQJAJL3gOJKYwcVECNW66idD26nRkyVZX2f6E6dROunYgEyopXhWvETfV4IDU8QRLZelv8e8FHG5auP3gGcSo7ZFmA==";
        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
        byte[] base642Byte = RSAUtil.base642Byte(data);
        byte[] privateDecrypt = RSAUtil.privateDecrypt3(base642Byte, privateKey);
        return new String(privateDecrypt, "UTF-8");
    }

    public static CryptographVO encryptIOS(String data)
            throws Exception
    {
        String iOSPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC77lLPK9umAITAJs5PqVLdQv9ttkF9C8wVUGfpQ1CLtRgffeoJFMF4OT57qT3+Y2ys7Y7wQjYx3lXP7THIbrYZUf6aT2sysT9NGZpGtnNktSWwK+o6jsfVOgpWydJxCocjvYpMPHabHfteMqA7DWevINpHAuCTieN33M117l3xFwIDAQAB";
        PublicKey publicKey = RSAUtil.string2PublicKey(iOSPublicKey);
        byte[] publicEncrypt = RSAUtil.publicEncrypt3(data.getBytes("UTF-8"), publicKey);
        String byte2Base64 = RSAUtil.byte2Base64(publicEncrypt);
        CryptographVO vo = new CryptographVO();
        vo.setKey("");
        vo.setData(byte2Base64);
        return vo;
    }

    public static String toDe(String data, String aesKeyStr)
            throws Exception
    {
        if (StringUtils.hasText(aesKeyStr)) {
            data = deciphering(data, aesKeyStr);
        } else {
            data = decipheringIOS(data);
        }
        return data;
    }

    public static String toEn(String data, String sign)
            throws Exception
    {
        CryptographVO mm;
        if (StringUtils.hasText(sign)) {
            mm = encryptIOS(data);
        } else {
            mm = encrypt(data);
        }
//        return VOUtils.getJsonData(mm);
        return mm.toString();
    }
}
