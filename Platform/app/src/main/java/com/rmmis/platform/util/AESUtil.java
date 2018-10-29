package com.rmmis.platform.util;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密方法
 * 
 * @author Administrator
 * 
 */
public class AESUtil {
	/**
	 * 加密方法
	 * 
	 * @param sSrc
	 *            要加密的数据
	 * @param sKey
	 *            加密的key（16位）
	 * @return
	 */
	public static String Encrypt(String sSrc, String sKey) {
		try {
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = null;//"算法/模式/补码方式"
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
			try {
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			byte[] encrypted = new byte[0];
			try {
				encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
			return Base64.encodeToString(encrypted, Base64.DEFAULT);
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	//
	/**
	 * 解密方法
	 * 
	 * @param sSrc
	 *            要解密的数据
	 * @param sKey
	 *            解密的Key（16位）
	 * @return
	 */
	public static String Decrypt(String sSrc, String sKey) {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708"
					.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			 byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original,"UTF-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	}

