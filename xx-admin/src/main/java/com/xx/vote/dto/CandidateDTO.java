package com.xx.vote.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.xx.vote.annotation.EncryptField;
import com.xx.vote.annotation.SensitiveEncryptEnabled;
import com.xx.vote.annotation.validated.AddGroup;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Data
@SensitiveEncryptEnabled
public class CandidateDTO implements Serializable {

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 候选人姓名
     */
    @NotBlank(message = "候选人姓名不能为空")
    @ApiModelProperty(value = "候选人姓名")
    private String name;

    /**
     * 候选人手机号
     */
    @EncryptField
    @NotBlank(message = "候选人手机号不能为空")
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "手机号格式错误")
    @ApiModelProperty(value = "候选人手机号")
    private String phone;

    /**
     * 候选人身份证号
     */
    @NotBlank(message = "候选人身份证号不能为空")
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$", message = "身份证号格式错误")
    @ApiModelProperty(value = "候选人身份证号")
    private String idCard;

}
