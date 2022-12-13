package com.tansci.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tansci.common.constant.Enums;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.domain.system.TaskConfig;
import com.tansci.mapper.system.RecordDataMapper;
import com.tansci.service.system.RecordDataService;
import com.tansci.utils.SecurityUserUtils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName： TaskConfigServiceImpl.java
 * @ClassPath： com.tansci.service.impl.system.TaskConfigServiceImpl.java
 * @Description： 任务配置
 * @Author： tanyp
 * @Date： 2022/2/25 9:46
 **/
@Slf4j
@Service
public class RecordDataServiceImpl extends ServiceImpl<RecordDataMapper, RecordData> implements RecordDataService {

  @Override
  public IPage<RecordData> page(Page page, RecordData dto) {
    IPage<RecordData> iPage = this.baseMapper
        .selectPage(page, Wrappers.<RecordData>lambdaQuery().eq(RecordData::getDocId, dto.getDocId())
            //            .eq(RecordData::getSenderId, SecurityUserUtils.getUser().getUsername())
            .eq(StringUtils.isNotBlank(dto.getProperty()), RecordData::getProperty, dto.getProperty())
            //            todo 根据时间查询
            .orderByAsc(RecordData::getTimestamp));
    //    iPage.getRecords().forEach(item -> {
    //      item.setStatusName(Enums.getVlaueByGroup(item.getStatus(), "status"));
    //    });
    return iPage;
    //    return null;
  }

  @Override
  public List<RecordData> selectList(RecordData dto) {
    LambdaQueryWrapper<RecordData> eq = Wrappers.<RecordData>lambdaQuery().eq(RecordData::getDocId, dto.getDocId())
        //            .eq(RecordData::getSenderId, SecurityUserUtils.getUser().getUsername())
        .eq(StringUtils.isNotBlank(dto.getProperty()), RecordData::getProperty, dto.getProperty());
    if(StringUtils.isNotBlank(dto.getMark())){
      eq.notIn(RecordData::getMark, Arrays.asList(dto.getMark().split(",")));
    }
//    eq.notIn(StringUtils.isNotBlank(dto.getMark()),RecordData::getMark, Arrays.asList(dto.getMark().split(",")));
    eq.orderByAsc(RecordData::getTimestamp);

    return this.baseMapper.selectList(eq);
//    return this.baseMapper.selectList(Wrappers.<RecordData>lambdaQuery().eq(RecordData::getDocId, dto.getDocId())
//        //            .eq(RecordData::getSenderId, SecurityUserUtils.getUser().getUsername())
//        .eq(StringUtils.isNotBlank(dto.getProperty()), RecordData::getProperty, dto.getProperty())
//
////        .notIn(StringUtils.isNotBlank(dto.getMark()), RecordData::getMark, Arrays.asList(dto.getMark().split(",")))
//        //            todo 根据时间查询
//        .orderByAsc(RecordData::getTimestamp));

  }

  @Override
  public boolean update(RecordData recordData) {
    this.baseMapper.updateById(recordData);
    return true;
  }

  @Override
  public RecordData selectById(int id) {

    return this.baseMapper.selectById(id);
  }

  @Override
  public boolean save(RecordData recordData) {

    return true;
  }

  @Override
  public boolean saveBatch(List<RecordData> recordDataList) {
    super.saveBatch(recordDataList);
    return true;
  }

  @Override
  public Integer countRecord(RecordData recordData) {
    return this.baseMapper.selectCount(
        Wrappers.<RecordData>lambdaQuery().eq(RecordData::getDocId, recordData.getDocId())
            .eq(StringUtils.isNotBlank(recordData.getProperty()), RecordData::getProperty, recordData.getProperty()));
  }

  //    @Override
  //    public boolean update(TaskConfig taskConfig) {
  //        taskConfig.setUpdateTime(LocalDateTime.now());
  //        int row = this.baseMapper.updateById(taskConfig);
  //        if (row > 0) {
  //            scheduledTask.refreshTask(this.list(Wrappers.<TaskConfig>lambdaQuery().eq(TaskConfig::getStatus, 1)));
  //            return true;
  //        }
  //        return false;
  //    }
  //
  //    @Override
  //    public boolean del(TaskConfigDto dto) {
  //        int row = this.baseMapper.deleteById(dto.getId());
  //        if (row > 0) {
  //            scheduledTask.refreshTask(this.list(Wrappers.<TaskConfig>lambdaQuery().eq(TaskConfig::getStatus, 1)));
  //            return true;
  //        }
  //        return false;
  //    }

}
