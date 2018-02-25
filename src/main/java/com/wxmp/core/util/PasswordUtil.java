package com.wxmp.core.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public class PasswordUtil {
	public static String encryptPassword(String strPasswd) {
		if (strPasswd == null || strPasswd.equals("")) {
			return "";
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return Base64.encodeBase64String(md.digest(strPasswd.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(encryptPassword("jeeweixin"));//5RpuDkfdbTs1ctwfT6MurA==
	}
}
