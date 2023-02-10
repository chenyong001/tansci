package com.tansci.controller.translate;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tansci.common.WrapMapper;
import com.tansci.common.Wrapper;
import com.tansci.domain.system.RecordParam;
import com.tansci.service.translate.TranslateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author chenyong
 * @Date 2023/2/9 15:50
 * @Version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/translate")
public class TranslateController {
  @Autowired
  private TranslateService translateService;

  @PostMapping("/text")
  @ResponseBody
  public Wrapper<String> text(String targetLanguage, String content,String srcLanguage) {
    log.info("text===========,srcLanguage={},targetLanguage={},content={}", srcLanguage,targetLanguage, content);
    if (StringUtils.isBlank(targetLanguage)||StringUtils.isBlank(content)) {
      //      如果数据为空，则不处理，直接返回
      return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
    }
    String translate = translateService.translate(srcLanguage,targetLanguage, content);

    //    recordParamService.del(recordParam);
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, translate);
  }

}
