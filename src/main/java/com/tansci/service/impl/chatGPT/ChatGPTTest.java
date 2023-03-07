package com.tansci.service.impl.chatGPT;

/**
 * @Author chenyong
 * @Date 2023/2/15 11:33
 * @Version 1.0
 */

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ChatGPTTest {
  public static void main(String[] args) throws Exception {
//    String result;
//    HttpClient httpClient = HttpClientBuilder.create().build();
//    //      text-davinci-003 的参数
//    //      String uri = "https://api.openai.com/v1/engines/text-davinci-003/completions";
//    //      HttpPost request = new HttpPost(uri);
//    //      request.addHeader("Content-Type", "application/json");
//    //      request.addHeader("Authorization", "Bearer " + apiKey);
//    //      JSONObject requestBody = new JSONObject();
//    //      requestBody.put("prompt", prompt);
//    //      requestBody.put("max_tokens", 1024);
//    //      requestBody.put("temperature", 0.7);
//    //      requestBody.put("n", 1);
//
//    //      gpt-3.5-turbo的参数
//    String uri = "https://api.openai.com/v1/chat/completions";
//    HttpPost request = new HttpPost(uri);
//    request.addHeader("Content-Type", "application/json");
//    request.addHeader("Authorization", "Bearer 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");
//
//    //      String messages="[{\"role\":\"user\", \"content\": \"Translate the following English text to French \"}]";
//    //    com.alibaba.fastjson.JSONArray messages=new com.alibaba.fastjson.JSONArray();
//    //    com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
//    //    jsonObject.put("role","user");
//    //    jsonObject.put("content","Translate the following English text to Chines");
//    //    messages.add(jsonObject);
//    List<ChatGPTImpl.ChatGPTMessage> messages = new ArrayList<>();
//    messages.add(new ChatGPTImpl.ChatGPTMessage("user", "hello"));
//    Map<String, String> headerParams = Maps.newHashMap();
//    headerParams.put("Content-Type", "application/json");
//    headerParams.put("Authorization", "Bearer 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");
//    Map<String, Object> bodyParams = Maps.newHashMap();
//    bodyParams.put("model", "gpt-3.5-turbo");
//    bodyParams.put("messages", messages);
//    String responseString = HttpClientUtil.sendPostRequest2(uri, headerParams, bodyParams);
//    JSONObject responseJson = new JSONObject(responseString);
//    JSONArray choices = responseJson.getJSONArray("choices");
//    JSONObject message = choices.getJSONObject(0).getJSONObject("message");
//    result = message.getString("content");
//    result = result.trim();
   chatgpt3();

  }

  private static void chatgpt3() throws JSONException, IOException {
    String apiKey = "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";
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost request = new HttpPost("https://api.openai.com/v1/engines/text-davinci-003/completions");
    request.addHeader("Content-Type", "application/json");
    request.addHeader("Authorization", "Bearer " + apiKey);

    JSONObject requestBody = new JSONObject();
    String prompt = "今天是什么日子？";
    requestBody.put("prompt", prompt);
    requestBody.put("max_tokens", 500);
    requestBody.put("temperature", 0.7);
    requestBody.put("n", 1);
    //    requestBody.put("model", "ada");
    //    model="text-davinci-003"

    StringEntity requestEntity = new StringEntity(requestBody.toString(), "UTF-8");
    request.setEntity(requestEntity);

    HttpResponse response = httpClient.execute(request);
    String responseString = EntityUtils.toString(response.getEntity());
    JSONObject responseJson = new JSONObject(responseString);
    JSONArray choices = responseJson.getJSONArray("choices");
    String text = choices.getJSONObject(0).getString("text");
    System.out.println(text);
    //    System.out.println("Response: " + responseJson.getString("choices").split("\n")[0]);
  }
}

