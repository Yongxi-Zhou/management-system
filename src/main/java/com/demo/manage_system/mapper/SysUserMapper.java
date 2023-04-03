package com.demo.manage_system.mapper;

import com.demo.manage_system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yongxi
 * @since 2023-03-27
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


  /**
   * @param userId
   * @return
   */
  List<Long> getNavMenuIds(Long userId);

  List<SysUser> listByMenuId(Long menuId);
}
