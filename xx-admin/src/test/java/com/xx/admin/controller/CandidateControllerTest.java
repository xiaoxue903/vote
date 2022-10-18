package com.xx.admin.controller;

import com.xx.vote.controller.CandidateController;
import com.xx.vote.dto.CandidateDTO;
import com.xx.vote.service.CandidateService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * @Author: xueqimiao
 * @Date: 2022/10/18 10:40
 */
@RunWith(MockitoJUnitRunner.class)
public class CandidateControllerTest {

    @InjectMocks
    private CandidateController candidateController;

    @Mock
    private CandidateService candidateService;

    @Test
    @DisplayName("添加候选人")
    public void addCandidate() {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setName("zhangsan");
        candidateDTO.setIdCard("A123456(3)");
        candidateController.addCandidate(candidateDTO);
    }

    @Test
    @DisplayName("获取列表")
    public void getList() {
        candidateController.getList();
    }

}
