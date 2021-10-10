package com.fzcoder.opensource.blog.service;

import java.io.File;

/**
 * 邮件服务的接口类
 * 
 * @author Frank Fang
 *
 */
public interface MailService {

	/**
	 * 发送简单邮件
	 * 
	 * @param to      收件人
	 * @param cc      抄送人
	 * @param subject 主题
	 * @param content 内容
	 */
	void sendSimpleMall(String to, String subject, String content);

	/**
	 * 发送带附件的邮件
	 * 
	 * @param to      收件人
	 * @param subject 主题
	 * @param content 内容
	 * @param file    文件
	 */
	void sendAttachFileMail(String to, String subject, String content, File file);

	/**
	 * 发送HTML格式的邮件
	 * 
	 * @param to      收件人
	 * @param subject 主题
	 * @param content 内容
	 */
	void sendHtmlMail(String to, String subject, String content);
}
