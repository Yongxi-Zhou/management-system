package com.demo.manage_system.controller;

import com.demo.manage_system.common.lang.Result;
import com.demo.manage_system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @Autowired
  SysUserService sysUserService;

  @Autowired
  BCryptPasswordEncoder bcryptPasswordEncoder;

  @GetMapping("/test")
  public Result test() {
    return Result.succ(sysUserService.list());
  }

  @GetMapping("/test/pass")
  public Result pass() {
    String password = bcryptPasswordEncoder.encode("123456");

    boolean match = bcryptPasswordEncoder.matches("123456", password);

    System.out.println("匹配结果：" + match);
    return Result.succ(password);
  }

}
