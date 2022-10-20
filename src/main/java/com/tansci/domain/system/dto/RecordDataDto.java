package com.tansci.domain.system.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author chenyong
 * @Date 2022/10/20 10:42
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel(value = "任务配置DTO")
public class RecordDataDto {
  private String docId;
  private String subtitle;
}
