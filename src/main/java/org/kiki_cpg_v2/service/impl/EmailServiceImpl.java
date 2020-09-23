package org.kiki_cpg_v2.service.impl;

import javax.mail.internet.MimeMessage;

import org.kiki_cpg_v2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Value("${viewer.notification.email.from}")
	private String FROM_ADDRESS;

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public boolean sendSimpleMessage(String to, String subject, String msg) {
		boolean isEmailSend = false;
		try {
			MimeMessage message = emailSender.createMimeMessage();
			message.setSubject(subject);
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(FROM_ADDRESS);
			helper.setTo(to);
			helper.setText(msg, true);
			emailSender.send(message);
			isEmailSend=true;
		} catch (Exception exception) {
			exception.printStackTrace();
			isEmailSend=false;
		}
		return isEmailSend;
	}
}
