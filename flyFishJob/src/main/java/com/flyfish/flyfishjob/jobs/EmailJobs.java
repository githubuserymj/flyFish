package com.flyfish.flyfishjob.jobs;

import com.alibaba.fastjson.JSONObject;
import com.flyfish.flyfishjob.model.Dto.MailDto;
import com.flyfish.flyfishjob.utils.EmailUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @Author: yumingjun
 * @Date: 2022/1/12 11:10
 * @since: 1.0.0
 */
@Component
public class EmailJobs {

    private static Logger LOGGER = LoggerFactory.getLogger(EmailJobs.class);

    @Autowired
    private EmailUtil emailUtil;

    @XxlJob("dailyGreetJob")
    public void dailyGreetJob(){
        String jobParamStr = XxlJobHelper.getJobParam();
        JSONObject jobParam = JSONObject.parseObject(jobParamStr);
        String toEmails = jobParam.getString("toEmails");
        if (StringUtils.isBlank(toEmails)){
            LOGGER.info(">>>邮件接受者未配置<<<");
            XxlJobHelper.log(">>>邮件接受者未配置<<<");
            return;
        }
        String subject = jobParam.getString("subject");
        String content = jobParam.getString("content");
        String bccEmails = jobParam.getString("bccEmails");
        String ccEmails = jobParam.getString("ccEmails");
        String replyTo = jobParam.getString("replyTo");

        MailDto mailDto = new MailDto();
        mailDto.setSubject(subject);
        mailDto.setToEmails(StringUtils.isNotBlank(toEmails) ? toEmails.split(",") : null);
        mailDto.setContent(content);
        mailDto.setBccEmails(StringUtils.isNotBlank(bccEmails) ? bccEmails.split(",") : null);
        mailDto.setCcEmails(StringUtils.isNotBlank(ccEmails) ? ccEmails.split(",") : null);
        mailDto.setReplyTo(replyTo);
        if (emailUtil.sendSimpleMail(mailDto)){
            LOGGER.info(">>>邮件：{}发送成功<<<", mailDto);
            XxlJobHelper.log(">>>邮件：{}发送成功<<<", mailDto);
            return;
        }
        LOGGER.info(">>>邮件：{}发送失败<<<", mailDto);
        XxlJobHelper.log(">>>邮件：{}发送失败<<<", mailDto);
    }
}
