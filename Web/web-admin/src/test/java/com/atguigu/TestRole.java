//package com.atguigu;
//
//import com.atguigu.entity.Role;
//import com.atguigu.service.RoleService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
///**
// * @author HaQQ
// * @version 1.0
// * @date 2022/6/13 0:18
// */
//@ContextConfiguration(locations = "classpath:spring/spring-service.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
//public class TestRole {
//    @Autowired
//    private RoleService roleService;
//
//    private Logger logger = LoggerFactory.getLogger(TestRole.class);
//
//    @Test
//    public void test01(){
//        List<Role> all = roleService.findAll();
//       logger.info(all.toString());
//    }
//}
