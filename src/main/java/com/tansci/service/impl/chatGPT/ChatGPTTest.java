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
import org.json.JSONObject;

public class ChatGPTTest {
  public static void main(String[] args) throws Exception {
    String apiKey="";
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost request = new HttpPost("https://api.openai.com/v1/engines/text-davinci-003/completions");
    request.addHeader("Content-Type", "application/json");
    request.addHeader("Authorization", "Bearer "+apiKey);

    JSONObject requestBody = new JSONObject();
    String prompt="今天是什么日子？";
    requestBody.put("prompt", prompt);
    requestBody.put("max_tokens", 500);
    requestBody.put("temperature", 0.7);
    requestBody.put("n", 1);
//    requestBody.put("model", "ada");
//    model="text-davinci-003"

    StringEntity requestEntity = new StringEntity(requestBody.toString(),"UTF-8");
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

