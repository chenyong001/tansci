package com.tansci.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import javax.annotation.PropertyKey;

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
@TableName(value = "record")
@ApiModel(value = "记录表")
public class Record {
  //    {"messageId":"9826557d-1210-4d7b-ba08-0b1377d8e285","senderId":"8dd607cf-f04a-46d0-8519-12f544c662af","objectId":"finalSubtitles","type":"map","operation":"set","property":"en","value":"{\"timestamp\":\"2022-09-13T02:12:19.5264700Z\",\"slideState\":{\"location\":{\"slideId\":{\"sid\":278,\"cid\":0},\"slideIndex\":0},\"timelineMappings\":[{\"timelineId\":\"0_anim\",\"step\":0,\"sequenceId\":\"\"}]},\"subtitle\":\"Finish.\"}","metadata":null}

  //    id
  //        messageId
  //    senderId
  //        property
  //    subtitle
  //        timestamp

  @ApiModelProperty(value = "主键id")
  @TableId(type = IdType.AUTO)
  private String id;

  @ApiModelProperty(value = "会话文档ID")
  private String docId;

  @ApiModelProperty(value = "用户ID")
  private String userId;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "备注")
  private String remark;


}