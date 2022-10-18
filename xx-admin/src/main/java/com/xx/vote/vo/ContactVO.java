package com.xx.vote.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Data
@TableName("contact")
public class ContactVO  implements Serializable {

	
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
	private String name;
	
  	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;
	
  	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	private String idCard;
	
  	/**
	 * 创建日期
	 */
	@ApiModelProperty(value = "创建日期")
	private Date createDate;
	
  	/**
	 * 更新日期
	 */
	@ApiModelProperty(value = "更新日期")
	private Date updateDate;
}
