package com.tansci.service.impl.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.domain.system.RecordStat;
import com.tansci.mapper.system.RecordMapper;
import com.tansci.service.system.RecordDataService;
import com.tansci.service.system.RecordService;
import com.tansci.service.system.RecordStatService;
import com.tansci.utils.SecurityUserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

  @Autowired
  private RecordDataService recordDataService;
  @Autowired
  private RecordStatService recordStatService;

  //
  //  @Override
  //  public IPage<Record> page(Page page, Record record) {
  //    IPage<Record> iPage = this.baseMapper.selectPage(page, Wrappers.<Record>lambdaQuery()
  //        .eq(StringUtils.isNotBlank(record.getDocId()), Record::getDocId, record.getDocId())
  //        .eq(Record::getUserId, SecurityUserUtils.getUser().getId()).eq(Record::getType, record.getType())
  //        .like(StringUtils.isNotBlank(record.getRemark()), Record::getRemark, record.getRemark())
  //        .orderByDesc(Record::getCreateTime));
  //    //    iPage.getRecords().forEach(item -> {
  //    //
  //    //      item.setStatusName(Enums.getVlaueByGroup(item.getStatus(), "status"));
  //    //    });
  //    return iPage;
  //  }
  public static void main(String[] args) {
    //    long duration=3119000;
    long duration = 7480000;
    //    52*60=3120
    long s = duration / 1000;
    long hh = s / (60 * 60) % 24;
    long mm = s / 60 % 60;
    long ss = s % 60;
    System.out.println(String.format("%s:%s:%s", hh, mm, ss));

    System.out.println(s);
    System.out.println(hh);
    System.out.println(mm);
    System.out.println(ss);

    //    String durationStr = new RecordServiceImpl().getDurationStr(duration);
    //    System.out.println(durationStr);

  }

  @Override
  public IPage<Record> page(Page page, Record record) {
    IPage<Record> iPage = this.baseMapper.selectPage(page, Wrappers.<Record>lambdaQuery()
        .eq(StringUtils.isNotBlank(record.getDocId()), Record::getDocId, record.getDocId())
        .eq(Record::getUserId, SecurityUserUtils.getUser().getId()).eq(Record::getType, record.getType())
        .like(StringUtils.isNotBlank(record.getRemark()), Record::getRemark, record.getRemark())
        .orderByDesc(Record::getCreateTime));

    iPage.getRecords().forEach(item -> {

      RecordStat recordStat = new RecordStat();
      recordStat.setDocId(item.getDocId());
      RecordStat recordStat1 = recordStatService.selectOne(recordStat);
      long recordNum = recordStat1 != null ? recordStat1.getRecordNum() : 0;
      long duration = recordStat1 != null ? recordStat1.getDuration() : 0; //持续时长
      String durationStr = getDurationStr(duration);
      long charactersCount = recordStat1 != null ? recordStat1.getCharactersCount() : 0;//字面量

      RecordData dto = new RecordData();
      dto.setDocId(item.getDocId());
      String property = record.getType() == 1 ? "en" : null;
      dto.setProperty(property);

      Integer total = recordDataService.countRecord(dto);

      if (total != recordNum && total != 0) {
        //        1、统计时长， 2、统计字符量，3、更新时长、字符量、total到record_stat表中
        //        1、统计时长         //        2、统计字符量
        List<RecordData> recordDataList = recordDataService.selectList(dto);
        recordNum = total;
        long tempCharactersCount = 0;
        long startTime = 0, endTime = 0;
        if (recordDataList.size() > 0) {
          for (int i = 0; i < recordDataList.size(); i++) {
            RecordData recordData = recordDataList.get(i);
            if (i == 0) {
              startTime = recordData.getTimestamp().getTime();
            }
            tempCharactersCount += recordData.getSubtitle().length();
            if (i == recordDataList.size() - 1) {
              endTime = recordData.getTimestamp().getTime();
            }
          }
          duration = endTime - startTime;
          durationStr = getDurationStr(duration);
          charactersCount = tempCharactersCount;

          //        3、更新时长、字符量、total到record_stat表中

          recordStat.setRecordNum(recordNum);
          recordStat.setDuration(duration);
          recordStat.setCharactersCount(charactersCount);
          recordStat.setUpdateTime(new Date());
          if (recordStat1 == null) {
            recordStat.setCreateTime(new Date());
            recordStatService.save(recordStat);
          } else {
            recordStatService.update(recordStat);
          }
        }
      }
      item.setRecordNum(recordNum);
      item.setDuration(duration);
      item.setDurationStr(durationStr);

      item.setCharactersCount(charactersCount);
    });
    return iPage;
  }

  /**
   * 获取时长字符串
   *
   * @param duration
   * @return
   */
  private String getDurationStr(long duration) {
    //    秒
    long s = duration / 1000;
    //    小时
    long hh = s / (60 * 60) % 24;
    //    分钟
    long mm = s / 60 % 60;
    //    秒钟
    long ss = s % 60;
    return String.format("%s:%s:%s", hh, mm, ss);
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
  @Override
  public boolean del(Record record) {
    int row = this.baseMapper.deleteById(record.getId());
    if (row > 0) {
      return true;
    }
    return false;
  }

}
