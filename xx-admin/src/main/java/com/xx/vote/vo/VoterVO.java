package com.xx.vote.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Data
@TableName("voter")
public class VoterVO  implements Serializable {

	
  	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(value = "")
	private Long id;
	
  	/**
	 * 投票标题
	 */
	@ApiModelProperty(value = "投票标题")
	private String title;
	
  	/**
	 * 0待开始 1已开始 2已结束
	 */
	@ApiModelProperty(value = "0待开始 1已开始 2已结束")
	private Integer status;
	
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

	@ApiModelProperty(value = "候选人列表")
	private List<CandidateVO> candidateList;
}
