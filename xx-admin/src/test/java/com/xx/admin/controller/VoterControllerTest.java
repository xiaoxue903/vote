package com.xx.admin.controller;

import com.xx.vote.controller.VoterController;
import com.xx.vote.dto.VoterCandidateDTO;
import com.xx.vote.dto.VoterDTO;
import com.xx.vote.service.VoterService;
import com.xx.vote.service.VoterToCandidateService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 15:05
 */
@RunWith(MockitoJUnitRunner.class)
public class VoterControllerTest {

    @InjectMocks
    private VoterController voterController;

    @Mock
    private VoterService voterService;

    @Mock
    private VoterToCandidateService voterToCandidateService;

    @Test
    @DisplayName("添加投票")
    public void addVoter() {
        VoterDTO voterDTO = new VoterDTO();
        voterDTO.setStatus(0);
        voterDTO.setTitle("第十届学生会投票");
        voterController.addVoter(voterDTO);
    }

    @Test
    @DisplayName("给投票添加候选人")
    public void addVoterCandidate() {
        VoterCandidateDTO voterCandidateDTO = new VoterCandidateDTO();
        voterCandidateDTO.setVoterId(1L);
        voterCandidateDTO.setCandidateIds(new HashSet<>(Arrays.asList(1L,2L)));
        voterController.addVoterCandidate(voterCandidateDTO);
    }

    @Test
    @DisplayName("获取列表")
    public void getList() {

        voterController.getList();
    }

    @Test
    @DisplayName("修改状态")
    public void editStatus() {
        Long id = 1L;
        Integer status = 0;
        voterController.editStatus(id, status);
    }

}
