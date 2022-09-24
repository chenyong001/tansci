package com.tansci.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tansci.domain.system.Record;

import java.util.List;

/**
 * @ClassName： TaskConfigService.java
 * @ClassPath： com.tansci.service.system.TaskConfigService.java
 * @Description： 任务配置
 * @Author： tanyp
 * @Date： 2022/2/25 9:45
 **/
public interface RecordService extends IService<Record> {

  IPage<Record> page(Page page, Record record);

  //    @Override
  @Override
  boolean save(Record record);

  Record selectOne(Record record);

  void update(Record record);

  //    boolean update(TaskConfig taskConfig);

  //    boolean del(TaskConfigDto dto);

}