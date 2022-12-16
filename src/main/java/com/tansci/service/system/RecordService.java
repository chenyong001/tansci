package com.tansci.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tansci.domain.system.Record;

/**
 * @ClassName： TaskConfigService.java
 * @ClassPath： com.tansci.service.system.TaskConfigService.java
 * @Description： 任务配置
 * @Author： tanyp
 * @Date： 2022/2/25 9:45
 **/
public interface RecordService extends IService<Record> {

  IPage<Record> page(Page page, Record record);

  void cuttingRecord(String docId, int ratio);

  void mergeRecord(String docId1, String docId2);

  //    @Override
  @Override
  boolean save(Record record);

  Record selectOne(Record record);

  void update(Record record);

  //    boolean update(TaskConfig taskConfig);

      boolean del(Record record);

  void deleteByDocId(Record record);
}
