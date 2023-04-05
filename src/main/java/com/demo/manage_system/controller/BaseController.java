package com.demo.manage_system.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.manage_system.service.SysMenuService;
import com.demo.manage_system.service.SysRoleMenuService;
import com.demo.manage_system.service.SysRoleService;
import com.demo.manage_system.service.SysUserRoleService;
import com.demo.manage_system.service.SysUserService;
import com.demo.manage_system.util.RedisUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

public class BaseController {
  @Autowired
  HttpServletRequest req;

  @Autowired
  RedisUtil redisUtil;

  @Autowired
  SysUserService sysUserService;

  @Autowired
  SysRoleService sysRoleService;

  @Autowired
  SysMenuService sysMenuService;

  @Autowired
  SysUserRoleService sysUserRoleService;

  @Autowired
  SysRoleMenuService sysRoleMenuService;

  public Page getPage() {
    int current = ServletRequestUtils.getIntParameter(req, "cuurent", 1);
    int size = ServletRequestUtils.getIntParameter(req, "size", 10);
    return new Page(current, size);
  }

}
