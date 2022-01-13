package com.flyfish.flyfishjob.utils;

import com.flyfish.flyfishjob.jobs.EmailJobs;
import com.flyfish.flyfishjob.model.Dto.MailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: yumingjun
 * @Date: 2022/1/12 15:32
 * @since: 1.0.0
 */
@Component
public class EmailUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 发送简单的文本邮件
     * @param mailDto
     * @return
     */
    public boolean sendSimpleMail(MailDto mailDto){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(mailDto.getSubject());
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(mailDto.getToEmails());
            simpleMailMessage.setBcc(mailDto.getBccEmails());
            simpleMailMessage.setCc(mailDto.getCcEmails());
            simpleMailMessage.setReplyTo(mailDto.getReplyTo());
            simpleMailMessage.setText(mailDto.getContent());
//            simpleMailMessage.setSentDate(new Date());
            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            LOGGER.error(">>>>>>邮件发送发生错误：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 发送网页邮件
     * @param dto
     * @return
     */
    public boolean sendHtmlMail(MailDto dto) {
        return true;
    }

    /**
     * 发送带附件的邮件
     * @param dto
     * @param files
     * @return
     */
    public boolean sendAttachmentMail(MailDto dto, MultipartFile[] files){
        return true;
    }

}
