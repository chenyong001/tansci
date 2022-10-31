package com.tansci.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
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
@TableName(value = "record_data")
@ApiModel(value = "记录数据表")
public class RecordData {
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

  @ApiModelProperty(value = "消息ID")
  private String messageId;

  @ApiModelProperty(value = "发送者ID，需要和userId映射")
  private String senderId;

  @ApiModelProperty(value = "属性语言，如英文(en)、中文(zh-Hans)、德语(de)、法语(fr)、日语(ja)")
  private String property;

  @ApiModelProperty(value = "记录内容")
  private String subtitle;

  @ApiModelProperty(value = "时间")
  private Date timestamp;

  @ApiModelProperty(value = "偏移量")
  private String offset;

  @ApiModelProperty(value = "持续时间")
  private String duration;

  public RecordData(String docId, String property) {
    this.docId = docId;
    this.property = property;
  }

}
