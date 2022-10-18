package com.xx.vote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xx.common.ResultData;
import com.xx.exception.BusinessException;
import com.xx.exception.ParamVerifyException;
import com.xx.utils.FunctionUtils;
import com.xx.utils.ValidationUtil;
import com.xx.utils.ValueUtil;
import com.xx.vote.dto.VoterDTO;
import com.xx.vote.entity.VoterToCandidateEntity;
import com.xx.vote.mybatis.BaseServiceImpl;
import com.xx.vote.service.CandidateService;
import com.xx.vote.service.VoterToCandidateService;
import com.xx.vote.vo.CandidateVO;
import com.xx.vote.vo.VoterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.xx.vote.entity.VoterEntity;
import com.xx.vote.mapper.VoterMapper;
import com.xx.vote.service.VoterService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
@Slf4j
@Service("voterService")
public class VoterServiceImpl extends BaseServiceImpl<VoterMapper,VoterEntity> implements VoterService {

    @Resource
    private CandidateService candidateService;

    @Resource
    private VoterToCandidateService voterToCandidateService;

    @Override
    public ResultData<String> addVoter(VoterDTO voterDTO) {
        if(ValidationUtil.isEmpty(voterDTO)){
            throw new ParamVerifyException("请求参数错误");
        }

        VoterEntity voter = ValueUtil.copyFieldValue(voterDTO, VoterEntity.class);
        // 校验标题不能重复
        boolean duplicate = checkDuplicate(null, VoterEntity::getTitle, voter.getTitle());
        if(duplicate){
            throw new BusinessException("标题不能重复");
        }
        save(voter);
        return new ResultData<>("操作成功");
    }

    @Override
    public List<VoterVO> getList() {
        List<VoterEntity> list = list();
        if(ValidationUtil.isEmpty(list)){
            return Collections.emptyList();
        }
        List<VoterVO> voterVOS = ValueUtil.copyFieldValues(list, VoterVO.class);
        List<Long> voterIds = FunctionUtils.map(voterVOS, VoterVO::getId);
        LambdaQueryWrapper<VoterToCandidateEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.in(VoterToCandidateEntity::getVoterId,voterIds);
        List<VoterToCandidateEntity> voterToList = voterToCandidateService.list(lambdaQuery);
        Map<Long, List<VoterToCandidateEntity>> voterMap = FunctionUtils.group(voterToList, VoterToCandidateEntity::getVoterId);

        for (VoterVO voterVO : voterVOS) {
            if(!voterMap.containsKey(voterVO.getId())){
                continue;
            }
            voterVO.setCandidateList(ValueUtil.copyFieldValues(voterMap.get(voterVO.getId()),CandidateVO.class));
        }

        List<List<CandidateVO>> lists = FunctionUtils.map(voterVOS, VoterVO::getCandidateList);

        List<CandidateVO> candidateS = new ArrayList<>();
        for (List<CandidateVO> candidateVOS : lists) {
            for (CandidateVO candidateVO : candidateVOS) {
                candidateS.add(candidateVO);
            }
        }
        Set<Long> candidateIds = FunctionUtils.mapToSet(candidateS, CandidateVO::getCandidateId);
        List<CandidateVO> candidateVOList = candidateService.getByIds(candidateIds);

        Map<Long, List<CandidateVO>> listMap = FunctionUtils.group(candidateVOList, CandidateVO::getId);

        for (VoterVO voterVO : voterVOS) {
            List<CandidateVO> candidateList = voterVO.getCandidateList();
            if(ValidationUtil.isEmpty(candidateList)){
                continue;
            }
            for (CandidateVO candidateVO : candidateList) {
                List<CandidateVO> candidateVOS = listMap.get(candidateVO.getCandidateId());
                CandidateVO candidateVO1 = candidateVOS.get(0);
                candidateVO.setName(candidateVO1.getName());
                candidateVO.setPhone(candidateVO1.getPhone());
                candidateVO.setIdCard(candidateVO1.getIdCard());
            }
        }
        return voterVOS;
    }

    @Override
    public ResultData editStatus(Long id, Integer status) {
        if(ValidationUtil.isEmpty(id)){
            throw new ParamVerifyException("id不能为空");
        }

        if(ValidationUtil.isEmpty(status)){
            throw new ParamVerifyException("状态不能为空");
        }

        VoterEntity voterEntity = getById(id);
        if(ValidationUtil.isEmpty(voterEntity)){
            throw new BusinessException("投票不存在");
        }
        voterEntity.setStatus(status);
        updateById(voterEntity);

        // 关闭投票
        if(Integer.valueOf(2).equals(status)){
            // 获取此轮投票的所有用户 发送邮件


        }

        return new ResultData("操作成功");
    }
}