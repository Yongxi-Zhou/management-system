package com.demo.manage_system.service;

import com.demo.manage_system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yongxi
 * @since 2023-03-27
 */
public interface SysRoleService extends IService<SysRole> {

  List<SysRole> listRolesByUserId(Long id);
}
