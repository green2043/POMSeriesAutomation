package com.qa.appName.factory;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncrypt {

	
		
		private static final String SECRET_KEY = "FWautomtion12345";

		public static String encrypt(String stringToEncrypt) {

			try {
				SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
				return Base64.getEncoder().encodeToString(cipher.doFinal(stringToEncrypt.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static String decrypt(String stringToDecrypt) {
			
			try {
				SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
				return new String(cipher.doFinal(Base64.getDecoder().decode(stringToDecrypt)));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		
		public static void main(String[] args) {
			String encryptedPwd= encrypt("Selenium@12345");
			System.out.println(encryptedPwd);
		}

}
