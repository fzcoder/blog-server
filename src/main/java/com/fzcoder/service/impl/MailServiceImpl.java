package com.fzcoder.service.impl;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fzcoder.service.MailService;

@Service
public class MailServiceImpl implements MailService{
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Autowired
	private JavaMailSender mailSender;
	
	// 发送简单邮件
	@Override
	public void sendSimpleMall(String to, String subject, String content) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			
			message.setFrom(from);
			message.setTo(to);
			message.setCc(from);
			message.setSubject(subject);
			message.setText(content);
			
			mailSender.send(message);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	// 发送带附件的邮件
	@Override
	public void sendAttachFileMail(String to, String subject, String content, File file) {		
        try {
        	MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            messageHelper.setText(content);
            //添加附件
            messageHelper.addAttachment(file.getName(), file);
            //发送
            mailSender.send(message);
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
	// 发送HTML格式文件
	@Override
	public void sendHtmlMail(String to, String subject, String content) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
}
