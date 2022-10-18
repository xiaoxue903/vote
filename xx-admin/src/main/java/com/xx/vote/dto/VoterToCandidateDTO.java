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
public class VoterToCandidateDTO implements Serializable {

	
  	/**
	 * 主键
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	
  	/**
	 * 投票Id
	 */
	@ApiModelProperty(value = "投票Id")
	private Long voterId;
	
  	/**
	 * 候选人Id
	 */
	@ApiModelProperty(value = "候选人Id")
	private Long candidateId;
	
  	/**
	 * 候选人票数
	 */
	@ApiModelProperty(value = "候选人票数")
	private Integer pollNumber;

}
