package com.xx.vote.entity;

import com.xx.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.xx.vote.annotation.EncryptField;
import com.xx.vote.annotation.SensitiveEncryptEnabled;
import lombok.Data;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Data
@TableName("candidate")
@SensitiveEncryptEnabled
public class CandidateEntity extends BaseEntity implements Serializable {


    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 候选人姓名
     */
    private String name;

    /**
     * 候选人手机号
     */
    @EncryptField
    private String phone;

    /**
     * 候选人身份证号
     */
    private String idCard;

}
