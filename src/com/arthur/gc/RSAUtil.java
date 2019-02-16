package com.arthur.gc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

import it.sauronsoftware.base64.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtil
{
    public static KeyPair getKeyPair()
            throws Exception
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    public static String getPublicKey(KeyPair keyPair)
    {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    public static String getPrivateKey(KeyPair keyPair)
    {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    public static PublicKey string2PublicKey(String pubStr)
            throws Exception
    {
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey string2PrivateKey(String priStr)
            throws Exception
    {
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, publicKey);

        return cipher.doFinal(content);
    }

    public static byte[] publicEncrypt2(byte[] data, String publicKey)
            throws Exception
    {
        byte[] keyBytes = Base64.decode(publicKey.getBytes());
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;int i = 0;
        while (inputLen - offSet > 0)
        {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] publicEncrypt3(byte[] content, PublicKey publicKey)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, publicKey);
        int inputLen = content.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;int i = 0;
        while (inputLen - offSet > 0)
        {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(content, offSet, 117);
            } else {
                cache = cipher.doFinal(content, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);

        return cipher.doFinal(content);
    }

    public static byte[] privateDecrypt2(byte[] encryptedData, String privateKey)
            throws Exception
    {
        byte[] keyBytes = Base64.decode(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;int i = 0;
        for (; inputLen - offSet > 0; offSet = i * 128)
        {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);i++;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();return decryptedData;
    }

    public static byte[] privateDecrypt3(byte[] content, PrivateKey privateKey)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);
        int inputLen = content.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;int i = 0;
        for (; inputLen - offSet > 0; offSet = i * 128)
        {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(content, offSet, 128);
            } else {
                cache = cipher.doFinal(content, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);i++;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static String byte2Base64(byte[] bytes)
    {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    public static byte[] base642Byte(String base64Key)
            throws IOException
    {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }

    public static void main(String[] args)
    {
        try
        {
            String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALuCf4dhtjr52vjMqCYO+pbKtdS3CK7XG4B5bSfnZte6huEXh5gXfMiVfPjLmRN8GYQOTvFrghR682Rlm6EhPkwU6ZibXIA0K03h6L2WELkPNteJfY1Klhqf0VEoF6JD6Ew2yLsuEkeva1eZnUrMHDf0axp4mfsASiHgXQ1PFUKfAgMBAAECgYA4kINcGbPZUqolx8cpw/cAKiJ0CssJw0Y+mWK+WOYDSfeKS4jR7rPr5nyOqQyCfjtMROy3k1DoULa/oI80Kk0aLifZBGIgPZi7Z+agGuXlN+51PT0tb2SxPwSRfuj3L4PqGwZDjETW+RXBMIIn+9Dr/wCLRh4HqO5WboeKFTliMQJBAOL4hfAPSkoI9Cw9CIJi10ec3FzbsppWXCbUIKtN0F71Lp9kCtym0vbZX4Qxr7XECCAfzggeJG1wAjhVxVWS360CQQDTffEg44oWC1cV9VxUyU5lvOBSfU2+wAiYj5VJ3gTa4xb5q7gUm0yN7tXKosmm5PhHiE9vWm7Qg8nfHQ2kkkT7AkALDRzAZo4wcqUo7kPWzWc8Bmg4YT4eA6xy+4snrB9EMF33xtA8lCbbfBQJhL3QdN4MamAGxyjw5y5EffCgzQj1AkEAsd6EA5MauXuEHHWk17IOe0Ykq3uy/TBoilaHyf4tuyGuWwDu6nXKJrytlaTfT7vWi+K6W/6EoF2WVE0NAlH6LQJAJL3gOJKYwcVECNW66idD26nRkyVZX2f6E6dROunYgEyopXhWvETfV4IDU8QRLZelv8e8FHG5auP3gGcSo7ZFmA==";
            String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7gn+HYbY6+dr4zKgmDvqWyrXUtwiu1xuAeW0n52bXuobhF4eYF3zIlXz4y5kTfBmEDk7xa4IUevNkZZuhIT5MFOmYm1yANCtN4ei9lhC5DzbXiX2NSpYan9FRKBeiQ+hMNsi7LhJHr2tXmZ1KzBw39GsaeJn7AEoh4F0NTxVCnwIDAQAB";
            String iOSPrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALvuUs8r26YAhMAmzk+pUt1C/222QX0LzBVQZ+lDUIu1GB996gkUwXg5PnupPf5jbKztjvBCNjHeVc/tMchuthlR/ppPazKxP00Zmka2c2S1JbAr6jqOx9U6ClbJ0nEKhyO9ikw8dpsd+14yoDsNZ68g2kcC4JOJ43fczXXuXfEXAgMBAAECgYAlHKVBwDl6CLQudftcA+YXR7nFYq6AESpWMzCyAdcQH80JuwXSkUS7hyJtrThO2jeF1svZalnlczCjBQZFvIc9Fi4/Dk90QWkh7pDwjKxV+8J5vtHeguiISSzgG375WV9aQaX15mIFLlwYPPQBot5FB0Mr2rlC7ekvcetw4298UQJBAN6lDL6oCnpaTHaqGaL4BpwyOqBMbsRj3AT5n7KQu5OoXQ4u0ODY4RO51Tc21JF0Y/xCgPXJ1qq+Aca0dP0yT+0CQQDYFesn9HBleFdSIxvVyeImjQuTOEGCVD58DfXJsguzYoWgcEKo9a4Bhv4aERaXzaIlqDV/M1jqxmidGiyt6LyTAkBwbZwf8q9H1a1jBsZnWyPLYDnmlHIizeJeyMbx3tA54f0Lmmxz/lC6K08V0KRKxyFuUw7YDxJNqiEvfBTPfsaJAkA2oxwppyIAVjRmHWB9ZyWar9E8cV4HETwXbAQ48p8IxaGaFqiURkKwOizn9GgtDB6yvNyCBD64ZmCvUJ35fz6nAkAXlUOYMbA17MiQXQVJWZN1o55VKr1iEOPOmxtgO6Iy+sLKqbgWSAJ9AOTx2c0rvcr2PgzVm/41TQa6OvWCRbg8";
            String iOSPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC77lLPK9umAITAJs5PqVLdQv9ttkF9C8wVUGfpQ1CLtRgffeoJFMF4OT57qT3+Y2ys7Y7wQjYx3lXP7THIbrYZUf6aT2sysT9NGZpGtnNktSWwK+o6jsfVOgpWydJxCocjvYpMPHabHfteMqA7DWevINpHAuCTieN33M117l3xFwIDAQAB";
            String message = "{\"currenttoken\":\"3972b4d9db6e411a8aedf743a8f6bc70\",\"firstlogin\":\"1\",\"gender\":\"\",\"jgdm\":\"Y0161802500     \",\"jgmc\":\"��������������������������                                  \",\"logintine\":\"2018-06-20 17:21:00\",\"name\":\"admin1\",\"userid\":\"800651\"}";
            String byte2Base64 = "McNj2PBsAK0/jliPijG2xRKJAxSTOu8Fl2+a3Jk3DFV5oEuibZ3ZJLO0I7446x6cFk0u+xAQ59+R1sNrUtN/aqf5xWcZynUMqA5Hwbu2pMOnFXBJFAjIMG35IJRkrpf3OB1qfTgkigWPQo0CdeWh45q5ttPnbTtnKbDiDsRbDD1/1MOlqXm1oDUlnp79iYX0gcAfSpvRMvHKCbUdVJblEIoSqDkAEXuIBwSI/wW7y5k3LdGPs+9Jjkxm7rt1fqJAWQxo0AyuXGuoytotgE4TkYOriFcOTqLrm/MqfxG23ak32fXMz8Wniv4OfbBVZoizEOScE9mkDMX2uNkOEZ/S5Tp27qHdHXmxFa2h9XzP6BJG6KJkgm8M3IpSXfPKYfTvBZ31E2Pa3rO/AT8nG9MrGcFzjtMtquEWZ2KZlN1/0krO21Bo0vwnVqf3GATqJknQkRU6pzR4Ab3bZRil3jUwiJXfcucBp1f9CAqTuXXwznBDleo3xI9oSohJKm+jvk5s";

            PublicKey publicKey = string2PublicKey(publicKeyStr);
            byte[] publicEncrypt = publicEncrypt3(message.getBytes(), publicKey);
            byte2Base64 = byte2Base64(publicEncrypt);
            System.out.println("����������Base64������������" + byte2Base64);

            byte2Base64 = "McNj2PBsAK0/jliPijG2xRKJAxSTOu8Fl2+a3Jk3DFV5oEuibZ3ZJLO0I7446x6cFk0u+xAQ59+R1sNrUtN/aqf5xWcZynUMqA5Hwbu2pMOnFXBJFAjIMG35IJRkrpf3OB1qfTgkigWPQo0CdeWh45q5ttPnbTtnKbDiDsRbDD1/1MOlqXm1oDUlnp79iYX0gcAfSpvRMvHKCbUdVJblEIoSqDkAEXuIBwSI/wW7y5k3LdGPs+9Jjkxm7rt1fqJAWQxo0AyuXGuoytotgE4TkYOriFcOTqLrm/MqfxG23ak32fXMz8Wniv4OfbBVZoizEOScE9mkDMX2uNkOEZ/S5Tp27qHdHXmxFa2h9XzP6BJG6KJkgm8M3IpSXfPKYfTvBZ31E2Pa3rO/AT8nG9MrGcFzjtMtquEWZ2KZlN1/0krO21Bo0vwnVqf3GATqJknQkRU6pzR4Ab3bZRil3jUwiJXfcucBp1f9CAqTuXXwznBDleo3xI9oSohJKm+jvk5s";

            PrivateKey privateKey = string2PrivateKey(iOSPrivateKey);
            byte[] base642Byte = base642Byte(byte2Base64);
            byte[] privateDecrypt = privateDecrypt3(base642Byte, privateKey);
            System.out.println("������������: " + new String(privateDecrypt, "UTF-8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
