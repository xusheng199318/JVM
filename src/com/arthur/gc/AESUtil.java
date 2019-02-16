package com.arthur.gc;

import java.io.IOException;
import java.io.PrintStream;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil
{
    public static String genKeyAES()
            throws Exception
    {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey key = keyGen.generateKey();
        String base64Str = byte2Base64(key.getEncoded());
        return base64Str;
    }

    public static SecretKey loadKeyAES(String base64Key)
            throws Exception
    {
        byte[] bytes = base642Byte(base64Key);
        SecretKeySpec key = new SecretKeySpec(bytes, "AES");
        return key;
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

    public static byte[] encryptAES(byte[] source, SecretKey key)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, key);
        return cipher.doFinal(source);
    }

    public static byte[] decryptAES(byte[] source, SecretKey key)
            throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, key);
        return cipher.doFinal(source);
    }

    public static void main(String[] args)
    {
        try
        {
            String message = "hello, i am infi, good night!";

            String base64Str = genKeyAES();
            System.out.println("AES����Base64����:" + base64Str);

            SecretKey aesKey = loadKeyAES(base64Str);

            byte[] encryptAES = encryptAES(message.getBytes(), aesKey);

            String byte2Base64 = byte2Base64(encryptAES);
            System.out.println("������Base64������������" + byte2Base64);

            SecretKey aesKey2 = loadKeyAES(base64Str);

            byte[] base642Byte = base642Byte(byte2Base64);

            byte[] decryptAES = decryptAES(base642Byte, aesKey2);

            System.out.println("������������: " + new String(decryptAES));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
