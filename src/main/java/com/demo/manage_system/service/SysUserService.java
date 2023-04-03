package com.demo.manage_system.service;

import com.demo.manage_system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yongxi
 * @since 2023-03-27
 */
public interface SysUserService extends IService<SysUser> {

  SysUser getByUsername(String username);

  String getUserAuthorityInfo(Long userId);

  void clearUserAuthorityInfo(String userName);

  void clearUserAuthorityInfoByRoleId(Long roleId);

  void clearUserAuthorityInfoByMenuId(Long menuId);
}
