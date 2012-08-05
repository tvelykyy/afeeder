package com.tvelykyy.afeeder.domain.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.crypto.codec.Hex;

import com.tvelykyy.afeeder.domain.SecurityUser;
import com.tvelykyy.afeeder.domain.User;

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
	
	public static SecurityUser userToSecurityUser(User user, List<GrantedAuthorityImpl> authorities) {
		return new SecurityUser(user.getLogin(), user.getPassword(), true, true, true, true,
				authorities, user.getName(), user.getId());
	}
}
