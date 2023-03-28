package com.demo.manage_system.controller;

import com.demo.manage_system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @Autowired
  SysUserService sysUserService;

  @GetMapping("/test")
  public Object test() {
    return sysUserService.list();
  }
}