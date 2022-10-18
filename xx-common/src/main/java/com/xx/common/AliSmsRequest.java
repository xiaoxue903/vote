package com.xx.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@ApiModel("短信信息")
@Data
public class AliSmsRequest implements Serializable {
    @ApiModelProperty("要接受短信的手机号")
    private String phoneNumbers;

    @ApiModelProperty("短信签名")
    private String signName = "阿里云短信测试";

    @ApiModelProperty("申请的模板编码")
    private String templateCode = "SMS_154950909";

    @ApiModelProperty("短信发送参数-json格式的字符串，如{\"code\":123456}")
    private String templateParam;

}
