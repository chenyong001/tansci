package com.tansci.enums;

/**
 * @Author chenyong
 * @Date 2022/9/19 11:25
 * @Version 1.0
 */

public enum QuestionTypeEnum {
//  1:单选题
SINGLE_CHOICE("0")
//  2：多选题
  , MULTIPLE_CHOICE("1")
//  判断题
  , TF_CHOICE("2")
//  简答题
  , SHORT_ANSWER_CHOICE("3")
  ;

  private String type;
  private QuestionTypeEnum(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
