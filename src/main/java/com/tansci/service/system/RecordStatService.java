package com.tansci.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tansci.domain.system.RecordStat;

/**
 * @ClassName： TaskConfigService.java
 * @ClassPath： com.tansci.service.system.TaskConfigService.java
 * @Description： 任务配置
 * @Author： tanyp
 * @Date： 2022/2/25 9:45
 **/
public interface RecordStatService extends IService<RecordStat> {

//  IPage<RecordStat> page(Page page, RecordStat record);

  //    @Override
  @Override
  boolean save(RecordStat record);

  RecordStat selectOne(RecordStat record);

  void update(RecordStat record);


//      boolean del(RecordStat record);

}
