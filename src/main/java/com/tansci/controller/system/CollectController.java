package com.tansci.controller.system;

import com.tansci.common.WrapMapper;
import com.tansci.common.Wrapper;
import com.tansci.common.annotation.Log;
import com.tansci.common.constant.Constants;
import com.tansci.common.task.RecordTask;
import com.tansci.domain.system.SysMenu;
import com.tansci.domain.system.dto.SysMenuDto;
import com.tansci.enums.LanguageEnum;
import com.tansci.service.system.SysMenuService;
import com.tansci.utils.ExportUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  private SysMenuService sysMenuService;
  @Autowired
  private RecordTask recordTask;

  @ApiOperation(value = "采集列表", notes = "采集列表")
  @Log(modul = "采集-采集列表", type = Constants.SELECT, desc = "采集列表")
  @GetMapping("/record")
  public void record(@RequestParam(name = "docId", required = true) String docId,
      @RequestParam(name = "domain", required = false) String domain, HttpServletResponse httpServletResponse) {
    //        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysMenuService.list(null));
    log.info("export===========,docId={},domain={}", docId, domain);
    recordTask.singleTask(docId, domain);
//    Map<String, String> exportMap = new HashMap();
//    LanguageEnum[] var6 = LanguageEnum.values();
//    int var7 = var6.length;
//
//    for (int var8 = 0; var8 < var7; ++var8) {
//      LanguageEnum value = var6[var8];
//      String language = value.getLanguage();
//      String key = docId + "_" + language;
//      if (RecordTask.dbMap.containsKey(key)) {
//        exportMap.put(key, (String) RecordTask.dbMap.get(key));
//      }
//    }
//
//    try {
//      Thread.sleep(1000L);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    if (!exportMap.isEmpty()) {
//      ExportUtil.exportZip(httpServletResponse, exportMap);
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
