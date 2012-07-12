package com.tvelykyy.afeeder.domain.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Hex;

public class UserUtils {
	public static String hashPasswordMD5(String password) {
		byte[] bytesOfPassword = null;
		try {
			bytesOfPassword = password.getBytes("UTF-8");
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
				byte[] thedigest = md.digest(bytesOfPassword);
				password = new String(Hex.encode(thedigest));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return password;
	}
}
