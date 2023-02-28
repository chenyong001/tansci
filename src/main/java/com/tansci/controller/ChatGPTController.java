package com.tansci.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tansci.common.WrapMapper;
import com.tansci.common.Wrapper;
import com.tansci.common.annotation.Log;
import com.tansci.common.constant.Constants;
import com.tansci.domain.system.ChatGPT;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.service.chatGPT.ChatGPTService;
import com.tansci.utils.ExportUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author chenyong
 * @Date 2023/2/16 10:28
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("chatGPT")
public class ChatGPTController {

  @Autowired
  private ChatGPTService chatGPTService;

  @PostMapping("/send")
  @ResponseBody
  public Wrapper<String> send(String prompt,String speechText) {
    log.info("send====ChatGPT=======,prompt={},speechText={}", prompt,speechText);
    if (StringUtils.isBlank(prompt)) {
      //      如果数据为空，则不处理，直接返回
      return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
    }
    String result = chatGPTService.send(prompt,speechText);
    //    String translate = translateService.translate(srcLanguage,targetLanguage, content);

    //    recordParamService.del(recordParam);
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
  }

  @ApiOperation(value = "chatGPT列表", notes = "chatGPT列表")
  @Log(modul = "列表", type = Constants.SELECT, desc = "列表")
  @ResponseBody
  @GetMapping("/page")
  public Wrapper<IPage<ChatGPT>> page(Page page, ChatGPT chatGPT) {
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, chatGPTService.page(page, chatGPT));
  }

  @ApiOperation(value = "导出列表", notes = "导出列表")
  @Log(modul = "chatGPT-导出列表", type = Constants.SELECT, desc = "导出列表")
  @GetMapping("/exportChatGPTTxt")
  public void exportChatGPTTxt(ChatGPT chatGPT, HttpServletResponse response) {
    List<ChatGPT> chatGPTList = chatGPTService.selectList(chatGPT);
    ExportUtil.exportChatGPTTxt(response, chatGPTList);
  }

}
