package com.demo.manage_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BaseEntity {
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private LocalDateTime created;
  private LocalDateTime updated;

  private Integer statu;
}
