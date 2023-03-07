package com.tansci.mapper.chatGPT;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tansci.domain.system.ChatGPT;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 50212
 */
@Mapper
public interface ChatGPTMapper extends BaseMapper<ChatGPT> {
  Page<ChatGPT> page(Page page, @Param("dto") ChatGPT dto);

}
