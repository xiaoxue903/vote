package com.xx.vote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xx.common.ResultData;
import com.xx.exception.BusinessException;
import com.xx.exception.ParamVerifyException;
import com.xx.utils.ValidationUtil;
import com.xx.vote.dto.VoterCandidateDTO;
import com.xx.vote.entity.VoterEntity;
import com.xx.vote.mapper.CandidateMapper;
import com.xx.vote.service.CandidateService;
import com.xx.vote.service.VoterService;
import com.xx.vote.vo.CandidateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.vote.entity.VoterToCandidateEntity;
import com.xx.vote.mapper.VoterToCandidateMapper;
import com.xx.vote.service.VoterToCandidateService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Slf4j
@Service("voterToCandidateService")
public class VoterToCandidateServiceImpl extends ServiceImpl<VoterToCandidateMapper,VoterToCandidateEntity> implements VoterToCandidateService {

    @Resource
    private VoterService voterService;

    @Resource
    private CandidateService candidateService;

    @Override
    public ResultData addVoterCandidate(VoterCandidateDTO voterCandidateDTO) {

        if(ValidationUtil.isEmpty(voterCandidateDTO)){
            throw new ParamVerifyException("请求参数错误");
        }
        if(ValidationUtil.isEmpty(voterCandidateDTO.getVoterId())){
            throw new ParamVerifyException("投票Id不能为空");
        }
        if(ValidationUtil.isEmpty(voterCandidateDTO.getCandidateIds())){
            throw new ParamVerifyException("候选人Id不能为空");
        }
        if(voterCandidateDTO.getCandidateIds().size() < 2){
            throw new ParamVerifyException("候选人不能少于2位");
        }
        LambdaQueryWrapper<VoterToCandidateEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(VoterToCandidateEntity::getVoterId,voterCandidateDTO.getVoterId());
        lambdaQuery.last("limit 1");
        List<VoterToCandidateEntity> entityList = list(lambdaQuery);
        if(!ValidationUtil.isEmpty(entityList)){
            throw new BusinessException("同一轮投票只能添加一次啊候选人");
        }

        VoterEntity voter = voterService.getById(voterCandidateDTO.getVoterId());
        if(ValidationUtil.isEmpty(voter)){
            throw new BusinessException("投票不存在");
        }

        List<CandidateVO> candidateVOS = candidateService.getByIds(voterCandidateDTO.getCandidateIds());

        if(ValidationUtil.isEmpty(candidateVOS) ||
                candidateVOS.size() != voterCandidateDTO.getCandidateIds().size()){
            throw new BusinessException("候选人查询失败");
        }

        List<VoterToCandidateEntity> voterToCandidates =
                new ArrayList<>(voterCandidateDTO.getCandidateIds().size());
        for (Long candidateId : voterCandidateDTO.getCandidateIds()) {
            VoterToCandidateEntity voterToCandidate = new VoterToCandidateEntity();
            voterToCandidate.setVoterId(voterCandidateDTO.getVoterId());
            voterToCandidate.setCandidateId(candidateId);
            voterToCandidates.add(voterToCandidate);
        }
        saveBatch(voterToCandidates);
        return new ResultData("操作成功");
    }
}