package com.tansci.enums;

/**
 * @Author chenyong
 * @Date 2022/9/19 11:25
 * @Version 1.0
 */

public enum LanguageEnum {
  EN("en")
  , ZH("zh-Hans"), DE("de"), FR("fr"), JA("ja")
  ;

  private String language;

  private LanguageEnum(String language) {
    this.language = language;
  }

  public String getLanguage() {
    return this.language;
  }
}
