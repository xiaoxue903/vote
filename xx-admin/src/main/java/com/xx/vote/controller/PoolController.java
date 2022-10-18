package com.xx.vote.controller;

import com.xx.common.ResultData;
import com.xx.vote.util.EmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 23:49
 */
@RestController
@Api(tags = "测试线程池")
@RequestMapping("/pool")
public class PoolController {

    @Resource
    private EmailUtil emailUtil;

    @ApiOperation(value = "test pool")
    @GetMapping("/testPool")
    public ResultData testPool() throws ExecutionException, InterruptedException {

        Future<Boolean> stringFuture = emailUtil.sendEmail("");

        //while (true) {
        //    if (stringFuture.isDone()) {
        //        break;
        //    }
        //    Thread.sleep(1000);
        //
        //}
        Boolean s = stringFuture.get();
        System.out.println("--------" + s);

        return new ResultData("操作成功" + s);
    }

}
