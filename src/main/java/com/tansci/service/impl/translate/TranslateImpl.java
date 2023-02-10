package com.tansci.service.impl.translate;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tansci.service.translate.TranslateService;

import org.springframework.stereotype.Service;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Author chenyong
 * @Date 2023/2/9 14:50
 * @Version 1.0
 */
@Service
public class TranslateImpl implements TranslateService {
  /*  Configure the local environment:
   * Set the TRANSLATOR_TEXT_RESOURCE_KEY,TRANSLATOR_TEXT_REGION and TRANSLATOR_TEXT_ENDPOINT environment
   * variables on your local machine using the appropriate method for your
   * preferred shell (Bash, PowerShell, Command Prompt, etc.).
   *
   * For TRANSLATOR_TEXT_ENDPOINT, use the same region you used to get your
   * resource keys.
   *
   * If the environment variable is created after the application is launched
   * in a console or with Visual Studio, the shell (or Visual Studio) needs to be
   * closed and reloaded to take the environment variable into account.
   */
//  private static String resourceKey = System.getenv("TRANSLATOR_TEXT_RESOURCE_KEY");
//  private static String region = System.getenv("TRANSLATOR_TEXT_REGION");
//  private static String endpoint = System.getenv("TRANSLATOR_TEXT_ENDPOINT");
//  String url = endpoint + "/translate?api-version=3.0&to=de,it";
  private static String resourceKey = "eb4fbff9b53047ed9a7fc2e8530f1776";
  private static String region = "eastasia";
//  文本翻译
  private static String endpoint = "https://api.cognitive.microsofttranslator.com/";
//  文档翻译
//  private static String endpoint = "https://mytranslatef0.cognitiveservices.azure.com/";


  // Instantiates the OkHttpClient.
  OkHttpClient client = new OkHttpClient();

  @Override
  public String translate(String srcLanguage,String targetLanguage, String content){
    String result;
    try {
      String response =  translatePost(srcLanguage,targetLanguage,content);
      System.out.println(prettify(response));
      result=prettify(response);
    } catch (IOException e) {
      e.printStackTrace();
      result="error";
    }
    return result;
  }

  // This function performs a POST request.
  public String translatePost(String srcLanguage,String targetLanguage,String content) throws IOException {
    String url = endpoint + "/translate?api-version=3.0&to="+targetLanguage;
    if(StringUtils.isNotBlank(srcLanguage)){
      url+="&from="+srcLanguage;
    }
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(mediaType,
        "[{\"Text\": \""+content+"\"}]");
    Request request = new Request.Builder()
        .url(url).post(body)
        .addHeader("Ocp-Apim-Subscription-Key", resourceKey)
        .addHeader("Ocp-Apim-Subscription-Region", region)
        .addHeader("Content-type", "application/json").build();
    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  // This function prettifies the json response.
  public static String prettify(String json_text) {
    JsonParser parser = new JsonParser();
    JsonElement json = parser.parse(json_text);
    JsonArray asJsonArray = json.getAsJsonArray();
    JsonElement translations = asJsonArray.get(0).getAsJsonObject().get("translations");
    JsonArray asJsonArray1 = translations.getAsJsonArray();
    String text = asJsonArray1.get(0).getAsJsonObject().get("text").getAsString();

//    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    return gson.toJson(json);
    return text;
  }
//  public static void main(String[] args) {
//    try {
//      TranslateImpl translateRequest = new TranslateImpl();
//      String response = translateRequest.translatePost();
//      System.out.println(prettify(response));
//    } catch (Exception e) {
//      System.out.println(e);
//    }
//  }

//  [
//  {
//    "detectedLanguage": {
//    "language": "en",
//        "score": 1.0
//  },
//    "translations": [
//    {
//      "text": "欢迎来到微软翻译。猜猜我说多少种语言！",
//        "to": "zh-Hans"
//    }
//    ]
//  }
//]
}
