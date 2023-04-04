package com.demo.manage_system.controller;


import cn.hutool.core.map.MapUtil;
import com.demo.manage_system.common.dto.SysMenuDto;
import com.demo.manage_system.common.lang.Result;
import com.demo.manage_system.entity.SysUser;
import java.security.Principal;
import java.util.List;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yongxi
 * @since 2023-03-27
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

  @GetMapping("/nav")
  public Result nav(Principal principal) {
    SysUser sysUser = sysUserService.getByUsername(principal.getName());

    // 获取权限信息
    String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());// ROLE_admin,ROLE_normal,sys:user:list,....
    String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");

    // 获取导航栏信息
    List<SysMenuDto> navs = sysMenuService.getCurrentUserNav();

    return Result.succ(MapUtil.builder()
        .put("authoritys", authorityInfoArray)
        .put("nav", navs)
        .map()
    );
  }
}
