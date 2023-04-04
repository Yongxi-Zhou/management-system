package com.demo.manage_system.service.impl;

import cn.hutool.json.JSONUtil;
import com.demo.manage_system.common.dto.SysMenuDto;
import com.demo.manage_system.entity.SysMenu;
import com.demo.manage_system.entity.SysUser;
import com.demo.manage_system.mapper.SysMenuMapper;
import com.demo.manage_system.mapper.SysUserMapper;
import com.demo.manage_system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.manage_system.service.SysUserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
  

  @Autowired
  SysUserService sysUserService;

  @Autowired
  SysUserMapper sysUserMapper;

  @Override
  public List<SysMenuDto> getCurrentUserNav() {
    String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    SysUser sysUser = sysUserService.getByUsername(username);

    List<Long> menuIds = sysUserMapper.getNavMenuIds(sysUser.getId());
    List<SysMenu> menus = this.listByIds(menuIds);

    // 转树状结构
    List<SysMenu> menuTree = buildTreeMenu(menus);

    // 实体转DTO
    return convert(menuTree);
  }

  private List<SysMenuDto> convert(List<SysMenu> menuTree) {
    List<SysMenuDto> menuDtos = new ArrayList<>();

    menuTree.forEach(m -> {
      SysMenuDto dto = new SysMenuDto();

      dto.setId(m.getId());
      dto.setName(m.getPerms());
      dto.setTitle(m.getName());
      dto.setComponent(m.getComponent());
      dto.setPath(m.getPath());

      if (m.getChildren().size() > 0) {

        // 子节点调用当前方法进行再次转换
        dto.setChildren(convert(m.getChildren()));
      }

      menuDtos.add(dto);
    });

    return menuDtos;


  }

  private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {
    List<SysMenu> finalMenus = new ArrayList<>();
    // 先各自寻找到各自的孩子
    for (SysMenu menu : menus) {

      for (SysMenu e : menus) {
        if (menu.getId() == e.getParentId()) {
          menu.getChildren().add(e);
        }
      }

      // 提取出父节点
      if (menu.getParentId() == 0L) {
        finalMenus.add(menu);
      }
    }

    System.out.println(JSONUtil.toJsonStr(finalMenus));
    return finalMenus;
  }
}
