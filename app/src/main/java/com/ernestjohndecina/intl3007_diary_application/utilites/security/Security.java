package com.ernestjohndecina.intl3007_diary_application.utilites.security;


import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 *
 */
public class Security {



    public Security() {

    }


    public void encryptString(
            String text
    ) {

        try {
            byte[] plaintext = text.getBytes();

            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(256);
            SecretKey key = keygen.generateKey();



            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

            Cipher cipherEncrypt = Cipher.getInstance("AES");
            cipherEncrypt.init(Cipher.ENCRYPT_MODE, keySpec);


            byte[] ciphertext = cipherEncrypt.doFinal(plaintext);
            byte[] iv = cipherEncrypt.getIV();
            IvParameterSpec ivSpec = new IvParameterSpec(iv);


            String encryptedString = Arrays.toString(ciphertext);
            Log.d("TEST", "Non Encrypted String: " + text);
            Log.d("TEST", "Encrypted String: " + encryptedString);
            Log.d("TEST", "Encrypted Text: " + encryptedString);

            Cipher cipherDecrypt = Cipher.getInstance("AES");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipherDecrypt.doFinal(ciphertext);
            String decryptedString = Arrays.toString(decryptedText);

            Log.d("TEST", "Decrypted String: " + Arrays.toString(decryptedText));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                 NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException e
        ) {
            throw new RuntimeException(e);
        }
    }
}
