package com.demo.manage_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.manage_system.entity.SysUser;
import com.demo.manage_system.mapper.SysUserMapper;
import com.demo.manage_system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yongxi
 * @since 2023-03-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

  public SysUser getByUsername(String username) {

    return getOne(new QueryWrapper<SysUser>().eq("username", username));
  }
}
