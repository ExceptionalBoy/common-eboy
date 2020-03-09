package com.eboy.common.mail;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 * @ClassName: Mail 
 * @Description: 封装邮件信息的实体类TODO 
 * @author ExceptionalBoy 
 * @date 2017年8月14日 上午10:25:12 
 *
 */
@Data
@Component
public class Mail {

	/**
	 * 邮箱服务器
	 */
	@Value("%{mail.host}")
	private String emailHost;
	/**
	 * 发件人邮箱
	 */
	@Value("%{mail.from}")
	private String emailFrom;

	/**
	 * 发件人用户名
	 */
	@Value("%{mail.username}")
	private String emailUserName;

	/**
	 * 发件人密码
	 */
	@Value("%{mail.password}")
	private String emailPassword;

	/**
	 * 收件人邮箱，多个邮箱以“;”分隔
	 */
	private String toEmails;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件内容
	 */
	private String content;
	/**
	 * 邮件中的图片，为空时无图片。map中的key为图片ID，value为图片地址
	 */
	private Map<String, String> pictures;
	/**
	 * 邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址
	 */
	private Map<String, String> attachments;

	public Mail() {
	}

	public Mail(String toEmails, String subject, String content) {
		this.toEmails = toEmails;
		this.subject = subject;
		this.content = content;
	}

	public Mail(String toEmails, String subject, String content, Map<String, String> pictures, Map<String, String> attachments) {
		this.toEmails = toEmails;
		this.subject = subject;
		this.content = content;
		this.pictures = pictures;
		this.attachments = attachments;
	}
}

