package com.github.xia.security.admin;

import com.github.xia.security.admin.entity.BaseUser;
import com.github.xia.security.admin.service.BaseUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AceAdminApplicationTests {

    @Autowired
    private BaseUserService baseUserService;

    @Test
    public void test1() {
        BaseUser baseUser = baseUserService.getById("1");
    }

}
