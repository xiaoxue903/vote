package com.xx.vote.test;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @ClassName: JasyptTest
 * @Author: xueqimiao
 * @Date: 2021/11/23 15:42
 */
public class JasyptTest {

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的密钥
        textEncryptor.setPassword("xiaoxueblog");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("mac_root");
        //String decrypt = textEncryptor.decrypt("83T+oQSPVtjizgExNQCo0yplG39laem/");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }
}