package com.xx.vote.dto;

import com.xx.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xx.vote.annotation.validated.AddGroup;
import com.xx.vote.annotation.validated.ConstantEnumValue;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Data
public class VoterDTO implements Serializable {

	
  	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Long id;
	
  	/**
	 * 投票标题
	 */
	@ApiModelProperty(value = "投票标题")
	@NotBlank(message = "投票标题不能为空",groups = AddGroup.class)
	private String title;
	
  	/**
	 * 0待开始 1已开始 2已结束
	 */
	@ApiModelProperty(value = "0待开始 1已开始 2已结束")
	@ConstantEnumValue(intValues = {0, 1}, message = "投票状态只能为0或者1",groups = AddGroup.class)
	private Integer status;

}
