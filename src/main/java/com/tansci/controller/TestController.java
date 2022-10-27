package com.tansci.controller;

import com.alibaba.fastjson.JSONArray;
import com.tansci.utils.DateUtil;
import com.tansci.utils.SystemUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author chenyong
 * @Date 2022/9/20 17:30
 * @Version 1.0
 */
@Slf4j
public class TestController {
  public static Map<String, String> dbMap = new ConcurrentHashMap();

  public static void main(String[] args) throws IOException {

    System.out.println((int) (Math.random()*100));


//    System.out.println(Date.before(new Date()));
//    System.out.println(System.getProperty("os.name"));
//    System.out.println(SystemUtil.getOsName());
//    System.out.println(SystemUtil.isLinux());
//
//    String str = "[{\"docId\":\"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2\",\"subtitle\":\"Hello。 H22ello22。我爱你，时间\",\"property\":\"en\",\"userId\":\"bc3ac26e69731b617eb80274453f6dba\",\"timestamp\":\"2022-09-23 15:57:15\"},{\"docId\":\"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2\",\"subtitle\":\"Create an acquisition task.\",\"property\":\"en\",\"userId\":\"bc3ac26e69731b617eb80274453f6dba\",\"timestamp\":\"2022-09-23 15:57:27\"}]";
//        //    PyList pyList = new PyList();
//        JSONArray jsonArray = JSONArray.parseArray(str);
//    System.out.println(jsonArray.toJSONString());

//    https://www.likecs.com/show-204036559.html?sc=100
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
//
//       "E:\\tansci\\src\\main\\java\\com\\tansci\\controller/test_def.py"
//			*/
//
//      String jsonStr=jsonArray.toJSONString();
//      jsonStr=jsonStr.replace("\"","\'");
////      System.out.println(jsonStr);
////      wordcloud
////      "C:\\Python310/python.exe"
//      String[] args1 = new String[] { "python", "E:\\tansci\\src\\main\\java\\com\\tansci\\controller/analyseRecord.py", "--param1="+jsonStr };
//
//      proc = Runtime.getRuntime().exec(args1);
//      BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//      String line = null;
//      while ((line = in.readLine()) != null) {
////        {"hello": 1, "h": 1, "ello": 1, "create": 1, "an": 1, "acquisition": 1, "task": 1}
//        System.out.println(line);
//      }
//      in.close();
//      proc.waitFor();
//    } catch (IOException e) {
//      e.printStackTrace();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }


    //    String region = "eastasia";
    //    String subscriptionKey = "de4e9ac5168843c2b74a9b1477376668";
    //    //    发送请求
    //    String url = "https://"+region+".api.cognitive.microsoft.com/sts/v1.0/issueToken";
    //    Map<String, String> headerParams= Maps.newHashMap();
    //    headerParams.put("Content-Type","application/json");
    //    headerParams.put("Ocp-Apim-Subscription-Key",subscriptionKey);
    //    Map<String, String> bodyParams= Maps.newHashMap();
    //    String result = HttpClientUtil.sendPostRequest(url, headerParams, bodyParams);
    //    log.info("==================getAzureToken={}",result);
    //    exportSrt();

    //
    //    long stime=2500000L;
    //    Date date = new Date(stime);
    //    System.out.println(date);
    //    2500000/1000
    ////    6100000
    //
    //    System.out.println(OffsetTime.now().getOffset());
    //        try {
    //      System.out.println("start");
    //      String para1="time";
    //      String para2="sfdjk";
    //      String[] args1 = new String[]{"/home/huan/anaconda2/bin/python", "/home/huan/myfile/pythonfile/helloword.py",para1,para2};
    //      Process pr = Runtime.getRuntime().exec(args1);
    //
    //      BufferedReader in = new BufferedReader(new InputStreamReader(
    //          pr.getInputStream()));
    //      String line;
    //      while ((line = in.readLine()) != null) {
    //        System.out.println(line);
    //      }
    //      in.close();
    //      pr.waitFor();
    //      System.out.println("end");
    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    }

