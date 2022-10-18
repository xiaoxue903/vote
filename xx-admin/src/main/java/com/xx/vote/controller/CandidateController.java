package com.xx.vote.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xx.common.ResultData;
import com.xx.vote.annotation.NeedPage;
import com.xx.vote.annotation.validated.UpdateGroup;
import com.xx.vote.dto.CandidateDTO;
import com.xx.vote.service.CandidateService;
import com.xx.vote.vo.CandidateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 10:51
 */
@RestController
@Api(tags = "候选人管理")
@RequestMapping("/candidate")
public class CandidateController {

    @Resource
    private CandidateService candidateService;

    @ApiOperation(value = "添加候选人")
    @ApiImplicitParam(name = "candidateDTO", value = "候选人DTO", paramType = "body", dataType = "JSON")
    @PostMapping("/addCandidate")
    public ResultData addCandidate(@Validated @RequestBody(required = false) CandidateDTO candidateDTO) {
        return candidateService.addCandidate(candidateDTO);
    }

    @NeedPage
    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "一页个数", paramType = "query", dataType = "Integer")
    })
    @GetMapping("/getList")
    public ResultData<CandidateVO> getList() {
        return candidateService.getList();
    }

}
