package com.itwillbs.pass;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptionUtil {
	 private static final String ALGORITHM = "AES";

	    public static String encrypt(String data, SecretKey key) throws Exception {
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte[] encryptedData = cipher.doFinal(data.getBytes());
	        return Base64.getEncoder().encodeToString(encryptedData);
	    }

	    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
	        return new String(decryptedData);
	    }

	    public static SecretKey generateKey() throws Exception {
	        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
	        keyGen.init(128); // 128 비트 키
	        return keyGen.generateKey();
	    }
	}