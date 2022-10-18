package com.xx.vote.service.impl;

import com.xx.common.ResultData;
import com.xx.exception.BusinessException;
import com.xx.exception.ParamVerifyException;
import com.xx.utils.ValidationUtil;
import com.xx.utils.ValueUtil;
import com.xx.vote.dto.CandidateDTO;
import com.xx.vote.mybatis.BaseServiceImpl;
import com.xx.vote.vo.CandidateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.vote.entity.CandidateEntity;
import com.xx.vote.mapper.CandidateMapper;
import com.xx.vote.service.CandidateService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Slf4j
@Service("candidateService")
public class CandidateServiceImpl extends BaseServiceImpl<CandidateMapper,CandidateEntity> implements CandidateService {

    @Override
    public ResultData addCandidate(CandidateDTO candidateDTO) {
        if(ValidationUtil.isEmpty(candidateDTO)){
            throw new ParamVerifyException("请求参数不能为空");
        }
        CandidateEntity candidateEntity = ValueUtil.copyFieldValue(candidateDTO, CandidateEntity.class);
        // 校验身份证号重复
        boolean duplicate = checkDuplicate(null, CandidateEntity::getIdCard, candidateEntity.getIdCard());
        if(duplicate){
            throw new BusinessException("身份证号重复");
        }
        save(candidateEntity);

        return new ResultData("操作成功");
    }

    @Override
    public ResultData<CandidateVO> getList() {
        List<CandidateEntity> list = list();
        List<CandidateVO> candidateVOS = ValueUtil.copyFieldValues(list, CandidateVO.class);
        return new ResultData(candidateVOS);
    }

    @Override
    public List<CandidateVO> getByIds(Set<Long> candidateIds) {

        if(ValidationUtil.isEmpty(candidateIds)){
            return Collections.EMPTY_LIST;
        }
        List<CandidateEntity> candidateVos = baseMapper.selectBatchIds(candidateIds);

        return ValidationUtil.isEmpty(candidateVos) ? Collections.EMPTY_LIST :
                ValueUtil.copyFieldValues(candidateVos,CandidateVO.class);
    }
}