package com.xx.vote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xx.common.ResultData;
import com.xx.exception.BusinessException;
import com.xx.exception.ParamVerifyException;
import com.xx.utils.ValidationUtil;
import com.xx.utils.ValueUtil;
import com.xx.vote.dto.ContactDTO;
import com.xx.vote.dto.ContactVoterDTO;
import com.xx.vote.entity.ContactToVoterEntity;
import com.xx.vote.entity.VoterEntity;
import com.xx.vote.entity.VoterToCandidateEntity;
import com.xx.vote.mybatis.BaseServiceImpl;
import com.xx.vote.service.ContactToVoterService;
import com.xx.vote.service.VoterService;
import com.xx.vote.service.VoterToCandidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.vote.entity.ContactEntity;
import com.xx.vote.mapper.ContactMapper;
import com.xx.vote.service.ContactService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Slf4j
@Service("contactService")
public class ContactServiceImpl extends BaseServiceImpl<ContactMapper, ContactEntity> implements ContactService {

    @Resource
    private VoterToCandidateService voterToCandidateService;

    @Resource
    private ContactToVoterService contactToVoterService;

    @Resource
    private VoterService voterService;

    @Override
    public ResultData addContact(ContactDTO contactDTO) {

        if (ValidationUtil.isEmpty(contactDTO)) {
            throw new ParamVerifyException("请求参数错误");
        }
        ContactEntity contact = ValueUtil.copyFieldValue(contactDTO, ContactEntity.class);
        boolean duplicate = checkDuplicate(null, ContactEntity::getIdCard, contact.getIdCard());
        if (duplicate) {
            throw new BusinessException("身份证号重复");
        }
        save(contact);
        return new ResultData("操作成功");
    }

    @Override
    public ResultData addVoter(ContactVoterDTO contactVoterDTO) {
        if (ValidationUtil.isEmpty(contactVoterDTO)) {
            throw new ParamVerifyException("请求参数错误");
        }
        // 根据身份证查询用户，没有创建，有则更新
        LambdaQueryWrapper<ContactEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.in(ContactEntity::getIdCard, contactVoterDTO.getIdCard());
        ContactEntity contact = getOne(lambdaQuery);

        ContactEntity contactEntity = ValueUtil.copyFieldValue(contactVoterDTO, ContactEntity.class);
        if (ValidationUtil.isEmpty(contact)) {
            save(contactEntity);
        } else {
            contactEntity.setId(contact.getId());
            updateById(contactEntity);
        }

        VoterToCandidateEntity voterToCandidateEntity = voterToCandidateService.getById(contactVoterDTO.getVoterToCandidatId());

        if (ValidationUtil.isEmpty(voterToCandidateEntity)) {
            throw new BusinessException("投票不存在");
        }

        VoterEntity voterEntity = voterService.getById(voterToCandidateEntity.getVoterId());

        if (!Integer.valueOf("1").equals(voterEntity.getStatus())) {
            throw new BusinessException("投票不在进行中，不能投票");
        }

        // 判断用户是否投过票
        LambdaQueryWrapper<ContactToVoterEntity>
                queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ContactToVoterEntity::getContactId, contactEntity.getId());
        queryWrapper.eq(ContactToVoterEntity::getVoterToContactId, contactVoterDTO.getVoterToCandidatId());
        List<ContactToVoterEntity> list = contactToVoterService.list(queryWrapper);
        if (!ValidationUtil.isEmpty(list)) {
            throw new BusinessException("此用户已经投过票,不能重复投票");
        }

        ContactToVoterEntity contactToVoterEntity = new ContactToVoterEntity();
        contactToVoterEntity.setContactId(contactEntity.getId());
        contactToVoterEntity.setVoterToContactId(contactVoterDTO.getVoterToCandidatId());
        contactToVoterService.save(contactToVoterEntity);
        voterToCandidateEntity.setPollNumber(ValidationUtil.isEmpty(voterToCandidateEntity.getPollNumber()) ? 1 : (voterToCandidateEntity.getPollNumber() + 1));
        voterToCandidateService.updateById(voterToCandidateEntity);
        return new ResultData("操作成功");
    }
}