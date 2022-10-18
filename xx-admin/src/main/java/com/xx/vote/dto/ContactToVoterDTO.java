package com.xx.vote.dto;

import com.xx.common.BaseEntity;
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
public class ContactToVoterDTO implements Serializable {

	
  	/**
	 * 主键
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	
  	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long contactId;
	
  	/**
	 * 投票对应候选人id
	 */
	@ApiModelProperty(value = "投票对应候选人id")
	private Long voterToContactId;

}
