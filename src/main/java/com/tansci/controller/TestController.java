package com.tansci.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import com.tansci.domain.system.Record;
import com.tansci.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author chenyong
 * @Date 2022/9/20 17:30
 * @Version 1.0
 */
public class TestController {
  public static Map<String, String> dbMap = new ConcurrentHashMap();

  public static void main(String[] args) {
    //    String result = HttpClientUtil.sendGetRequest(createZoomMeetingUrl, new HashMap(), new HashMap());

    String result = "[{\"messageId\":\"e97d1669-5f04-483b-80a7-bdbd677ac2f0\",\"senderId\":null,\"objectId\":\"finalSubtitles\",\"type\":\"map\",\"operation\":null,\"property\":\"en\",\"value\":\"{\\\"timestamp\\\":\\\"2022-09-20T07:02:13.4690538Z\\\",\\\"slideState\\\":{\\\"location\\\":{\\\"slideId\\\":{\\\"sid\\\":278,\\\"cid\\\":0},\\\"slideIndex\\\":0},\\\"timelineMappings\\\":null},\\\"subtitle\\\":\\\"Hello Hello。\\\"}\",\"metadata\":{\"timestamp\":\"09/20/2022 07:02:13\"}},{\"messageId\":\"684e4fe2-cd77-48e6-871c-ab0950aab851\",\"senderId\":null,\"objectId\":\"finalSubtitles\",\"type\":\"map\",\"operation\":null,\"property\":\"en\",\"value\":\"{\\\"timestamp\\\":\\\"2022-09-20T07:02:50.0974831Z\\\",\\\"slideState\\\":{\\\"location\\\":{\\\"slideId\\\":{\\\"sid\\\":278,\\\"cid\\\":0},\\\"slideIndex\\\":0},\\\"timelineMappings\\\":null},\\\"subtitle\\\":\\\"That's right.\\\"}\",\"metadata\":{\"timestamp\":\"09/20/2022 07:02:50\"}},{\"messageId\":\"519fa533-d0ca-4271-a51e-cd7716088b00\",\"senderId\":\"1c122e24-ba5b-4e6c-b334-fca8378c626b\",\"objectId\":\"finalSubtitles\",\"type\":\"map\",\"operation\":\"set\",\"property\":\"en\",\"value\":\"{\\\"timestamp\\\":\\\"2022-09-20T07:02:13.4690538Z\\\",\\\"slideState\\\":{\\\"location\\\":{\\\"slideId\\\":{\\\"sid\\\":278,\\\"cid\\\":0},\\\"slideIndex\\\":0},\\\"timelineMappings\\\":null},\\\"subtitle\\\":\\\"Hello Hello。\\\"}\",\"metadata\":null}]";
    JSONArray jsonArray = JSONArray.parseArray(result);
    int size = jsonArray.size();
    //    JSONArray docMsg = JSONArray.parseArray(dbMap.get(key));
    //    int docSize = docMsg != null ? docMsg.size() : 0;
    int docSize = 0;
    //    log.info("任务={},消息数量={}", docId, size);
    //       新增消息
    int newIncreased = size - docSize;
    if (size != 0 && newIncreased > 0) {
      //      dbMap.put(key, result);
      List<Record> recordList = Lists.newArrayList();
      Record record;
      for (int i = docSize; i < newIncreased; i++) {
        record = new Record();
        record.setDocId("docid");
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        record.setMessageId(json2Str(jsonObject.get("messageId")));
        if (Objects.isNull(jsonObject.get("senderId"))) {
          continue;
        } else {
          record.setSenderId(json2Str(jsonObject.get("senderId")));
        }
        jsonObject.get("property");
        record.setProperty(json2Str(jsonObject.get("property")));

        JSONObject subJsonObject = JSON.parseObject(String.valueOf(jsonObject.get("value")));
        subJsonObject.get("subtitle");
        record.setSubtitle(json2Str(subJsonObject.get("subtitle")));
        subJsonObject.get("timestamp");

        record.setTimestamp(DateUtil.str2Date(json2Str(subJsonObject.get("timestamp")), DateUtil.FORMAT_YYYYMMDDTHHMMSS));
        recordList.add(record);

      }
    }

    String dateStr = "2022-09-20T07:02:13.4690538Z";

    Date date = DateUtil.str2Date(dateStr, DateUtil.FORMAT_YYYYMMDDTHHMMSS);
    //    LocalDateTime localDateTime = DateUtil.string2LocalDateTime(dateStr, DateUtil.FORMATTER_YYYY_MM_T_DD_HH_MM_SS);
    System.out.println();

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
