package com.zkq.service;

import com.zkq.domain.UsersCustom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class UserserviceimplTest {

    @Autowired
    Userservice Userservice;
    @Test
    public void checkUserNameAndPassword() {
        UsersCustom usersCustom=new UsersCustom();
        usersCustom.setUsername("zkq162534");
        usersCustom.setPassword("zkq162534");
        System.out.println(Userservice.checkUserNameAndPassword(usersCustom));
    }
}