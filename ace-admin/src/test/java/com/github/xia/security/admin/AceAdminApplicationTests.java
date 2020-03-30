package com.github.xia.security.admin;

import com.github.xia.security.admin.entity.BaseUser;
import com.github.xia.security.admin.mapper.BaseMenuMapper;
import com.github.xia.security.admin.mapper.BaseUserMapper;
import com.github.xia.security.api.feign.UserFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AceAdminApplicationTests {

    @Autowired
    private BaseMenuMapper baseMenuMapper;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Test
    public void test1(){
        baseUserMapper.selectLeaderByGroupId(1);
    }

    @Test
    public void test2(){
        String systems = userFeignClient.getSystemsByUsername("admin");
        System.out.println(systems);
    }


}
