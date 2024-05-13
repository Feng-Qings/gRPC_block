package com.block.utils;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.util.UUID;


/**
 * 密码学工具类
 *
 *
 */
public class CryptoUtil {

	/**
	 * SHA256散列函数
	 * @param str
	 * @return
	 */
	public static String SHA256(String str) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			encodeStr = byte2Hex(messageDigest.digest());
		} catch (Exception e) {
			System.out.println("getSHA256 is error" + e.getMessage());
		}
		return encodeStr;
	}
	
	private static String byte2Hex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                builder.append("0");
            }
            builder.append(temp);
        }
		return builder.toString();
	}

	public static String MD5(String str) {
		String resultStr = DigestUtils.md5DigestAsHex(str.getBytes());
		return resultStr.substring(4, resultStr.length());
	}

	public static String UUID() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}

}
