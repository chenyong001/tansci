package com.tansci.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tansci.domain.system.RecordStat;

import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName： TaskConfigMapper.java
 * @ClassPath： com.tansci.mapper.system.TaskConfigMapper.java
 * @Description： 任务配置
 * @Author： tanyp
 * @Date： 2022/2/25 9:45
 **/
@Mapper
public interface RecordStatMapper extends BaseMapper<RecordStat> {
}
