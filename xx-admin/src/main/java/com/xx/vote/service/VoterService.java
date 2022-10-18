package com.xx.vote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.common.ResultData;
import com.xx.vote.dto.VoterDTO;
import com.xx.vote.entity.VoterEntity;
import com.xx.vote.vo.VoterVO;

import java.util.List;

/**
 * @author xueqimiao
 * @date 2022-10-17 10:44:17
 */
public interface VoterService extends IService<VoterEntity> {

    /**
     * 添加投票
     * @return
     */
    ResultData<String> addVoter(VoterDTO voterDTO);

    /**
     * 获取列表
     * @return
     */
    List<VoterVO> getList();

    /**
     * 修改状态
     * @param id
     * @param status 状态 0待开始 1已开始 2已结束
     * @return
     */
    ResultData editStatus(Long id, Integer status);
}

