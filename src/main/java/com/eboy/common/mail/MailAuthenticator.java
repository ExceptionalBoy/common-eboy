package com.eboy.common.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * @ClassName: MyMailAuthenticator 
 * @Description: 自定义邮箱用户密码验证器TODO 
 * @author ExceptionalBoy 
 * @date 2017年8月14日 上午10:46:34 
 *
 */
public class MailAuthenticator extends Authenticator{ 
	//两个属性
	private String username;  
	private String password;  
	
	//带参数的构造方法 
	public MailAuthenticator(String username, String password) {  
		super();  
		this.username = username;  
		this.password = password;  
	}  
	
	protected PasswordAuthentication getPasswordAuthentication() { 
		return new PasswordAuthentication(username, password);  
	}  
}
