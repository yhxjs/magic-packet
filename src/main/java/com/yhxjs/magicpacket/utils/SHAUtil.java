package com.yhxjs.magicpacket.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class SHAUtil {

    private SHAUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getSHA(String pass) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update(pass.getBytes());
        byte[] resultByteArray = digest.digest();
        StringBuilder result = new StringBuilder();
        for (byte bite : resultByteArray) {
            result.append(String.format("%02x", bite));
        }
        return new String(result);
    }

    public static String getSalt() {
        byte[] salt = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        StringBuilder result = new StringBuilder();
        for (byte bite : salt) {
            result.append(String.format("%02x", bite));
        }
        return new String(result);
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("hexString length not valid");
        }
        int length = hexString.length() / 2;
        byte[] resultBytes = new byte[length];
        for (int index = 0; index < length; index++) {
            String result = hexString.substring(index * 2, index * 2 + 2);
            resultBytes[index] = Integer.valueOf(result, 16).byteValue();
        }
        return resultBytes;
    }

    private static String bytesToHexString(byte[] sources) {
        if (sources == null) {
            return null;
        }
        StringBuilder stringBuffer = new StringBuilder();
        for (byte source : sources) {
            String result = Integer.toHexString(source & 0xff);
            if (result.length() < 2) {
                result = "0" + result;
            }
            stringBuffer.append(result);
        }
        return stringBuffer.toString();
    }

    public static String encryptByAES(String input, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] iv = new byte[12];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        byte[] contentBytes = input.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), parameterSpec);
        byte[] encryptData = cipher.doFinal(contentBytes);
        assert encryptData.length == contentBytes.length + 16;
        byte[] message = new byte[12 + contentBytes.length + 16];
        System.arraycopy(iv, 0, message, 0, 12);
        System.arraycopy(encryptData, 0, message, 12, encryptData.length);
        return bytesToHexString(message);
    }

    public static String decryptByAES(String input, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] content = hexStringToBytes(input);
        if (content.length < 12 + 16) {
            throw new IllegalArgumentException();
        }
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, content, 0, 12);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), parameterSpec);
        byte[] decryptData = cipher.doFinal(content, 12, content.length - 12);
        return new String(decryptData, StandardCharsets.UTF_8);
    }

    public static String encrypt(String content, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        if (content == null) {
            return null;
        }
        String iv = key.substring(0, 16);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        int blockSize = cipher.getBlockSize();
        byte[] dataBytes = content.getBytes();
        int plaintextLength = dataBytes.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }
        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        byte[] encrypted = cipher.doFinal(plaintext);
        return new Base64().encodeToString(encrypted);
    }

    public static String decrypt(String content, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        String iv = key.substring(0, 16);
        byte[] encrypted = new Base64().decode(content);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original).trim();
    }
}

