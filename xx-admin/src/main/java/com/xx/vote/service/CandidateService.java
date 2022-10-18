package com.xx.vote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.common.ResultData;
import com.xx.vote.dto.CandidateDTO;
import com.xx.vote.entity.CandidateEntity;
import com.xx.vote.vo.CandidateVO;

import java.util.List;
import java.util.Set;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
public interface CandidateService extends IService<CandidateEntity> {

    /**
     * 添加候选人
     * @param candidateDTO
     * @return
     */
    ResultData addCandidate(CandidateDTO candidateDTO);

    /**
     * 获取列表
     * @return
     */
    ResultData<CandidateVO> getList();

    /**
     * 根据多个Id查询数据
     * @param candidateIds
     * @return
     */
    List<CandidateVO> getByIds(Set<Long> candidateIds);
}

