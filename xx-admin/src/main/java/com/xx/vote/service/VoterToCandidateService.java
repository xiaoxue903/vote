package com.xx.vote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.common.ResultData;
import com.xx.vote.dto.VoterCandidateDTO;
import com.xx.vote.entity.VoterToCandidateEntity;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
public interface VoterToCandidateService extends IService<VoterToCandidateEntity> {

    /**
     * 给投票添加候选人
     * @param voterCandidateDTO
     * @return
     */
    ResultData addVoterCandidate(VoterCandidateDTO voterCandidateDTO);
}

