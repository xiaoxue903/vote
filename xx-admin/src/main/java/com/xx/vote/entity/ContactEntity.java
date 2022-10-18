package com.xx.vote.entity;

import com.xx.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Data
@TableName("contact")
public class ContactEntity extends BaseEntity implements Serializable {

	
  	/**
	 * 主键
	 */
	@TableId
	private Long id;
	
  	/**
	 * 姓名
	 */
	private String name;
	
  	/**
	 * 邮箱
	 */
	private String email;
	
  	/**
	 * 身份证号
	 */
	private String idCard;

}
