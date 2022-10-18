package com.xx.vote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.common.ResultData;
import com.xx.vote.dto.ContactDTO;
import com.xx.vote.dto.ContactVoterDTO;
import com.xx.vote.entity.ContactEntity;
import com.xx.vote.mybatis.BaseService;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
public interface ContactService extends BaseService<ContactEntity> {

    /**
     * 添加用户
     * @param contactDTO
     * @return
     */
    ResultData addContact(ContactDTO contactDTO);

    /**
     * 用户投票
     * @param contactVoterDTO
     * @return
     */
    ResultData addVoter(ContactVoterDTO contactVoterDTO);
}

