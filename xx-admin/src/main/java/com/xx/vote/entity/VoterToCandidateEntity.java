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
@TableName("voter_to_candidate")
public class VoterToCandidateEntity extends BaseEntity implements Serializable {

	
  	/**
	 * 主键
	 */
	@TableId
	private Long id;
	
  	/**
	 * 投票Id
	 */
	private Long voterId;
	
  	/**
	 * 候选人Id
	 */
	private Long candidateId;
	
  	/**
	 * 候选人票数
	 */
	private Integer pollNumber;
}
