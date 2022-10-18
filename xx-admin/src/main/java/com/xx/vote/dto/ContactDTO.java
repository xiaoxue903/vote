package com.xx.vote.dto;

import com.xx.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class ContactDTO implements Serializable {

	
  	/**
	 * 主键
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	
  	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@NotBlank(message = "姓名不能为空")
	private String name;
	
  	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	@NotBlank(message = "邮箱不能为空")
	@Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "邮箱格式错误")
	private String email;
	
  	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	@NotBlank(message = "候选人身份证号不能为空")
	@Pattern(regexp = "^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$", message = "身份证号格式错误")
	private String idCard;

}
