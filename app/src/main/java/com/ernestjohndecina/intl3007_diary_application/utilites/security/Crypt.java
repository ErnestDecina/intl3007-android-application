package com.ernestjohndecina.intl3007_diary_application.utilites.security;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 *
 */
public class Crypt {
    SecretKeySpec secretKeySpec;
    IvParameterSpec ivParameterSpec;

    String key = "YourEncryptionKe";
    String iv = "InitializationVec";
    int KEY_SIZE = 128;




    public Crypt() {
        try {
            KeyGenerator generator  = KeyGenerator.getInstance("AES");
            generator.init(KEY_SIZE);
            secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            ivParameterSpec = new IvParameterSpec(iv.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public String encryptString(
            String message
    ) {
        byte[] messageInBytes = message.getBytes();
        try {
            Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
            return encode(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }


    public String decryptString(
            String encryptedMessage
    ) {
        byte[] messageInBytes = decode(encryptedMessage);
        try {
            Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            decryptionCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decyptedBytes = decryptionCipher.doFinal(messageInBytes);
            return new String(decyptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }


    public byte[] encryptImage(Bitmap bitmap) {
        // Convert bitmap to byteArray
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapByteArray = stream.toByteArray();

            Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return encryptionCipher.doFinal(bitmapByteArray);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

    }

    public Bitmap decryptImage(byte[] encryptedBitmap) {
        try {
            Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            decryptionCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = decryptionCipher.doFinal(encryptedBitmap);
            return BitmapFactory.decodeByteArray(decryptedBytes, 0, decryptedBytes.length);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }


    public void  encryptAudio() {

    }


    public void decryptAudio() {

    }


    private String encode(byte[] data) { return Base64.getEncoder().encodeToString(data); }

    private byte[] decode(String data) { return Base64.getDecoder().decode(data); }





}
