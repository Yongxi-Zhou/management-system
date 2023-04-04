package com.demo.manage_system.service;

import com.demo.manage_system.common.dto.SysMenuDto;
import com.demo.manage_system.entity.SysMenu;
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
public interface SysMenuService extends IService<SysMenu> {

  List<SysMenuDto> getCurrentUserNav();

  List<SysMenu> tree();
}
