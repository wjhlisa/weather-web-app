package com.tq;

import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.tq.db.WeatherDataTimer;

/*
 * A bean can not be autowired to a static field. A workaround is:
 * 1 Make the class @Component to initialize it as a bean by the context container
 * 2 Autowired its constructor
 * https://stackoverflow.com/questions/17659875/autowired-and-static-method/17660550#17660550
 */
@Component
public class TqUtil
{
	private static JavaMailSender mailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(WeatherDataTimer.class);
	
	@Autowired
	public TqUtil(JavaMailSender mailSender)
	{
		TqUtil.mailSender = mailSender;
	}
	
	public static void SendMail(String subject, String text)
	{
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("xxx@xxx.com");
		msg.setTo("xxx@xxx.com");
		msg.setSubject(subject);
		msg.setText(text);
		
		try
		{
			mailSender.send(msg);
			logger.info(MessageFormat.format("EMail sent: {0}", subject));
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
	}

}
