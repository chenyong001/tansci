package com.tansci.service.impl.chatGPT;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.tansci.domain.system.ChatGPT;
import com.tansci.domain.system.SysDic;
import com.tansci.domain.system.SysUser;
import com.tansci.domain.system.dto.SysDicDto;
import com.tansci.mapper.chatGPT.ChatGPTMapper;
import com.tansci.service.chatGPT.ChatGPTService;
import com.tansci.service.system.SysDicService;
import com.tansci.service.system.SysUserService;
import com.tansci.utils.CollectionUtil;
import com.tansci.utils.HttpClientUtil;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Data;
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
  String azureApiKey;
  @Override
  public String send(String prompt, String speechText) {
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
      if (StringUtils.isNotBlank(speechText)) {
        chatGPT.setSpeechText(speechText);
      }
      chatGPT.setContent(result);
      chatGPT.setCreateTime(new Date());
      chatGPT.setUpdateTime(new Date());
      save(chatGPT);
      return result;
    }
  }

  @Override
  public String send2OpenAi(String prompt, String speechText) {
    String result = "";
    String uri = "https://api.openai.com/v1/chat/completions";
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

      Map<String, String> headerParams = Maps.newHashMap();
      headerParams.put("Content-Type", "application/json");
      headerParams.put("Authorization", "Bearer " + apiKey);
      Map<String, Object> bodyParams = Maps.newHashMap();
      bodyParams.put("model", "gpt-3.5-turbo");
      List<ChatGPTImpl.ChatGPTMessage> messages = new ArrayList<>();
      messages.add(new ChatGPTImpl.ChatGPTMessage("system", "You are a helpful assistant."));
      messages.add(new ChatGPTImpl.ChatGPTMessage("user", prompt));
      bodyParams.put("messages", messages);
      bodyParams.put("max_tokens", 1024);
      bodyParams.put("n", 1);
      String responseString = HttpClientUtil.sendPostRequest2(uri, headerParams, bodyParams);

      JSONObject responseJson = new JSONObject(responseString);
      JSONArray choices = responseJson.getJSONArray("choices");
      JSONObject message = choices.getJSONObject(0).getJSONObject("message");
      result = message.getString("content");
      result = result.trim();

    } catch (Exception e) {
      log.error("Exception:", e);
    } finally {
      //      添加记录
      ChatGPT chatGPT = new ChatGPT();
      chatGPT.setUserId(SecurityUserUtils.getUser().getId());
      chatGPT.setPrompt(prompt);
      if (StringUtils.isNotBlank(speechText)) {
        chatGPT.setSpeechText(speechText);
      }
      chatGPT.setContent(result);
      chatGPT.setCreateTime(new Date());
      chatGPT.setUpdateTime(new Date());
      save(chatGPT);
      return result;
    }
  }

  @Override
  public String send2Azure(String prompt, String speechText) {
    String result = "";
    String uri = "https://tsigpt.openai.azure.com/openai/deployments/tsigpt4/chat/completions?api-version=2023-03-15-preview";
    try {
      if (StringUtils.isBlank(azureApiKey)) {
        SysDicDto sysDicDto = new SysDicDto();
        sysDicDto.setKeyword("chat_gpt_azure_apikey");
        List<SysDic> sysDics = sysDicService.dicList(sysDicDto);
        if (CollectionUtil.isEmpty(sysDics)) {
          log.warn("chatGPT apikey is not get!!!");
          return result;
        }
        azureApiKey = sysDics.get(0).getRemarks();
      }

      Map<String, String> headerParams = Maps.newHashMap();
      headerParams.put("Content-Type", "application/json");
      headerParams.put("api-key", azureApiKey);
      Map<String, Object> bodyParams = Maps.newHashMap();
//      bodyParams.put("model", "gpt-3.5-turbo");
      List<ChatGPTImpl.ChatGPTMessage> messages = new ArrayList<>();
      messages.add(new ChatGPTImpl.ChatGPTMessage("system", "You are a helpful assistant."));
      messages.add(new ChatGPTImpl.ChatGPTMessage("user", prompt));
      bodyParams.put("messages", messages);
      bodyParams.put("max_tokens", 1024);
      bodyParams.put("temperature", 0);
      String responseString = HttpClientUtil.sendPostRequest2(uri, headerParams, bodyParams);

      JSONObject responseJson = new JSONObject(responseString);
      JSONArray choices = responseJson.getJSONArray("choices");
      JSONObject message = choices.getJSONObject(0).getJSONObject("message");
      result = message.getString("content");
      result = result.trim();

    } catch (Exception e) {
      log.error("Exception:", e);
    } finally {
      //      添加记录
      ChatGPT chatGPT = new ChatGPT();
      chatGPT.setUserId(SecurityUserUtils.getUser().getId());
      chatGPT.setPrompt(prompt);
      if (StringUtils.isNotBlank(speechText)) {
        chatGPT.setSpeechText(speechText);
      }
      chatGPT.setContent(result);
      chatGPT.setCreateTime(new Date());
      chatGPT.setUpdateTime(new Date());
      save(chatGPT);
      return result;
    }
  }
  @Override
  public IPage<ChatGPT> page(Page page, ChatGPT chatGPT) {

    SysUser user = SecurityUserUtils.getUser();
    if (!Objects.equals(0, user.getType())) {
      chatGPT.setOrgIds(user.getOrgIds());
    }
    if (Objects.equals(user.getType(), 2)) {
      //      如果类型是普通用户，才通过账号ID过滤
      chatGPT.setUserId(user.getId());
    }

    IPage<ChatGPT> iPage = this.baseMapper.page(page, chatGPT);
    //    IPage<ChatGPT> iPage = this.baseMapper.selectPage(page,
    //        Wrappers.<ChatGPT>lambdaQuery().eq(ChatGPT::getUserId, SecurityUserUtils.getUser().getId())
    //            .eq(ChatGPT::getStatus, 1)
    //            .orderByDesc(ChatGPT::getCreateTime));

    iPage.getRecords().forEach(item -> {
      //      遍历查询每个会话的用户信息
      SysUser sysUser = sysUserService.selectByUserId(item.getUserId());
      item.setUserName(sysUser == null ? "" : sysUser.getUsername());
    });
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

  @Data
  static class ChatGPTMessage implements Serializable {
    private static final long serialVersionUID = -8593133888176767391L;
    String role;
    String content;

    public ChatGPTMessage() {
    }

    public ChatGPTMessage(String role, String content) {
      this.role = role;
      this.content = content;
    }
  }

}
