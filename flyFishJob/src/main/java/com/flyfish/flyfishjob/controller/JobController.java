package com.flyfish.flyfishjob.controller;

import com.alibaba.fastjson.JSONObject;
import com.flyfish.flyfishjob.model.Dto.MailDto;
import com.flyfish.flyfishjob.utils.EmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: yumingjun
 * @Date: 2022/1/13 11:03
 * @since: 1.0.0
 */
@Controller
@RequestMapping("/flyFishjob")
@Api(tags = "job")
public class JobController {

    @Autowired
    private EmailUtil emailUtil;

    @ApiOperation("发送邮件")
    @RequestMapping(value = "/sendEmail",method = RequestMethod.POST)
    @ResponseBody
    public boolean sendEmail(@RequestBody MailDto mailDto){
//        emailUtil.sendSimpleMail(mailDto);
        emailUtil.sendHtmlMail(mailDto);
        return true;
    }

    @ApiOperation("发送附件邮件")
    @RequestMapping(value = "/sendFjEmail",method = RequestMethod.POST)
    @ResponseBody
    public boolean sendEmail(@RequestParam("files") MultipartFile[] files){
        MailDto mailDto = new MailDto();
        mailDto.setToEmails("2538128459@qq.com".split(","));
        mailDto.setContent("<a>附件邮件</a>");
        emailUtil.sendAttachmentMail(mailDto, files);
        return true;
    }

}
