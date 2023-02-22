package com.tansci.service.chatGPT;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tansci.domain.system.ChatGPT;

import java.util.List;

/**
 * @Author chenyong
 * @Date 2023/2/9 15:48
 * @Version 1.0
 */
public interface ChatGPTService {
  String send(String prompt);

  IPage<ChatGPT> page(Page page, ChatGPT chatGPT);

  boolean del(ChatGPT chatGPT);

  List<ChatGPT> selectList(ChatGPT chatGPT);
}