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
 *
 * @author 50212
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "record_param")
@ApiModel(value = "记录参数表")
public class RecordParam {

  @ApiModelProperty(value = "主键id")
  @TableId(type = IdType.AUTO)
  private int id;

  @ApiModelProperty(value = "会话文档ID")
  private String docId;

  @ApiModelProperty(value = "0=停用词，1=关键词，2=参数")
  private int type;

  @ApiModelProperty(value = "参数名")
  private String name;

  @ApiModelProperty(value = "参数值")
  private String value;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "备注")
  private String remark;

}
