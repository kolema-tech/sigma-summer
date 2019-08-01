package com.sigma.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/7/18-13:56
 * desc:
 **/
@Slf4j
public class EmailSender {

    @Resource
    Configuration configuration;

    @Resource
    private JavaMailSender mailSender;

    @Async
    public void sendAsync(String sender, String receiver, String subject, String templatePath, Map<String, Object> params) {
        try {
            Template t = configuration.getTemplate(templatePath);
            String body = FreeMarkerTemplateUtils.processTemplateIntoString(t, params);

            send(sender, receiver, subject, body);
        } catch (IOException | TemplateException | MessagingException e) {
            e.printStackTrace();
        }
    }

    public void send(String sender, String receiver, String subject, String body) throws MessagingException {

        MimeMessage message = null;

        message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(receiver);
        helper.setSubject(subject);

        helper.setText(body, true);

        mailSender.send(message);
    }
}
