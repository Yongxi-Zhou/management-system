package com.demo.manage_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.manage_system.entity.SysMenu;
import com.demo.manage_system.entity.SysRole;
import com.demo.manage_system.entity.SysUser;
import com.demo.manage_system.mapper.SysUserMapper;
import com.demo.manage_system.service.SysMenuService;
import com.demo.manage_system.service.SysRoleService;
import com.demo.manage_system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.manage_system.util.RedisUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yongxi
 * @since 2023-03-27
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

  @Autowired
  SysRoleService sysRoleService;

  @Autowired
  SysUserMapper sysUserMapper;

  @Autowired
  SysMenuService sysMenuService;

  @Autowired
  RedisUtil redisUtil;

  public SysUser getByUsername(String username) {

    return getOne(new QueryWrapper<SysUser>().eq("username", username));
  }

  @Override
  public String getUserAuthorityInfo(Long userId) {
    // ROLE_admin,ROLE_normal,sys:user:list,....
    String authorityInfo = "";
    SysUser sysUser = sysUserMapper.selectById(userId);

    if (redisUtil.hasKey(userId + ":authorityInfo")) {
      authorityInfo = (String) redisUtil.get("GrantedAuthority:" + sysUser.getUsername());
    } else {

      // 获取角色
      List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>().inSql("id",
          "select role_id from sys_user_role where user_id = " + userId));

      if (roles.size() > 0) {
        String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode())
            .collect(Collectors.joining(","));
        authorityInfo = roleCodes.concat(",");
      }
      // 获取菜单操作权限
      List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
      if (menuIds.size() > 0) {
        List<SysMenu> menus = sysMenuService.listByIds(menuIds);
        String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
        authorityInfo = authorityInfo.concat(menuPerms);
      }
      log.info("authorityInfo:{}", authorityInfo);

      redisUtil.set("GrantedAuthority:" + sysUser.getUsername(), authorityInfo, 60 * 60);
    }
    return authorityInfo;
  }

  @Override
  public void clearUserAuthorityInfo(String userName) {
    redisUtil.del("GrantedAuthority:" + userName);
  }

  @Override
  public void clearUserAuthorityInfoByRoleId(Long roleId) {
    // 通过roleId获取与之关联的userId，然后删除与之关联的userName
    List<SysUser>sysUsers = this.list(new QueryWrapper<SysUser>().inSql("id", "select user_id from sys_user_role where role_id = " + roleId));
    sysUsers.forEach(sysUser -> {
      redisUtil.del("GrantedAuthority:" + sysUser.getUsername());
    });
  }

  @Override
  public void clearUserAuthorityInfoByMenuId(Long menuId) {
    List<SysUser> sysUsers = sysUserMapper.listByMenuId(menuId);
    sysUsers.forEach(sysUser -> {
      redisUtil.del("GrantedAuthority:" + sysUser.getUsername());
    });
  }
}
