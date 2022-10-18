package com.xx.vote.controller;

import com.xx.common.ResultData;
import com.xx.vote.dto.ContactDTO;
import com.xx.vote.dto.ContactVoterDTO;
import com.xx.vote.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 21:51
 */
@RestController
@Api(tags = "用户,投票")
@RequestMapping("/contact")
public class ContactController {

    @Resource
    private ContactService contactService;

    @ApiOperation(value = "添加用户")
    @ApiImplicitParam(name = "candidateDTO", value = "候选人DTO", paramType = "body", dataType = "JSON")
    @PostMapping("/addContact")
    public ResultData addContact(@Validated @RequestBody(required = false)ContactDTO contactDTO){
        return contactService.addContact(contactDTO);
    }


    @ApiOperation(value = "用户投票")
    @ApiImplicitParam(name = "candidateDTO", value = "候选人DTO", paramType = "body", dataType = "JSON")
    @PostMapping("/addVoter")
    public ResultData addVoter(@Validated @RequestBody(required = false) ContactVoterDTO contactVoterDTO){
        return contactService.addVoter(contactVoterDTO);
    }


}
