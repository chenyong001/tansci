package com.tansci.service.record;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordParam;

import java.util.List;

/**
 * @ClassName： TaskConfigService.java
 * @ClassPath： com.tansci.service.system.TaskConfigService.java
 * @Description： 任务配置
 * @Author： tanyp
 * @Date： 2022/2/25 9:45
 **/
public interface RecordParamService extends IService<RecordParam> {

  IPage<RecordParam> page(Page page, RecordParam recordParam);

  @Override
  boolean save(RecordParam recordParam);

  RecordParam selectOne(RecordParam recordParam);

  List<RecordParam> selectListByType(RecordParam recordParam);

  RecordParam selectOneByName(RecordParam recordParam);

  void update(RecordParam recordParam);

  boolean del(RecordParam recordParam);

  void deleteByDocId(RecordParam recordParam);
}
