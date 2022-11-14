package com.tansci.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName： TaskConfig.java
 * @ClassPath： com.tansci.domain.system.TaskConfig.java
 * @Description： 任务配置
 * @Author： tanyp
 * @Date： 2022/2/25 9:35
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "record_stat")
@ApiModel(value = "记录统计表")
/**
 * 查询会话时，查询数据记录量，如果不相同则更新数据记录量、字符量和时间并返回
 */
public class RecordStat {
  @ApiModelProperty(value = "主键id")
  @TableId(type = IdType.AUTO)
  private String id;

  @ApiModelProperty(value = "会话文档ID")
  private String docId;

  @ApiModelProperty(value = "数据记录量，用于判断是否有新的记录，便于是否需要重新统计数据")
  private long recordNum;
  @ApiModelProperty(value = "时长(毫秒)")
  private long duration;

  @ApiModelProperty(value = "字符量")
  private long charactersCount;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "备注")
  private String remark;

}
