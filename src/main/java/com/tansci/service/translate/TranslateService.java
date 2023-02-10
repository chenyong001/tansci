package com.tansci.service.translate;

/**
 * @Author chenyong
 * @Date 2023/2/9 15:48
 * @Version 1.0
 */
public interface TranslateService {
  String translate(String srcLanguage,String targetLanguage, String content);
}