    //    Map<String, Object> binding = new HashMap<>();
    //    binding.put("name", "zhangshan");
    //    binding.put("age", "18");
    //    try (PythonInterpreter interpreter = new PythonInterpreter(Py.java2py(binding))) {
    //      interpreter.exec("varMap = globals()");
    //      interpreter.exec("varMap.put('name', 'ls')");
    //      interpreter.exec("varMap.put('sex', '1')");
    //      PyObject vars = interpreter.get("varMap");
    //      System.out.println(vars.toString());
    //    }

//    Properties props = new Properties();
//
//    //    props.put("python.home", "C:\\Python310/python.exe");
//    //
//        props.put("python.console.encoding", "UTF-8");
//    //
//        props.put("python.security.respectJavaAccessibility", "false");
//
//    props.put("python.import.site", "false");
//
//    Properties preprops = System.getProperties();
//
//    PythonInterpreter.initialize(preprops, props, new String[0]);
//    //
    //    PythonInterpreter interpreter = new PythonInterpreter();
    //
    //    interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun'); ");
    //
    //    interpreter.exec("print days[1];");

    //    PythonInterpreter interpreter = new PythonInterpreter();
    //
    //    interpreter.execfile("E:\\tansci\\src\\main\\java\\com\\tansci\\controller/test_def.py");
    //
    //    PyFunction func = (PyFunction) interpreter.get("add",
    //
    //        PyFunction.class);
    //
    //    int a = 100, b = 100;
    //
    //    PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
    //
    //
    //    System.out.println("anwser = " + pyobj.toString());

//    PythonInterpreter interpreter = new PythonInterpreter();
//
//    interpreter.execfile("E:\\tansci\\src\\main\\java\\com\\tansci\\controller/test.py");
//
//    PyFunction func = (PyFunction) interpreter.get("get_noun",
//
//        PyFunction.class);
//    String str = "[{\n" + " \"docId\": \"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2\",\n"
//        + " \"userId\": \"bc3ac26e69731b617eb80274453f6dba\",\n" + " \"property\": \"en\",\n"
//        + " \"subtitle\": \"Hello。 Hello。\",\n" + " \"timestamp\": \"2022-09-23 15:57:15\"\n" + "}, {\n"
//        + " \"docId\": \"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2\",\n"
//        + " \"userId\": \"bc3ac26e69731b617eb80274453f6dba\",\n" + " \"property\": \"en\",\n"
//        + " \"subtitle\": \"Create an acquisition task.\",\n" + " \"timestamp\": \"2022-09-23 15:57:27\"\n" + "}]";
//    //    PyList pyList = new PyList();
//    JSONArray jsonArray = JSONArray.parseArray(str);
//    PyArray pyArray = new PyArray(JSONArray.class, jsonArray);
//
//    //    pyList.add()
//
//    //    int a = 100, b = 100;
//
//    PyObject pyobj = func.__call__(pyArray);
//
//    System.out.println("anwser = " + pyobj.toString());

    //    PythonInterpreter interpreter = new PythonInterpreter();
    //    interpreter.execfile("./test_def.py");
    //
    //    // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
    //    PyFunction pyFunction = interpreter.get("add", PyFunction.class);
    //    int a = 5, b = 10;
    //    //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
    //    PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
    //    System.out.println("the anwser is: " + pyobj);

    //    String dataStr="2022-09-23T07:04:46.3735361Z";
    //    Date date = DateUtil.str2Date(dataStr, DateUtil.FORMAT_YYYYMMDDTHHMMSS);
    //    Calendar cal = Calendar.getInstance();
    //     cal.setTime(date);
    //     cal.add(Calendar.HOUR_OF_DAY, 8);
    //     // adds one hour cal.getTime(); // returns new date object, one hour in the future
    //    Date time = cal.getTime();
    //    System.out.println();

    //    String result = HttpClientUtil.sendGetRequest(createZoomMeetingUrl, new HashMap(), new HashMap());

