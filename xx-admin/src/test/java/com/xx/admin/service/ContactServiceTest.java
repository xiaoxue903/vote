package com.xx.admin.service;

import com.xx.vote.entity.ContactEntity;
import com.xx.vote.service.ContactService;
import com.xx.vote.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/18 10:50
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    @InjectMocks
    private ContactServiceImpl contactServiceImpl;

    @Mock
    private ContactService contactService;

    public MockMvc mockMvc;

    @Resource
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() throws NoSuchFieldException, IllegalAccessException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void addContact(){
       // Mockito.when(contactService.checkDuplicate(null,new String[]{},new String[]{})).thenReturn(false);
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setName("hhh");
        contactEntity.setEmail("xue@qq.com");
        contactEntity.setIdCard("A123466(2)");
        contactService.save(contactEntity);
    }

}
