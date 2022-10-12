package com.tansci.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Lists;
import com.tansci.common.WrapMapper;
import com.tansci.common.Wrapper;
import com.tansci.common.annotation.Log;
import com.tansci.common.constant.Constants;
import com.tansci.common.task.RecordTask;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.enums.CollectTypeEnum;
import com.tansci.service.system.RecordDataService;
import com.tansci.service.system.RecordService;
import com.tansci.utils.DateUtil;
import com.tansci.utils.ExportUtil;
import com.tansci.utils.SecurityUserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName： SysMenuController.java
 * @ClassPath： com.tansci.controller.SysMenuController.java
 * @Description： 菜单
 * @Author： tanyp
 * @Date： 2021/7/20 17:08
 **/
@Slf4j
@Controller
@RequestMapping("/collect")
@Api(value = "collect", tags = "采集")
public class CollectController {

  @Autowired
  private RecordDataService recordDataService;
  @Autowired
  private RecordService recordService;

  @Autowired
  private RecordTask recordTask;

  @ApiOperation(value = "采集列表", notes = "采集列表")
  @Log(modul = "采集-采集列表", type = Constants.SELECT, desc = "采集列表")
  @ResponseBody
  @GetMapping("/page")
  public Wrapper<IPage<Record>> page(Page page, Record record) {
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, recordService.page(page, record));
  }

  @ApiOperation(value = "记录数据列表", notes = "记录数据列表")
  @Log(modul = "采集-记录数据列表", type = Constants.SELECT, desc = "记录数据列表")
  @ResponseBody
  @GetMapping("/dataPage")
  public Wrapper<IPage<RecordData>> dataPage(Page page, RecordData recordData) {
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, recordDataService.page(page, recordData));
  }

  @ApiOperation(value = "导出列表", notes = "导出列表")
  @Log(modul = "采集-导出列表", type = Constants.SELECT, desc = "导出列表")
  @GetMapping("/exportTxt")
  public void exportTxt(RecordData recordData, HttpServletResponse response) {
    List<RecordData> recordDataList = recordDataService.selectList(recordData);
    ExportUtil.exportTxt(response, recordDataList);
  }

  @ApiOperation(value = "列表", notes = "采集列表")
  @Log(modul = "采集-采集列表", type = Constants.SELECT, desc = "采集列表")
  @GetMapping("/record")
  @ResponseBody
  public Wrapper<Boolean> record(@RequestParam(name = "docId", required = true) String docId,
      @RequestParam(name = "domain", required = false) String domain,
      @RequestParam(name = "remark", required = false) String remark) {
    //        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysMenuService.list(null));
    log.info("export===========,docId={},domain={}", docId, domain);
    //   新增或更新采集记录
    Record record = new Record();
    record.setDocId(docId);
    record.setUserId(SecurityUserUtils.getUser().getId());
    record.setRemark(remark);

    if (Objects.isNull(recordService.selectOne(record))) {
      record.setCreateTime(new Date());
      record.setUpdateTime(new Date());
      record.setType(CollectTypeEnum.COLLECT_TYPE_MICROSOFT_PPT.getType());
      recordService.save(record);
    } else {
      record.setUpdateTime(new Date());
      recordService.update(record);
    }

    //    添加采集数据到数据库中 netstat -ano|findstr "8005" tasklist|findstr "20516" taskkill /f /t /im java.exe
    //    recordTask.singleTask(docId, domain);
    recordTask.addTask(docId, domain);
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);

    //    return "success";
    //    Map<String, String> exportMap = new HashMap();
    //    LanguageEnum[] var6 = LanguageEnum.values();
    //    int var7 = var6.length;
    //
    //    for (int var8 = 0; var8 < var7; ++var8) {
    //      LanguageEnum value = var6[var8];
    //      String language = value.getLanguage();
    //      String key = docId + "_" + language;
    //      if (RecordTask.dbMap.containsKey(key)) {
    //            exportMap.put(key, (String) RecordTask.dbMap.get(key));
    //      }
    //    }
    //
    //    try {
    //      Thread.sleep(1000L);
    //    } catch (InterruptedException e) {
    //      e.printStackTrace();
    //    }
    //    if (!exportMap.isEmpty()) {
    //          ExportUtil.exportZip(httpServletResponse, exportMap);
    //      log.info("export===========success,docId={},time={}", docId, System.currentTimeMillis());
    //    }
  }

  @ApiOperation(value = "列表", notes = "采集列表")
  @Log(modul = "采集-采集列表", type = Constants.SELECT, desc = "采集列表")
  @GetMapping("/sendNote")
  @ResponseBody
  public Wrapper<Boolean> sendNote(@RequestParam(name = "resultText", required = true) String resultText,
      @RequestParam(name = "docId", required = true) String docId,
      @RequestParam(name = "remark", required = false) String remark,
      @RequestParam(name = "language", required = true) String language) {
    //        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysMenuService.list(null));
    log.info("export===========,resultText={}", resultText);
    //   新增或更新采集记录
    Record record = new Record();
    //    String docId = UUID.randomUUID().toString().replace("-", "");
    record.setDocId(docId);
    record.setUserId(SecurityUserUtils.getUser().getId());
    record.setRemark(remark);
    if (Objects.isNull(recordService.selectOne(record))) {

      record.setType(CollectTypeEnum.COLLECT_TYPE_AZURE.getType());
      record.setCreateTime(new Date());
      record.setUpdateTime(new Date());
      recordService.save(record);
    }
//    else {
//      record.setUpdateTime(new Date());
//      recordService.update(record);
//    }
    //    添加内容到数据库
    List<RecordData> recordDataList = Lists.newArrayList();
    RecordData recordData = new RecordData();
    recordData.setDocId(docId);
    recordData.setProperty(language);
    recordData.setSubtitle(resultText);
    recordData.setTimestamp(new Date());
    recordDataList.add(recordData);
    //           入库
    if (!recordDataList.isEmpty()) {
      recordDataService.saveBatch(recordDataList);
    }
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
  }
}