    //    String result = "[{\"messageId\":\"e97d1669-5f04-483b-80a7-bdbd677ac2f0\",\"senderId\":null,\"objectId\":\"finalSubtitles\",\"type\":\"map\",\"operation\":null,\"property\":\"en\",\"value\":\"{\\\"timestamp\\\":\\\"2022-09-20T07:02:13.4690538Z\\\",\\\"slideState\\\":{\\\"location\\\":{\\\"slideId\\\":{\\\"sid\\\":278,\\\"cid\\\":0},\\\"slideIndex\\\":0},\\\"timelineMappings\\\":null},\\\"subtitle\\\":\\\"Hello Hello。\\\"}\",\"metadata\":{\"timestamp\":\"09/20/2022 07:02:13\"}},{\"messageId\":\"684e4fe2-cd77-48e6-871c-ab0950aab851\",\"senderId\":null,\"objectId\":\"finalSubtitles\",\"type\":\"map\",\"operation\":null,\"property\":\"en\",\"value\":\"{\\\"timestamp\\\":\\\"2022-09-20T07:02:50.0974831Z\\\",\\\"slideState\\\":{\\\"location\\\":{\\\"slideId\\\":{\\\"sid\\\":278,\\\"cid\\\":0},\\\"slideIndex\\\":0},\\\"timelineMappings\\\":null},\\\"subtitle\\\":\\\"That's right.\\\"}\",\"metadata\":{\"timestamp\":\"09/20/2022 07:02:50\"}},{\"messageId\":\"519fa533-d0ca-4271-a51e-cd7716088b00\",\"senderId\":\"1c122e24-ba5b-4e6c-b334-fca8378c626b\",\"objectId\":\"finalSubtitles\",\"type\":\"map\",\"operation\":\"set\",\"property\":\"en\",\"value\":\"{\\\"timestamp\\\":\\\"2022-09-20T07:02:13.4690538Z\\\",\\\"slideState\\\":{\\\"location\\\":{\\\"slideId\\\":{\\\"sid\\\":278,\\\"cid\\\":0},\\\"slideIndex\\\":0},\\\"timelineMappings\\\":null},\\\"subtitle\\\":\\\"Hello Hello。\\\"}\",\"metadata\":null}]";
    //    JSONArray jsonArray = JSONArray.parseArray(result);
    //    int size = jsonArray.size();
    //    //    JSONArray docMsg = JSONArray.parseArray(dbMap.get(key));
    //    //    int docSize = docMsg != null ? docMsg.size() : 0;
    //    int docSize = 0;
    //    //    log.info("任务={},消息数量={}", docId, size);
    //    //       新增消息
    //    int newIncreased = size - docSize;
    //    if (size != 0 && newIncreased > 0) {
    //      //      dbMap.put(key, result);
    //      List<RecordData> recordDataList = Lists.newArrayList();
    //      RecordData recordData;
    //      for (int i = docSize; i < newIncreased; i++) {
    //        recordData = new RecordData();
    //        recordData.setDocId("docid");
    //        JSONObject jsonObject = jsonArray.getJSONObject(i);
    //        recordData.setMessageId(json2Str(jsonObject.get("messageId")));
    //        if (Objects.isNull(jsonObject.get("senderId"))) {
    //          continue;
    //        } else {
    //          recordData.setSenderId(json2Str(jsonObject.get("senderId")));
    //        }
    //        jsonObject.get("property");
    //        recordData.setProperty(json2Str(jsonObject.get("property")));
    //
    //        JSONObject subJsonObject = JSON.parseObject(String.valueOf(jsonObject.get("value")));
    //        subJsonObject.get("subtitle");
    //        recordData.setSubtitle(json2Str(subJsonObject.get("subtitle")));
    //        subJsonObject.get("timestamp");
    //
    //        recordData.setTimestamp(DateUtil.str2Date(json2Str(subJsonObject.get("timestamp")), DateUtil.FORMAT_YYYYMMDDTHHMMSS));
    //        recordDataList.add(recordData);

    //      }
    //    }

    //    String dateStr = "2022-09-20T07:02:13.4690538Z";
    //
    //    Date date = DateUtil.str2Date(dateStr, DateUtil.FORMAT_YYYYMMDDTHHMMSS);
    //    //    LocalDateTime localDateTime = DateUtil.string2LocalDateTime(dateStr, DateUtil.FORMATTER_YYYY_MM_T_DD_HH_MM_SS);
    //    System.out.println();

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
