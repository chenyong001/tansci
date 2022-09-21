package com.tansci.service.impl.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tansci.common.constant.Enums;
import com.tansci.common.task.ScheduledTask;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.SysUser;
import com.tansci.domain.system.TaskConfig;
import com.tansci.domain.system.dto.TaskConfigDto;
import com.tansci.mapper.system.RecordMapper;
import com.tansci.mapper.system.TaskConfigMapper;
import com.tansci.service.system.RecordService;
import com.tansci.service.system.TaskConfigService;
import com.tansci.utils.SecurityUserUtils;
import com.tansci.utils.UUIDUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
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
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {


  @Override
  public boolean save(Record record) {

    return true;
  }

  @Override
  public boolean saveBatch(List<Record> recordList) {
    super.saveBatch(recordList);
    return true;
  }

  @Override
  public Integer countRecord(Record record) {
    return this.baseMapper.selectCount(Wrappers.<Record>lambdaQuery().eq(Record::getDocId, record.getDocId())
        .eq(Record::getProperty,record.getProperty()));
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
