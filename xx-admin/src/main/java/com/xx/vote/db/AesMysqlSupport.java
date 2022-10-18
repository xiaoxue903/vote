package com.xx.vote.db;

import com.xx.vote.type.SensitiveType;
import com.xx.vote.type.SensitiveTypeHandler;
import com.xx.vote.type.SensitiveTypeRegisty;
import com.xx.vote.util.AesMysqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 15:18
 */
public class AesMysqlSupport implements Encrypt {
    private static final Logger log = LoggerFactory.getLogger(AesMysqlSupport.class);

    private String password;

    private SecretKeySpec secretKeySpec;

    private SensitiveTypeHandler sensitiveTypeHandler = SensitiveTypeRegisty.get(SensitiveType.DEFAUL);

    public AesMysqlSupport(String password) throws Exception {
        if (StringUtils.isEmpty(password))
            throw new IllegalArgumentException("password should not be null!");
        this.password = password;
        this.secretKeySpec = AesMysqlUtil.getSecretKey(password);
    }

    public String encrypt(String value) {
        try {
            return AesMysqlUtil.encrypt(value, this.secretKeySpec);
        } catch (Exception e) {
            log.error("AES{}", this.sensitiveTypeHandler.handle(this.password));
            throw new IllegalStateException("AES"+ e.getMessage(), e);
        }
    }

    public String decrypt(String value) {
        if (StringUtils.isEmpty(value))
            return "";
        try {
            return AesMysqlUtil.decrypt(value, this.secretKeySpec);
        } catch (Exception e) {
            log.error("AES{},{}", this.sensitiveTypeHandler.handle(this.password), value);
            throw new IllegalStateException("AES"+ e.getMessage(), e);
        }
    }
}

