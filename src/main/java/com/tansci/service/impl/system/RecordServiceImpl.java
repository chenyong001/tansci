package com.tansci.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tansci.common.constant.Constants;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.domain.system.RecordParam;
import com.tansci.domain.system.RecordStat;
import com.tansci.domain.system.SysUser;
import com.tansci.enums.CollectTypeEnum;
import com.tansci.mapper.system.RecordMapper;
import com.tansci.service.record.RecordParamService;
import com.tansci.service.system.RecordDataService;
import com.tansci.service.system.RecordService;
import com.tansci.service.system.RecordStatService;
import com.tansci.service.system.SysUserService;
import com.tansci.utils.DateUtil;
import com.tansci.utils.FileUtil;
import com.tansci.utils.SecurityUserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
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

  @Autowired
  private RecordDataService recordDataService;
  @Autowired
  private RecordStatService recordStatService;
  @Autowired
  private RecordParamService recordParamService;
  @Autowired
  private SysUserService sysUserService;

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
    SysUser user = SecurityUserUtils.getUser();

    LambdaQueryWrapper<Record> recordLambdaQueryWrapper = Wrappers.<Record>lambdaQuery()
        .eq(StringUtils.isNotBlank(record.getDocId()), Record::getDocId, record.getDocId());
    if (Objects.equals(user.getType(), 2)) {
      //      如果类型是普通用户，才通过账号ID过滤
      recordLambdaQueryWrapper.eq(Record::getUserId, user.getId());
    }
    recordLambdaQueryWrapper.eq(Record::getType, record.getType())
        .like(StringUtils.isNotBlank(record.getRemark()), Record::getRemark, record.getRemark())
        .orderByDesc(Record::getCreateTime);

    IPage<Record> iPage = this.baseMapper.selectPage(page, recordLambdaQueryWrapper);

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
      //      遍历查询每个会话的用户信息
      SysUser sysUser = sysUserService.selectByUserId(item.getUserId());
      item.setUserName(sysUser == null ? "" : sysUser.getUsername());

      item.setRecordNum(recordNum);
      item.setDuration(duration);
      item.setDurationStr(durationStr);

      item.setCharactersCount(charactersCount);
    });
    return iPage;
  }

  /**
   * 1、获取开始和结束时间
   * 2、计算时长，根据比例计算出分隔时间
   * 3、根据分隔时间，新建会话并将超过>=的记录移到新的会话中
   * 4、分隔音频，删除总音频，并更新数据记录
   *
   * @param docId 会话ID
   * @param ratio 比例
   */
  @Override
  public void cuttingRecord(String docId, int ratio) {
    //    1、获取开始和结束时间
    //    2、计算时长，根据比例计算出分隔时间
    //    3、根据分隔时间，新建会话并将>=分隔时间的记录移到新的会话中
    //    4、分隔音频，删除总音频，并更新数据记录

    //    float ratio = 50.0f;
    if (ratio == 100) {
      //      不切割
      return;
    }
    RecordData dto = new RecordData();
    dto.setDocId(docId);
    //    String property = record.getType() == 1 ? "en" : null;
    //    dto.setProperty(property);
    //    1、获取开始和结束时间
    RecordData firstRecordData = recordDataService.selectFirstByDocId(dto);
    RecordData endRecordData = recordDataService.selectEndByDocId(dto);
    if (Objects.isNull(firstRecordData) || Objects.isNull(endRecordData)) {
      return;
    }
    //    2、计算时长，根据比例计算出分隔时间
    Record oldRecord = new Record();
    oldRecord.setDocId(docId);
    Record oldRecord1 = selectOne(oldRecord);
    String filePath = oldRecord1.getFilePath();

    int duration = FileUtil.getTimeLen(filePath);
    long startTime = firstRecordData.getTimestamp().getTime();
    //    long endTime = startTime+duration;
    //    long duration = endTime - startTime;
    long ratioTime = duration * ratio / 100;
    Date cutDate = new Date(startTime + ratioTime);
    String newDocId = "cut_" + System.currentTimeMillis() + "_" + docId;

    //    3、根据分隔时间，新建会话并将超过>=的记录移到新的会话中
    Record record = new Record();
    record.setDocId(newDocId);
    record.setUserId(SecurityUserUtils.getUser().getId());
    record.setRemark("切割来自DOCID=" + docId);

    //    if (Objects.isNull(recordService.selectOne(record))) {
    record.setCreateTime(new Date());
    record.setUpdateTime(new Date());
    //    record.setFilePath();
    record.setType(CollectTypeEnum.COLLECT_TYPE_AZURE.getType());
    save(record);

    RecordParam recordParam = new RecordParam();
    recordParam.setDocId(newDocId);
    recordParam.setName(Constants.ANALYSIS_NAME);
    recordParam.setValue(Constants.ANALYSIS_NUM);
    recordParam.setType(2);
    recordParam.setCreateTime(new Date());
    recordParam.setUpdateTime(new Date());
    recordParamService.save(recordParam);

    RecordData rdDto = new RecordData();
    rdDto.setDocId(newDocId);
    RecordData condition = new RecordData();
    condition.setDocId(docId);
    condition.setTimestamp(cutDate);
    recordDataService.updateCut(rdDto, condition);
    //    4、分隔音频，删除总音频，并更新数据记录

    if (StringUtils.isNotBlank(filePath)) {
      Path path = Paths.get("").toAbsolutePath().resolve("tempAudio");
      String parentPath = path.toAbsolutePath().toString() + File.separator;

      //目标文件
      String targetFileName1 =
          parentPath + "audio_recording_" + docId + "_" + DateUtil.date2Str(new Date(), DateUtil.FORMAT_YYYYMMDDHHMMSS)
              + ".mp3";
      String targetFileName2 = parentPath + "audio_recording_" + newDocId + "_" + DateUtil
          .date2Str(new Date(), DateUtil.FORMAT_YYYYMMDDHHMMSS) + ".mp3";

      //原mp3文件
      FileUtil.cutMp3(filePath, targetFileName1, 0, ratioTime);
      FileUtil.cutMp3(filePath, targetFileName2, ratioTime, duration);
      //      删除源文件
      File srcFile = new File(filePath);
      if (srcFile.exists()) {
        srcFile.delete();
      }
      //      更新记录

      oldRecord.setFilePath(targetFileName1);
      update(oldRecord);
      Record newRecord = new Record();
      newRecord.setDocId(newDocId);
      newRecord.setFilePath(targetFileName2);
      update(newRecord);
    }

  }

  /**
   * //   1、合并2个音频、删除2个音频，并更新数据记录
   * //   2、将传入docid的会话数据，更新其docid为本docid
   *
   * @param docId1
   * @param docId2
   */
  @Override
  public void mergeRecord(String docId1, String docId2) {
    //    1、合并2个音频、删除2个音频，并更新数据记录
    Record record1 = new Record();
    record1.setDocId(docId1);
    record1 = selectOne(record1);
    Record record2 = new Record();
    record2.setDocId(docId2);
    record2 = selectOne(record2);
    String filePath1 = record1.getFilePath();
    String filePath2 = record2.getFilePath();
    String newFilePath;
    if (StringUtils.isBlank(filePath1) || StringUtils.isBlank(filePath2)) {
      newFilePath = filePath1 + filePath2;
    } else {
      Path path = Paths.get("").toAbsolutePath().resolve("tempAudio");
      String parentPath = path.toAbsolutePath().toString() + File.separator;
      //目标文件
      newFilePath = parentPath + "merge_audio_recording_" + docId1 + "_" + DateUtil
          .date2Str(new Date(), DateUtil.FORMAT_YYYYMMDDHHMMSS) + ".mp3";
      FileUtil.mergeFile(filePath1, filePath2, newFilePath);

      //      删除源文件
      File srcFile1 = new File(filePath1);
      if (srcFile1.exists()) {
        srcFile1.delete();
      }
      File srcFile2 = new File(filePath2);
      if (srcFile2.exists()) {
        srcFile2.delete();
      }

    }
    //      更新记录
    Record newRecord = new Record();
    newRecord.setDocId(docId1);
    newRecord.setFilePath(newFilePath);
    update(newRecord);

    //    2、将传入docid的会话数据，更新其docid为本docid，删除历史参数

    Record record = new Record();
    record.setDocId(docId2);
    deleteByDocId(record);

    RecordParam recordParam = new RecordParam();
    recordParam.setDocId(docId2);
    recordParamService.deleteByDocId(recordParam);

    RecordData rdDto = new RecordData();
    rdDto.setDocId(docId1);
    RecordData condition = new RecordData();
    condition.setDocId(docId2);
    recordDataService.updateCut(rdDto, condition);
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

  @Override
  public void deleteByDocId(Record record) {
    this.baseMapper.delete(Wrappers.<Record>lambdaQuery().eq(Record::getDocId, record.getDocId()));
  }

}
