package com.tansci.service.impl.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tansci.common.constant.Enums;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.domain.system.TaskConfig;
import com.tansci.mapper.system.RecordMapper;
import com.tansci.service.system.RecordService;
import com.tansci.utils.SecurityUserUtils;

import org.springframework.stereotype.Service;

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
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {


  @Override
  public IPage<Record> page(Page page, Record record) {
    IPage<Record> iPage = this.baseMapper.selectPage(page,
        Wrappers.<Record>lambdaQuery()
            .eq(StringUtils.isNotBlank(record.getDocId()),Record::getDocId, record.getDocId())
            .eq(Record::getUserId,SecurityUserUtils.getUser().getId())
            .eq(Record::getType, record.getType())
            .like(StringUtils.isNotBlank(record.getRemark()), Record::getRemark, record.getRemark())
            .orderByDesc(Record::getCreateTime)
    );
//    iPage.getRecords().forEach(item -> {
//      item.setStatusName(Enums.getVlaueByGroup(item.getStatus(), "status"));
//    });
    return iPage;
  }

  @Override
  public boolean save(Record record) {
    this.baseMapper.insert(record);

    return true;
  }

  @Override
  public Record selectOne(Record record) {

    return this.baseMapper.selectOne(Wrappers.<Record>lambdaQuery().eq(Record::getDocId, record.getDocId()));
  }



  @Override
  public void update(Record record) {
    this.baseMapper.update(record, Wrappers.<Record>lambdaQuery().eq(Record::getDocId, record.getDocId()));
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
