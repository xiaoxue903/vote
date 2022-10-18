package com.xx.vote.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 22:47
 */
@Data
public class ContactVoterDTO implements Serializable {


    @ApiModelProperty(value = "关联表Id")
    private Long voterToCandidatId;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "邮箱")
    private String email;


}
