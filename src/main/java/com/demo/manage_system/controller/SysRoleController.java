package com.demo.manage_system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.manage_system.common.lang.Const;
import com.demo.manage_system.common.lang.Result;
import com.demo.manage_system.entity.SysRole;
import com.demo.manage_system.entity.SysRoleMenu;
import com.demo.manage_system.entity.SysUserRole;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

//    @GetMapping("/info/{id}")
//    @PreAuthorize("hasAuthority('sys:role:list')")
//    public Result info(@PathVariable("id") Long id) {
//      SysRole sysRole = sysRoleService.getById(id);
//      List<SysRoleMenu> roleMenus = sysRoleMenuService.list(
//          new QueryWrapper<SysRoleMenu>().eq("role_id", id));
//
//      List<Long> menuIds = roleMenus.stream().map(r -> r.getMenuId()).collect(Collectors.toList());
//      sysRole.setMenusIds(menuIds);
//      return Result.succ(sysRole);
//    }

  @PreAuthorize("hasAuthority('sys:role:list')")
  @GetMapping("/info/{id}")
  public Result info(@PathVariable("id") Long id) {

    SysRole sysRole = sysRoleService.getById(id);

    // 获取角色相关联的菜单id
    List<SysRoleMenu> roleMenus = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
    List<Long> menuIds = roleMenus.stream().map(p -> p.getMenuId()).collect(Collectors.toList());

    sysRole.setMenuIds(menuIds);
    return Result.succ(sysRole);
  }

  @GetMapping("/list")
  @PreAuthorize("hasAuthority('sys:role:list')")
  public Result list(String name) {
    Page<SysRole> pageData = sysRoleService.page(getPage(),
        new QueryWrapper<SysRole>().like(StrUtil.isNotBlank(name), "name", name));
    return Result.succ(pageData);
  }



  @PostMapping("/save")
  @PreAuthorize("hasAuthority('sys:role:save')")
  public Result save(@Validated @RequestBody SysRole sysRole) {
    sysRole.setCreated(LocalDateTime.now());
    sysRole.setStatu(Const.STATUS_ON);

    sysRoleService.save(sysRole);
    return Result.succ(sysRole);
  }

  @PostMapping("/update")
  @PreAuthorize("hasAuthority('sys:role:update')")
  public Result update(@Validated @RequestBody SysRole sysRole) {
    sysRole.setUpdated(LocalDateTime.now());

    sysRoleService.updateById(sysRole);

    // 更新缓存
    sysUserService.clearUserAuthorityInfoByRoleId(sysRole.getId());
    return Result.succ(sysRole);
  }

  @PostMapping("/delete")
  @Transactional
  @PreAuthorize("hasAuthority('sys:role:delete')")
  public Result delete(@RequestBody Long[] ids) {
    sysRoleService.removeByIds(Arrays.asList(ids));

    // 删除中间表
    sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().in("role_id", ids));
    sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("role_id", ids));

    Arrays.stream(ids).forEach(id -> {
      // 更新缓存
      sysUserService.clearUserAuthorityInfoByRoleId(id);
    });
    return Result.succ("");
  }

  @Transactional
  @PostMapping("/perm/{roleId}")
  @PreAuthorize("hasAuthority('sys:role:perm')")
  public Result info(@PathVariable("roleId") Long roleId, @RequestBody Long[] menuIds) {

    List<SysRoleMenu> sysRoleMenus = new ArrayList<>();

    Arrays.stream(menuIds).forEach(menuId -> {
      SysRoleMenu roleMenu = new SysRoleMenu();
      roleMenu.setMenuId(menuId);
      roleMenu.setRoleId(roleId);

      sysRoleMenus.add(roleMenu);
    });

    // 先删除原来的记录，再保存新的
    sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
    sysRoleMenuService.saveBatch(sysRoleMenus);

    // 删除缓存
    sysUserService.clearUserAuthorityInfoByRoleId(roleId);

    return Result.succ(menuIds);
  }
}
