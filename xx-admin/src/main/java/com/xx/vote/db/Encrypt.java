package com.xx.vote.db;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 15:18
 */
public interface Encrypt {

    String encrypt(String paramString);

    String decrypt(String paramString);
}
