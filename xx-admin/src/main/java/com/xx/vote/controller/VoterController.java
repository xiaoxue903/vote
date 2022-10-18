package com.xx.vote.controller;

import com.xx.common.ResultData;
import com.xx.vote.annotation.NeedPage;
import com.xx.vote.annotation.validated.AddGroup;
import com.xx.vote.dto.VoterCandidateDTO;
import com.xx.vote.dto.VoterDTO;
import com.xx.vote.service.VoterService;
import com.xx.vote.service.VoterToCandidateService;
import com.xx.vote.vo.VoterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 15:05
 */
@RestController
@Api(tags = "投票管理")
@RequestMapping("/voter")
public class VoterController {

    @Resource
    private VoterService voterService;

    @Resource
    private VoterToCandidateService voterToCandidateService;

    @ApiOperation(value = "添加投票")
    @ApiImplicitParam(name = "candidateDTO", value = "候选人DTO", paramType = "body", dataType = "JSON")
    @PostMapping("/addVoter")
    public ResultData<String> addVoter(@Validated({AddGroup.class}) @RequestBody(required = false)
                                               VoterDTO voterDTO) {
        return voterService.addVoter(voterDTO);
    }

    @ApiOperation(value = "给投票添加候选人")
    @ApiImplicitParam(name = "ids", value = "候选人id", paramType = "body", dataType = "JSON")
    @PostMapping("/addVoterCandidate")
    public ResultData addVoterCandidate(@RequestBody(required = false) VoterCandidateDTO voterCandidateDTO) {

        return voterToCandidateService.addVoterCandidate(voterCandidateDTO);
    }

    @ApiOperation(value = "获取列表")
    //@ApiImplicitParam(name = "candidateDTO", value = "候选人DTO", paramType = "body", dataType = "JSON")
    @NeedPage
    @GetMapping("/getList")
    public ResultData<VoterVO> getList() {

        return new ResultData(voterService.getList());
    }

    @ApiOperation(value = "修改状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "状态 0待开始 1已开始 2已结束", paramType = "query", dataType = "Integer")
    })
    @GetMapping("/editStatus")
    public ResultData editStatus(Long id, Integer status) {
        return voterService.editStatus(id, status);
    }

}
