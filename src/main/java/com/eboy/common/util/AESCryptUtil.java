package com.eboy.common.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * aes加密解密工具类
 * @author ExceptionalBoy
 *
 */
public class AESCryptUtil {

	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

	/**
	 * 
	 * @Title: encrypt
	 * @Description: TODO(加密算法)
	 * @param @param data
	 * @param @param key
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String encrypt(String data, String key) {

		if(data != null && !"".equals(data) && key != null && !"".equals(key)){
			try {
				byte[] bData = data.getBytes();
				byte[] bKey = key.getBytes();
				//创建key
				Key cryptKey = new SecretKeySpec(bKey,KEY_ALGORITHM);
				//实例化
				Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
				//使用密钥初始化，设置为加密模式
				cipher.init(Cipher.ENCRYPT_MODE, cryptKey);
				//加密
				// 执行加密操作
				byte[] encryptedData =  cipher.doFinal(bData);
				//将加密结果由二进制转为十六进制
				String result = parseByte2HexStr(encryptedData);
				//返回加密结果
				return	result;
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: decrypt
	 * @Description: TODO(解密方法)
	 * @param @param data
	 * @param @param key
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String decrypt(String data, String key){

		if(data != null && !"".equals(data) && key != null && !"".equals(key)){
			try {
				//将需要解密的十六进制数据转为二进制并转为字节数组
				byte[] bData = parseHexStr2Byte(data);
				byte[] bKey = key.getBytes();
				//创建key
				Key cryptKey = new SecretKeySpec(bKey,KEY_ALGORITHM);
				//实例化
				Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
				//使用密钥初始化，设置为解密模式
				cipher.init(Cipher.DECRYPT_MODE, cryptKey);
				// 开始解密操作
				byte[] decryptedData = cipher.doFinal(bData);
				//返回结果
				return new String(decryptedData);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return null;

	}


	/**
	 * 二进制转十六进制
	 * @param data
	 * @return
	 */
	public static String parseByte2HexStr(byte[] data) {
		if(data != null && data.length>1){
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < data.length; i++) {
				String hex = Integer.toHexString(data[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				sb.append(hex.toUpperCase());
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * 十六进制转二进制
	 *
	 * @param data
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String data) {
		if(data != null && !"".equals(data)){
			byte[] result = new byte[data.length() / 2];
			for (int i = 0; i < data.length() / 2; i++) {
				int high = Integer.parseInt(data.substring(i * 2, i * 2 + 1), 16);
				int low = Integer.parseInt(data.substring(i * 2 + 1, i * 2 + 2), 16);
				result[i] = (byte) (high * 16 + low);
			}
			return result;
		}
		return null;
	}
}
