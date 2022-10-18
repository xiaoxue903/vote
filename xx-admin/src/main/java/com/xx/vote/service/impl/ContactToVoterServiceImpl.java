package com.xx.vote.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.vote.entity.ContactToVoterEntity;
import com.xx.vote.mapper.ContactToVoterMapper;
import com.xx.vote.service.ContactToVoterService;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Slf4j
@Service("contactToVoterService")
public class ContactToVoterServiceImpl extends ServiceImpl<ContactToVoterMapper,ContactToVoterEntity> implements ContactToVoterService {

}