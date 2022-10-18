package com.xx.vote.util;

import com.xx.vote.pool.ThreadPoolType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 23:41
 */
@Slf4j
@Component
public class EmailUtil {

    @Async(ThreadPoolType.EMAIL_THREAD_POOL)
    public Future<Boolean> sendEmail(String email) throws InterruptedException {
        log.info("邮件发送成功:" + email);
        return new AsyncResult(true);
    }


}
