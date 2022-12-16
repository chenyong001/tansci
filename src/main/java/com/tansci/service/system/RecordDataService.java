package com.tansci.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tansci.domain.system.RecordData;
import com.tansci.domain.system.TaskConfig;

import java.util.List;

/**
 * @ClassName： TaskConfigService.java
 * @ClassPath： com.tansci.service.system.TaskConfigService.java
 * @Description： 任务配置
 * @Author： tanyp
 * @Date： 2022/2/25 9:45
 **/
public interface RecordDataService extends IService<RecordData> {

  IPage<RecordData> page(Page page, RecordData recordData);

  //    @Override
  @Override
  boolean save(RecordData recordData);

  boolean saveBatch(List<RecordData> recordDataList);

  Integer countRecord(RecordData recordData);

  List<RecordData> selectList(RecordData dto);

  RecordData selectFirstByDocId(RecordData dto);

  RecordData selectEndByDocId(RecordData dto);

  boolean update(RecordData recordData);


  boolean updateCut(RecordData dto, RecordData condition);

  RecordData selectById(int id);

  //    boolean del(TaskConfigDto dto);

}
