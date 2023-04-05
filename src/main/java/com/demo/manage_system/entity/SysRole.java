package com.demo.manage_system.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
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
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    @NotBlank(message = "角色描述不能为空")
    private String code;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private List<Long> menuIds = new ArrayList<>();
}