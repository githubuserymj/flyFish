package com.flyfish.flyfishjob.model.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.File;

/**
 * @Author: yumingjun
 * @Date: 2022/1/12 15:38
 * @since: 1.0.0
 */
@Data
@ApiModel("邮件传输对象")
public class MailDto {

    @ApiModelProperty(value = "发件人id", required = true)
    @NotEmpty(message = "发件人id不能为空")
    private String fromId;
    @ApiModelProperty(value = "收件人id,多个用逗号分隔", required = true)
    @NotEmpty(message = "收件人id不能为空")
    private String[] toIds;

    @ApiModelProperty(value = "收件人邮箱,多个用逗号分隔", required = true)
    @NotEmpty(message = "收件人邮箱不能为空")
    private String[] toEmails;

//    @ApiModelProperty(value = "附件", required = true)
//    @NotEmpty(message = "附件")
//    private File[] Attachments;

    @ApiModelProperty(value = "抄送人id,多个用逗号分隔", required = true)
    private String[] ccid;

    @ApiModelProperty(value = "抄送人邮箱,多个用逗号分隔", required = true)
    private String[] ccEmails;

    @ApiModelProperty(value = "密送人id,多个用逗号分隔", required = true)
    private String[] bccid;

    @ApiModelProperty(value = "密送人邮箱,多个用逗号分隔", required = true)
    private String[] bccEmails;

    @ApiModelProperty(value = "邮件回复")
    private String replyTo;

    @ApiModelProperty(value = "邮件主题")
    private String subject;

    @ApiModelProperty(value = "邮件内容")
    private String content;
}
