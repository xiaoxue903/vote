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
@TableName("voter")
public class VoterEntity extends BaseEntity implements Serializable {

  	/**
	 * 主键
	 */
	@TableId
	private Long id;
	
  	/**
	 * 投票标题
	 */
	private String title;
	
  	/**
	 * 0待开始 1已开始 2已结束
	 */
	private Integer status;

}
