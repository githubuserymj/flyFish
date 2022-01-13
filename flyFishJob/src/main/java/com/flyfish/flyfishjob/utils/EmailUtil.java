package com.flyfish.flyfishjob.utils;

import com.flyfish.flyfishjob.jobs.EmailJobs;
import com.flyfish.flyfishjob.model.Dto.MailDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @Author: yumingjun
 * @Date: 2022/1/12 15:32
 * @since: 1.0.0
 * 邮件发送服务
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
            LOGGER.error(">>>>>>发送邮件发生错误：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 发送复杂邮件(支持html，带附件)
     * @param mailDto
     * @return
     */
    private boolean sendComplexMail(MailDto mailDto, MultipartFile[] files){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            /*true代表支持多组件发送*/
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            if (StringUtils.isNotBlank(mailDto.getSubject())){
                mimeMessageHelper.setSubject(mailDto.getSubject());
            }
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(mailDto.getToEmails());
            if (null != mailDto.getBccEmails() && mailDto.getBccEmails().length > 0){
                mimeMessageHelper.setBcc(mailDto.getBccEmails());
            }
            if (null != mailDto.getCcEmails() && mailDto.getCcEmails().length > 0){
                mimeMessageHelper.setCc(mailDto.getCcEmails());
            }
            if (StringUtils.isNotBlank(mailDto.getReplyTo())){
                mimeMessageHelper.setReplyTo(mailDto.getReplyTo());
            }
            /*设置以html形式发送*/
            mimeMessageHelper.setText(mailDto.getContent(), true);
//            mimeMessageHelper.setSentDate(new Date());
            if (null != files){
                for (MultipartFile file : files) {
                    mimeMessageHelper.addAttachment(file.getOriginalFilename(), file);
                }
            }
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error(">>>>>>发送邮件发生错误：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 发送网页邮件
     * @param mailDto
     * @return
     */
    public boolean sendHtmlMail(MailDto mailDto) {
        return this.sendComplexMail(mailDto, null);
    }

    /**
     * 发送带附件的邮件
     * @param mailDto
     * @param files
     * @return
     */
    public boolean sendAttachmentMail(MailDto mailDto, MultipartFile[] files){
        return this.sendComplexMail(mailDto, files);
    }

}
