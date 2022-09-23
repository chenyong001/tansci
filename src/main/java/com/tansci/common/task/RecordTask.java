package com.tansci.common.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import com.tansci.domain.system.RecordData;
import com.tansci.enums.LanguageEnum;
import com.tansci.service.system.RecordDataService;
import com.tansci.utils.DateUtil;
import com.tansci.utils.HttpClientUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author chenyong
 * @Date 2022/9/19 11:23
 * @Version 1.0
 */
@Slf4j
@Component
@EnableScheduling
@EnableAsync
public class RecordTask implements SchedulingConfigurer {
  public static Map<String, Integer> countMap = new ConcurrentHashMap();
  @Autowired
  private RecordDataService recordDataService;

  public static final Integer DEFAULT_THREAD_POOL = 10;
  private volatile ScheduledTaskRegistrar registrar;
  public static final ConcurrentHashMap<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap();
  public static Map<String, String> dbMap = new ConcurrentHashMap();

  public RecordTask() {
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar registrar) {
    registrar.setScheduler(Executors.newScheduledThreadPool(DEFAULT_THREAD_POOL));
    this.registrar = registrar;
  }

  public void addTask(String docId, String domain) {
    String expression = "0/30 * * * * ?";
    CronTask task = new CronTask(new Runnable() {
      @Override
      public void run() {
        singleTask(docId, domain);
      }
    }, expression);
    ScheduledFuture<?> future = this.registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
    scheduledFutures.put(docId, future);
  }

  public void delTask(String taskId) {
    log.info("==========停止单个任务,taskId={},time={}", taskId, System.currentTimeMillis());
    ((ScheduledFuture)scheduledFutures.get(taskId)).cancel(false);
    scheduledFutures.remove(taskId);
  }







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
        int docSize = countMap.containsKey(key) ? countMap.get(key) : recordDataService
            .countRecord(new RecordData(docId,language));
        log.info("任务={},消息数量={},已入库消息数量={}", docId, size,docSize);
        //       新增消息
        int newIncreased = size - docSize;
        if (size != 0 && newIncreased > 0) {
          countMap.put(key, size);
          List<RecordData> recordDataList = Lists.newArrayList();
          RecordData recordData;
          for (int i = docSize; i < size; i++) {
            recordData = new RecordData();
            recordData.setDocId(docId);
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            recordData.setMessageId(json2Str(jsonObject.get("messageId")));
//            if (Objects.isNull(jsonObject.get("senderId"))) {
//              continue;
//            } else {
              recordData.setSenderId(json2Str(jsonObject.get("senderId")));
//            }
            recordData.setProperty(json2Str(jsonObject.get("property")));
            JSONObject subJsonObject = JSON.parseObject(String.valueOf(jsonObject.get("value")));
            recordData.setSubtitle(json2Str(subJsonObject.get("subtitle")));
            recordData.setTimestamp(
                dateAfter8Hour(DateUtil.str2Date(json2Str(subJsonObject.get("timestamp")), DateUtil.FORMAT_YYYYMMDDTHHMMSS)));
            recordDataList.add(recordData);

          }
          //           入库
          if (!recordDataList.isEmpty()) {
            recordDataService.saveBatch(recordDataList);
            log.info("任务={},更新数据={}", docId, recordDataList);
          }
        }
      }
    } catch (Exception var15) {
      log.error("执行任务异常，异常信息：{}", var15);
    }

  }

  /**
   * 时间追加8小时
   * @param date
   * @return
   */
  private Date dateAfter8Hour(Date date){
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.HOUR_OF_DAY, 8);
    return cal.getTime();
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
