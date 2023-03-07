package com.tansci.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 50212
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "chat_gpt_data")
@ApiModel(value = "chatGPT数据表")
public class ChatGPT {
  @ApiModelProperty(value = "主键id")
  @TableId(type = IdType.AUTO)
  private String id;
  @ApiModelProperty(value = "用户ID")
  private String userId;
  @ApiModelProperty(value = "用户名称")
  @TableField(exist = false)
  private String userName;
  @ApiModelProperty(value = "提问")
  private String prompt;
  @ApiModelProperty(value = "语音识别的内容")
  private String speechText;
  @ApiModelProperty(value = "内容")
  private String content;
  @ApiModelProperty(value = "默认1正常，0删除")
  private Integer status;
  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "备注")
  private String remark;

  @ApiModelProperty(value = "值IDS")
  @TableField(exist = false)
  private List<Integer> orgIds;
}
