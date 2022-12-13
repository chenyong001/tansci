package com.tansci.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.tansci.utils.DateUtil;
import com.tansci.utils.HttpClientUtil;
import com.tansci.utils.SystemUtil;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Author chenyong
 * @Date 2022/9/20 17:30
 * @Version 1.0
 */
@Slf4j
public class TestController {
  public static Map<String, String> dbMap = new ConcurrentHashMap();

  public static void main(String[] args) throws IOException {
//
//    Process proc;
//    try {
//        	/*
//			附加：
//			String[] args1=new String[]{"/home/huan/anaconda2/bin/python","/home/huan/myfile/pythonfile/helloword.py"};
//            Process pr=Runtime.getRuntime().exec(args1);
//			String数组里的那一行很重要
//			首先一定要设置好你所使用的python的位置，切记不要直接使用python，因为系统会默认使用自带的python，所以一定要设置好你所使用的python的位置，否则可能会出现意想不到的问题（比如说我使用的是anaconda中的python，而ubuntu系统会默认调用自带的python，而我自带的python中并没有numpy库，所以会造成相应的代码不会执行的问题，所以设置好python的位置是很重要的）。还有就是要设置好py文件的位置，使用绝对路径。在这里插入代码片
//
//       还有就是可以看出，此方法可以满足我们python代码中调用第三方库的情况，简单实用。
//			*/
////      proc = Runtime.getRuntime().exec("python E://tansci//py//analyseRecord_windows.py  --filePath=E:\\tansci\\py\\4658E7EC4A364AA6BF394F64E7716917_1670309430243_65.txt "
////          + " --num=20 --keywordsList 开始 --stopwordsList 腾讯 生活");
//      String filePath="E:\\tansci\\py\\4658E7EC4A364AA6BF394F64E7716917_1670309430243_65.txt";
//      String num="33";
//      String keywordsList=" 开始";
//      String stopwordsList=" 腾讯 生活";
//      String[] args1 = new String[] { "python", "E:\\tansci\\py/analyseRecord_windows.py", "--filePath=" + filePath,
//          "--num=" + num, "--keywordsList" + keywordsList, "--stopwordsList" + stopwordsList };
//      proc = Runtime.getRuntime().exec(args1);
//      BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//      String line = null;
//      while ((line = in.readLine()) != null) {
//        System.out.println(line);
//      }
//
//      BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
//      String lineerror;
//      while ((lineerror = error.readLine()) != null) {
//        //        {"hello": 1, "h": 1, "ello": 1, "create": 1, "an": 1, "acquisition": 1, "task": 1}
//        System.out.println(lineerror);
//      }
//      error.close();
//      in.close();
//      int i = proc.waitFor();
//      if(i==0){
//        System.out.println("py脚本执行成功");
//      }else {
//        System.out.println("py脚本执行失败");
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }


  }

  private static void httppostTest() throws IOException {
    OkHttpClient client = new OkHttpClient().newBuilder()
        .build();
    MediaType mediaType = MediaType.parse("text/plain");
    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("image1","1651135266387.pdf",
            RequestBody.create(MediaType.parse("application/octet-stream"),
                new File("D:\\test\\商品房买卖合同--面积补充协议.pdf")))
        .addFormDataPart("image2","1651135266387.pdf",
            RequestBody.create(MediaType.parse("application/octet-stream"),
                new File("D:\\test\\大连乾进物流协议扫描（2017）.pdf")))
        .build();
    Request request = new Request.Builder()
        .url("http://172.16.0.19:51000/compare")
        .method("POST", body)
        .build();
    Response response = client.newCall(request).execute();
    System.out.println(JSON.toJSONString(response));
    System.out.println(response.code());
    FileUtils.writeByteArrayToFile(new File("D:\\test\\t1.png"),response.body().bytes());
  }

  private static void exportSrt() throws IOException {
    String srtFilePath = "test1.srt";
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < 20; i++) {
      String startTime = DateUtil.date2Str(new Date(), DateUtil.FORMAT_HH_MM_SS_SSS);
      String endTime = DateUtil.date2Str(new Date(), DateUtil.FORMAT_HH_MM_SS_SSS);
      String msg = "srt_" + i;
      sb.append(i + 1).append("\n").append(startTime).append(" --> ").append(endTime).append("\n").append(msg)
          .append("\n").append("\n");
    }
    BufferedWriter out = new BufferedWriter(new FileWriter(srtFilePath));
    out.write(sb.toString());
    out.flush();
    out.close();
  }

  /**
   * json转String
   *
   * @param obj
   * @return
   */
  private static String json2Str(Object obj) {
    return Objects.isNull(obj) ? "" : String.valueOf(obj);
  }
}
