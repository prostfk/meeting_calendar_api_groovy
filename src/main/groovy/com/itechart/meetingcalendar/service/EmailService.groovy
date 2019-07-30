package com.itechart.meetingcalendar.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

import javax.mail.Message
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.regex.Pattern

import static com.itechart.meetingcalendar.service.FileService.getTemplate

@Service
class EmailService {

    @Autowired
    private JavaMailSender sender

    void sendTemplate(String to, String subject, EmailTemplate template, Map<String, String> params) {
        def templateStr = getTemplate(template)
        params.keySet().each {
            templateStr.replaceAll(Pattern.quote(it), params.get(it))
        }
        sendEmail(to, subject, templateStr)
    }

    void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = sender.createMimeMessage()
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to, false))
            message.setSubject(subject)
            message.setContent(body, "text/html; charset=utf-8")
            sender.send(message)
        } catch (Exception e) {
            //empty
        }
    }

}
