package com.demo.manage_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.manage_system.entity.SysRole;
import com.demo.manage_system.mapper.SysRoleMapper;
import com.demo.manage_system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

  @Override
  public List<SysRole> listRolesByUserId(Long id) {
    return this.list(new QueryWrapper<SysRole>().inSql("id", "select role_id from sys_user_role where user_id = " + id));
  }
}
