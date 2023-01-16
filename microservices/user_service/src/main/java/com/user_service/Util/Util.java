package com.user_service.Util;

import java.security.SecureRandom;
import java.util.Random;

public class Util {
	public static boolean checkEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public static String generateRandomString(int length) {
		Random RANDOM = new SecureRandom();
		String numbers = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwnyz";
		String string = "";

		for (int i = 0; i < length; i++) {
			int index = (int) (RANDOM.nextDouble() * numbers.length());
			string += numbers.substring(index, index + 1);
		}

		return string;
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}

	public static byte[] computeHash(String x) throws Exception {
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return d.digest();
	}
}
