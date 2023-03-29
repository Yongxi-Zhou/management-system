package com.demo.manage_system.controller;

import com.demo.manage_system.util.RedisUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
  @Autowired
  HttpServletRequest req;

  @Autowired
  RedisUtil redisUtil;

}
