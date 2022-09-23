package com.tansci.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tansci.common.WrapMapper;
import com.tansci.common.Wrapper;
import com.tansci.common.annotation.Log;
import com.tansci.common.constant.Constants;
import com.tansci.common.task.RecordTask;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.service.system.RecordDataService;
import com.tansci.service.system.RecordService;
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
    ExportUtil.exportTxt(response,recordDataList);
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
  //
  //    @GetMapping("/t1")
  //    public void down1(HttpServletResponse response) throws Exception {
  //        response.reset();
  //        response.setContentType("application/octet-stream;charset=utf-8");
  //        response.setHeader(
  //            "Content-disposition",
  //            "attachment; filename=test.png");
  //        try(
  //            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\test\\demodoc.pdf"));
  //            // 输出流
  //            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
  //        ){
  //            byte[] buff = new byte[1024];
  //            int len = 0;
  //            while ((len = bis.read(buff)) > 0) {
  //                bos.write(buff, 0, len);
  //            }
  //        }
  //    }

  //
  //    @ApiOperation(value = "添加菜单", notes = "添加菜单")
  //    @Log(modul = "菜单-添加菜单", type = Constants.INSERT, desc = "添加菜单")
  //    @PostMapping("/save")
  //    public Wrapper<Boolean> save(@RequestBody SysMenu sysMenu) {
  //        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysMenuService.save(sysMenu));
  //    }
  //
  //    @ApiOperation(value = "修改菜单", notes = "修改菜单")
  //    @Log(modul = "菜单-修改菜单", type = Constants.UPDATE, desc = "修改菜单")
  //    @PostMapping("/update")
  //    public Wrapper<Boolean> update(@RequestBody SysMenu sysMenu) {
  //        sysMenu.setUpdateTime(LocalDateTime.now());
  //        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysMenuService.updateById(sysMenu));
  //    }
  //
  //    @ApiOperation(value = "删除菜单", notes = "删除菜单")
  //    @Log(modul = "菜单-删除菜单", type = Constants.DELETE, desc = "删除菜单")
  //    @GetMapping("/del")
  //    public Wrapper<Boolean> del(Integer id) {
  //        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysMenuService.del(id));
  //    }

}
