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
@TableName("candidate")
public class CandidateVO  implements Serializable {

	
  	/**
	 * 主键
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "候选人id")
	private Long candidateId;
	
  	/**
	 * 候选人姓名
	 */
	@ApiModelProperty(value = "候选人姓名")
	private String name;
	
  	/**
	 * 候选人手机号
	 */
	@ApiModelProperty(value = "候选人手机号")
	private String phone;
	
  	/**
	 * 候选人身份证号
	 */
	@ApiModelProperty(value = "候选人身份证号")
	private String idCard;

  	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createDate;

  	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateDate;

}
