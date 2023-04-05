package com.demo.manage_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yongxi
 * @since 2023-03-27
 */
@Data
public class SysUserRole {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;


}
