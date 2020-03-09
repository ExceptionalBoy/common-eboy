package com.eboy.common.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * 
 * @ClassName: Base64Util
 * @Description: TODO(Base64转码工具类,只支持jdk1.8以后的版本)
 * @author ExceptionalBoy
 * @date 2019年1月17日
 *
 */
public class Base64Util {
	
	/**
	 * JDK8以后版本提供的Base64解码工具类
	 */
	private static final Decoder DECODER = Base64.getDecoder();
	/**
	 * JDK8以后版本提供的Base64编码工具类
	 */
	private static final Encoder ENCODER = Base64.getEncoder();
	
	/**
	 * 
	 * @Title: encode
	 * @Description: TODO(将字符串编码为Base64编码格式的字符串)
	 * @param @param source
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	public static String encode(String source){
		byte[] eBytes = source.getBytes();
		byte[] dBytes = ENCODER.encode(eBytes);
 		return new String(dBytes);
	}
	/**
	 * 
	 * @Title: decode
	 * @Description: TODO(将Base64编码格式的字符串进行解码处理)
	 * @param @param source
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	public static String decode(String source){
		byte[] dBytes = source.getBytes();
		byte[] eBytes = DECODER.decode(dBytes);
		return new String(eBytes);
	}
}
