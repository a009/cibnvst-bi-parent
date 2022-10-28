package com.vst.common.tools.encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.vst.common.tools.string.ToolsString;

/**
 * @author wei.wei(joslyn)
 * @date 2014-11-13 下午11:56:57
 * @description 加密工具类
 */
public class ToolsEncrypt {

	/**
	 * SHA256加密
	 * @param str
	 * @return
	 */
	public static String getSHA256Encrypt(String str) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = str.getBytes();
        try { 
            md = MessageDigest.getInstance("SHA-256");
            md.update(bt);
            strDes = bytesToHex(md.digest());
        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }
	
	/**
	 * MD5加密
	 * @param str
	 * @return
	 */
	public static String getMD5Encrypt(String str) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = str.getBytes();
        try { 
            md = MessageDigest.getInstance("MD5");
            md.update(bt);
            strDes = bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }
	
	/**
	 * 二进制转十六进制
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
        	temp = (Integer.toHexString(bytes[i] & 0xFF));
            if (temp.length() == 1) sb.append("0");
            sb.append(temp);
        }
        return sb.toString();
    }
	
	/**
	 * 十六进制字符串转byte数组
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (ToolsString.isEmpty(hexString)) {
			return new byte[0];
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	/**
	 * 加密字符串（base64加密）
	 * @param str
	 * @return
	 */
	public static String encryptUUID(String str) {
		if(ToolsString.isEmpty(str)) return "";
		String prefix = bytesToHex(Base64.encode(str).getBytes()).toUpperCase();
		String suffix = "_zjjtv";
		// 生成md5加密后缀
		String encrypt = getMD5Encrypt(prefix.concat(suffix)).toUpperCase();
		return prefix.concat(encrypt.substring(encrypt.length() - 6));
	}
	
	/**
	 * 解密字符串
	 * @param str
	 * @return
	 */
	public static String decodeUUID(String str) {
		if (ToolsString.isEmpty(str) || str.length() <= 10) {
			return null;
		}

		// 只匹配大小写字母和数字
		Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher m = p.matcher(str);

		if (!m.matches()) {
			return null;
		}

		String matchSuffix = str.substring(str.length() - 6);
		String suffix = "_zjjtv";

		// 生成md5加密后缀
		String prefix = str.substring(0, str.length() - 6);
		// 生成正确的加密url
		String encrypt = getMD5Encrypt(prefix.concat(suffix)).toUpperCase();
		
		// 获取正确的后缀
		String realSuffix = encrypt.substring(encrypt.length() - 6);

		if (realSuffix.equals(matchSuffix)) {
			return Base64.decode(new String(hexStringToBytes(prefix))).trim();
		}
		
		return null;
	}
	
	/**
	 * 加密cookie
	 * @param cookie
	 * @return
	 */
	public static String encryptCookie(String cookie){
		try {
			SecretKeySpec keySpec = new SecretKeySpec("f909aab907d91beb".getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			// 加密数组
			int length = cookie.getBytes().length % 16;
			// 不够的，补空格字符串
			if(length > 0){
				for(int i = 0; i < 16 - length; i++){
					cookie += " ";
				}
			}
			// 加密数组
			byte[] encrypt = cipher.doFinal(cookie.getBytes());
			// 转十六进制
			String hexString = bytesToHex(encrypt);
			return hexString + getMD5Encrypt("vstlogin_" + hexString + "-cookie_2015");
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 解密cookie
	 * @param cookies
	 * @return
	 */
	public static String decodeCookie(String cookie){
		try {
			if(!ToolsString.isEmpty(cookie) && cookie.length() > 45){
				String password = cookie.substring(cookie.length() - 32);
				String text = cookie.substring(0, cookie.length() - 32);
				
				String md5 = getMD5Encrypt("vstlogin_" + text +"-cookie_2015");
				if(password.equals(md5)){
					SecretKeySpec keySpec = new SecretKeySpec("f909aab907d91beb".getBytes(), "AES");
					Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
					cipher.init(Cipher.DECRYPT_MODE, keySpec);
					byte[] decrypt = cipher.doFinal(hexStringToBytes(text));
					return new String(decrypt).trim();
				}
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 解密url中的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeParam(String str) {
		String result = "";

		if (!ToolsString.isEmpty(str)) {
			try {
				result = URLDecoder.decode(str.trim().replaceAll("_", "%"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}

		return result;
	}
	
	/**
	 * 加密url中的中文字符串
	 * @param str
	 * @return
	 */
	public static String encryptParam(String str){
		String result = "";

		if (!ToolsString.isEmpty(str)) {
			try {
				result = URLEncoder.encode(str.trim(), "UTF-8").replaceAll("%", "_");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return result;
	}
}
