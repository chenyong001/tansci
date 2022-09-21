package com.tansci.common.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import com.tansci.domain.system.Record;
import com.tansci.enums.LanguageEnum;
import com.tansci.service.system.RecordService;
import com.tansci.utils.DateUtil;
import com.tansci.utils.HttpClientUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author chenyong
 * @Date 2022/9/19 11:23
 * @Version 1.0
 */
@Slf4j
@Component
public class RecordTask {
  public static Map<String, Integer> countMap = new ConcurrentHashMap();
  @Autowired
  private RecordService recordService;

  public void singleTask(String docId, String domain) {
    log.info("===============执行任务docId={},time={}", docId, System.currentTimeMillis());

    try {
      LanguageEnum[] var3 = LanguageEnum.values();
      int var4 = var3.length;

      for (int var5 = 0; var5 < var4; ++var5) {
        LanguageEnum value = var3[var5];
        String language = value.getLanguage();
        String key = docId + "_" + language;
        String createZoomMeetingUrl =
            "https://" + domain + "/StateServiceHandler.ashx?action=deltas&objectId=finalSubtitles&docId=" + docId
                + "&property=" + language;
        log.info("===============执行任务docId={},time={},发送请求={}",
            new Object[] { docId, System.currentTimeMillis(), createZoomMeetingUrl });
        String result = HttpClientUtil.sendGetRequest(createZoomMeetingUrl, new HashMap(), new HashMap());
        JSONArray jsonArray = JSONArray.parseArray(result);
        int size = jsonArray.size();
        //从数据库统计docId  + language的数量
        int docSize = countMap.containsKey(key) ? countMap.get(key) : recordService.countRecord(new Record(docId,language));
        log.info("任务={},消息数量={}", docId, size);
        //       新增消息
        int newIncreased = size - docSize;
        if (size != 0 && newIncreased > 0) {
          countMap.put(key, size);
          List<Record> recordList = Lists.newArrayList();
          Record record;
          for (int i = docSize; i < size; i++) {
            record = new Record();
            record.setDocId(docId);
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            record.setMessageId(json2Str(jsonObject.get("messageId")));
//            if (Objects.isNull(jsonObject.get("senderId"))) {
//              continue;
//            } else {
              record.setSenderId(json2Str(jsonObject.get("senderId")));
//            }
            record.setProperty(json2Str(jsonObject.get("property")));
            JSONObject subJsonObject = JSON.parseObject(String.valueOf(jsonObject.get("value")));
            record.setSubtitle(json2Str(subJsonObject.get("subtitle")));
            record.setTimestamp(
                DateUtil.str2Date(json2Str(subJsonObject.get("timestamp")), DateUtil.FORMAT_YYYYMMDDTHHMMSS));
            recordList.add(record);

          }
          //           入库
          if (!recordList.isEmpty()) {
            recordService.saveBatch(recordList);
            log.info("任务={},更新数据={}", docId, recordList);
          }
        }
      }
    } catch (Exception var15) {
      log.error("执行任务异常，异常信息：{}", var15);
    }

  }

  /**
   * json转String
   *
   * @param obj
   * @return
   */
  private String json2Str(Object obj) {
    return Objects.isNull(obj) ? "" : String.valueOf(obj);
  }
}
