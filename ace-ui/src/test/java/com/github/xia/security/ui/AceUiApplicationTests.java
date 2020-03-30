package com.github.xia.security.ui;

import com.github.xia.security.api.feign.UserFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)

public class AceUiApplicationTests {

	@Autowired
	private UserFeignClient userFeignClient;

	@Test
	public void test1(){
		String systems = userFeignClient.getSystemsByUsername("admin");
		System.out.println("systems-----------------------"+systems);
	}

}
