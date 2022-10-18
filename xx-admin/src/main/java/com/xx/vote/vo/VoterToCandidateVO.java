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
@TableName("voter_to_candidate")
public class VoterToCandidateVO  implements Serializable {

	
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
	
  	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createDate;
	
  	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date updateDate;
}
