package com.xx.admin.controller;

import com.xx.vote.controller.ContactController;
import com.xx.vote.dto.ContactDTO;
import com.xx.vote.dto.ContactVoterDTO;
import com.xx.vote.service.ContactService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/18 10:31
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {

    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactService contactService;

    @Test
    @DisplayName("添加用户")
    public void addContact() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("zhangsan");
        contactDTO.setEmail("xxx@qq.com");
        contactDTO.setIdCard("A123456(3)");
        contactController.addContact(contactDTO);
    }

    @Test
    @DisplayName("用户投票")
    public void addVoter() {
        ContactVoterDTO contactVoterDTO = new ContactVoterDTO();
        contactVoterDTO.setVoterToCandidatId(1L);
        contactVoterDTO.setName("zhangsan");
        contactVoterDTO.setEmail("xxx@qq.com");
        contactVoterDTO.setIdCard("A123456(3)");
        contactService.addVoter(contactVoterDTO);
    }


}
