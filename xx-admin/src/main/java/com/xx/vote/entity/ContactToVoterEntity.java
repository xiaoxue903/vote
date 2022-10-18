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
@TableName("contact_to_voter")
public class ContactToVoterEntity extends BaseEntity implements Serializable {

	
  	/**
	 * 主键
	 */
	@TableId
	private Long id;
	
  	/**
	 * 用户id
	 */
	private Long contactId;
	
  	/**
	 * 投票id
	 */
	private Long voterToContactId;

}
