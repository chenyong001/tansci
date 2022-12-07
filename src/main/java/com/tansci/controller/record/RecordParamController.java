package com.tansci.controller.record;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tansci.common.WrapMapper;
import com.tansci.common.Wrapper;
import com.tansci.common.annotation.Log;
import com.tansci.common.constant.Constants;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.domain.system.RecordParam;
import com.tansci.enums.CollectTypeEnum;
import com.tansci.service.record.RecordParamService;
import com.tansci.service.system.RecordService;
import com.tansci.utils.SecurityUserUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Date;
import java.util.Objects;

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
@RequestMapping("/recordParam")
public class RecordParamController {

  @Autowired
  private RecordParamService recordParamService;

  @ApiOperation(value = "采集列表", notes = "采集列表")
  @Log(modul = "采集-采集列表", type = Constants.SELECT, desc = "采集列表")
  @ResponseBody
  @GetMapping("/page")
  public Wrapper<IPage<RecordParam>> page(Page page, RecordParam recordParam) {
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, recordParamService.page(page, recordParam));
  }

  @GetMapping("/createParam")
  @ResponseBody
  public Wrapper<Boolean> createParam(RecordParam recordParam) {
    log.info("createParam===========,recordParam={}", JSON.toJSONString(recordParam));
    if (StringUtils.isNotBlank(recordParam.getDocId())) {
      recordParam.setCreateTime(new Date());
      recordParam.setUpdateTime(new Date());
      recordParamService.save(recordParam);
    }
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
  }

  @GetMapping("/updateParam")
  @ResponseBody
  public Wrapper<Boolean> updateParam(RecordParam recordParam) {
    log.info("updateParam===========,recordParam={}", JSON.toJSONString(recordParam));
    RecordParam recordParam1 = recordParamService.selectOne(recordParam);
    //    添加内容到数据库
    recordParam1.setUpdateTime(new Date());
    recordParam1.setValue(recordParam.getValue());
    recordParamService.update(recordParam1);
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
  }

  @PostMapping("/deleteParam")
  @ResponseBody
  public Wrapper<Boolean> deleteParam(@RequestBody RecordParam recordParam) {
    log.info("deleteParam===========,resultText={}");
    if (Objects.isNull(recordParam.getId())) {
      //      如果数据为空，则不处理，直接返回
      return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
    }
    recordParamService.del(recordParam);
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
  }

}
