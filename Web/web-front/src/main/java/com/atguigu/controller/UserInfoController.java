package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.bo.LoginBo;
import com.atguigu.entity.bo.RegisterBo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/20 18:17
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Reference
    private UserInfoService userInfoService;
    
    
    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone")String phone, HttpSession session){
       //模拟短信
        String code = "1111";
        session.setAttribute("CODE",code);

       return Result.ok();
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo, HttpSession session){

        String sessionCode = (String)session.getAttribute("CODE");
        //检查验证码是否正确
        if (!registerBo.getCode().equalsIgnoreCase(sessionCode)) {
          return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }

        //调用业务层
        UserInfo userInfo = userInfoService.getByPhone(registerBo.getPhone());
        //检查手机号是否存在
        if (userInfo != null){
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

//      不存在,可以注册
        //对密码进行加密
        String password = MD5.encrypt(registerBo.getPassword());
//      将registerBo的信息拷贝到userInfo中
        userInfo = new UserInfo();
        BeanUtils.copyProperties(registerBo,userInfo);
        userInfo.setPassword(password);
        //设置状态为1：正常
        userInfo.setStatus(1);
        userInfoService.register(userInfo);

       return Result.ok();
    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginBo loginBo,HttpSession session){

        //调用业务层
        UserInfo userInfo = userInfoService.getByPhone(loginBo.getPhone());
        //如果为空则账号错误
        if (userInfo == null){
          return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
      //不为空则检查密码
        //将密码加密
        String encrypt = MD5.encrypt(loginBo.getPassword());
        if (!encrypt.equals(userInfo.getPassword())){
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        //账户状态检查
        Integer status = userInfo.getStatus();
        if (1 !=status){
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        session.setAttribute("USER",userInfo);

        Map<String,Object> map = new HashMap<>();
        map.put("nickName",userInfo.getNickName());
        map.put("phone",userInfo.getPhone());

        return Result.ok(map);
    }
//    退出登录
    @GetMapping("/logout")
    public Result logout(HttpSession session){
       session.invalidate();
       return Result.ok();
    }

}
