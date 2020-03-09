package com.eboy.common.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.eboy.common.exception.BusinessException;
import com.eboy.common.enums.SystemCodeEnum;
import com.eboy.common.util.StringUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: MailSander 
 * @Description: 邮件发送器TODO 
 * @author ExceptionalBoy 
 * @date 2017年8月14日 上午11:00:06 
 *
 */
@Component
public class MailSender {
	
	/**
	 * 
	 * @Title: sendEmail 
	 * @Description: 发送邮件TODO
	 * @author ExceptionalBoy 
	 * @param @param mail
	 * @param @throws exception
	 * @return void   
	 * @throws
	 * @date 2017年8月14日 上午11:01:40
	 */
	public void sendEmail(Mail mail) throws Exception {

		if (mail.getEmailHost().equals("") || mail.getEmailFrom().equals("")
				/*|| mail.getEmailUserName().equals("")*/
				|| mail.getEmailPassword().equals("")) {
			throw new BusinessException(SystemCodeEnum.MAIL_ERROR.getCode(),"发件人信息不完全,请确认发件人信息");
		}
		//这个就是我刚才说的
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost(mail.getEmailHost());

		// 建立邮件消息
		MimeMessage mailMessage = senderImpl.createMimeMessage();

		MimeMessageHelper messageHelper = null;
		messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
		// 设置发件人邮箱
		messageHelper.setFrom(mail.getEmailFrom());

		// 设置收件人邮箱
		String[] toEmailArray = mail.getToEmails().split(";");
		List<String> toEmailList = new ArrayList<String>();
		if (null == toEmailArray || toEmailArray.length <= 0) {
			throw new BusinessException(SystemCodeEnum.MAIL_ERROR.getCode(),"收件人邮箱不得为空");
		} else {
			for (String s : toEmailArray) {
				s = StringUtil.checkString(s);
				if (!s.equals("")) {
					toEmailList.add(s);
				}
			}
			if (null == toEmailList || toEmailList.size() <= 0) {
				throw new BusinessException(SystemCodeEnum.MAIL_ERROR.getCode(),"收件人邮箱不得为空");
			} else {
				toEmailArray = new String[toEmailList.size()];
				for (int i = 0; i < toEmailList.size(); i++) {
					toEmailArray[i] = toEmailList.get(i);
				}
			}
		}
		messageHelper.setTo(toEmailArray);

		// 邮件主题
		messageHelper.setSubject(mail.getSubject());

		// true 表示启动HTML格式的邮件
		messageHelper.setText(mail.getContent(), true);

		// 添加图片
		if (null != mail.getPictures()) {
			for (Iterator<Map.Entry<String, String>> it = mail.getPictures().entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				String cid = entry.getKey();
				String filePath = entry.getValue();
				if (null == cid || null == filePath) {
					throw new BusinessException(SystemCodeEnum.MAIL_ERROR.getCode(),"请确认每张图片的ID和图片地址是否齐全");
				}

				File file = new File(filePath);
				if (!file.exists()) {
					throw new BusinessException(SystemCodeEnum.MAIL_ERROR.getCode(),"图片" + filePath + "不存在");
				}

				FileSystemResource img = new FileSystemResource(file);
				messageHelper.addInline(cid, img);
			}
		}

		// 添加附件
		if (null != mail.getAttachments()) {
			for (Iterator<Map.Entry<String, String>> it = mail.getAttachments()
					.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				String cid = entry.getKey();
				String filePath = entry.getValue();
				if (null == cid || null == filePath) {
					throw new BusinessException(SystemCodeEnum.MAIL_ERROR.getCode(),"请确认每个附件的ID和地址是否齐全");
				}

				File file = new File(filePath);
				if (!file.exists()) {
					throw new BusinessException(SystemCodeEnum.MAIL_ERROR.getCode(),"附件" + filePath + "不存在");
				}

				FileSystemResource fileResource = new FileSystemResource(file);
				messageHelper.addAttachment(cid, fileResource);
			}
		}

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		// 添加验证
		MailAuthenticator auth = new MailAuthenticator(mail.getEmailUserName(), mail.getEmailPassword());

		Session session = Session.getDefaultInstance(prop, auth);
		senderImpl.setSession(session);

		// 发送邮件
		senderImpl.send(mailMessage);
	}
}
