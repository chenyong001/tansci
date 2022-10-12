package com.tansci.enums;

/**
 * @Author chenyong
 * @Date 2022/9/19 11:25
 * @Version 1.0
 */

public enum CollectTypeEnum {
//  1:表示微软PPT语音采集
  COLLECT_TYPE_MICROSOFT_PPT(1)
//  2：表示AZURE-API语音采集
  , COLLECT_TYPE_AZURE(2)
  ;

  private Integer type;
  private CollectTypeEnum(Integer type) {
    this.type = type;
  }

  public Integer getType() {
    return type;
  }
}
