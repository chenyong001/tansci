package com.tansci.service.impl.chatGPT;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Collections2;
import com.tansci.domain.system.ChatGPT;
import com.tansci.domain.system.SysDic;
import com.tansci.domain.system.dto.SysDicDto;
import com.tansci.mapper.chatGPT.ChatGPTMapper;
import com.tansci.service.chatGPT.ChatGPTService;
import com.tansci.service.system.SysDicService;
import com.tansci.service.system.SysUserService;
import com.tansci.utils.CollectionUtil;
import com.tansci.utils.SecurityUserUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author chenyong
 * @Date 2023/2/9 14:50
 * @Version 1.0
 */
@Slf4j
@Service
public class ChatGPTImpl extends ServiceImpl<ChatGPTMapper, ChatGPT> implements ChatGPTService {
  @Autowired
  private SysUserService sysUserService;
  @Autowired
  private SysDicService sysDicService;
  String uri = "https://api.openai.com/v1/engines/text-davinci-003/completions";
  String apiKey;

  @Override
  public String send(String prompt) {
    String result = "";
    try {
      if (StringUtils.isBlank(apiKey)) {
        SysDicDto sysDicDto = new SysDicDto();
        sysDicDto.setKeyword("chat_gpt_apikey");
        List<SysDic> sysDics = sysDicService.dicList(sysDicDto);
        if (CollectionUtil.isEmpty(sysDics)) {
          log.warn("chatGPT apikey is not get!!!");
          return result;
        }
        apiKey = sysDics.get(0).getRemarks();
      }

      HttpClient httpClient = HttpClientBuilder.create().build();
      HttpPost request = new HttpPost(uri);
      request.addHeader("Content-Type", "application/json");
      request.addHeader("Authorization", "Bearer " + apiKey);
      JSONObject requestBody = new JSONObject();
      requestBody.put("prompt", prompt);
      requestBody.put("max_tokens", 1024);
      requestBody.put("temperature", 0.7);
      requestBody.put("n", 1);

      StringEntity requestEntity = new StringEntity(requestBody.toString(), "UTF-8");
      request.setEntity(requestEntity);

      HttpResponse response = httpClient.execute(request);
      String responseString = EntityUtils.toString(response.getEntity());
      JSONObject responseJson = new JSONObject(responseString);
      JSONArray choices = responseJson.getJSONArray("choices");
      result = choices.getJSONObject(0).getString("text");
      result = result.trim();
    } catch (Exception e) {
      log.error("Exception:", e);
    } finally {
      //      添加记录
      ChatGPT chatGPT = new ChatGPT();
      chatGPT.setUserId(SecurityUserUtils.getUser().getId());
      chatGPT.setPrompt(prompt);
      chatGPT.setContent(result);
      chatGPT.setCreateTime(new Date());
      chatGPT.setUpdateTime(new Date());
      save(chatGPT);
      return result;
    }
  }

  @Override
  public IPage<ChatGPT> page(Page page, ChatGPT chatGPT) {

    IPage<ChatGPT> iPage = this.baseMapper.selectPage(page,
        Wrappers.<ChatGPT>lambdaQuery().eq(ChatGPT::getUserId, SecurityUserUtils.getUser().getId())
            .eq(ChatGPT::getStatus, 1)
            //        .eq(StringUtils.isNotBlank(chatGPT.getDocId()), ChatGPT::getDocId, chatGPT.getDocId())
            //        .eq(ChatGPT::getType, chatGPT.getType())
            .orderByDesc(ChatGPT::getCreateTime));

    //    iPage.getRecords().forEach(item -> {
    //      //      遍历查询每个会话的用户信息
    //      SysUser sysUser = sysUserService.selectByUserId(item.getUserId());
    //      item.setUserName(sysUser == null ? "" : sysUser.getUsername());
    //    });
    return iPage;
  }

  @Override
  public boolean save(ChatGPT chatGPT) {
    this.baseMapper.insert(chatGPT);
    return true;
  }

  @Override
  public boolean del(ChatGPT chatGPT) {
    int row = this.baseMapper.deleteById(chatGPT.getId());
    if (row > 0) {
      return true;
    }
    return false;
  }

  @Override
  public List<ChatGPT> selectList(ChatGPT chatGPT) {
    LambdaQueryWrapper<ChatGPT> eq = Wrappers.<ChatGPT>lambdaQuery()
        .eq(ChatGPT::getUserId, SecurityUserUtils.getUser().getId()).eq(ChatGPT::getStatus, 1)
        .orderByDesc(ChatGPT::getCreateTime);
    return this.baseMapper.selectList(eq);
  }

}