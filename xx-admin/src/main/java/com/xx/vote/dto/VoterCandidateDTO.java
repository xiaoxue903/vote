package com.xx.vote.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 21:21
 */
@Data
public class VoterCandidateDTO implements Serializable {

    @ApiModelProperty(value = "投票Id")
    private Long voterId;

    @ApiModelProperty(value = "候选人Ids")
    private Set<Long> candidateIds;

}
