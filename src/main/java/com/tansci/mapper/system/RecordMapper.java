package com.tansci.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.domain.system.SysUser;
import com.tansci.domain.system.dto.SysUserDto;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName： TaskConfigMapper.java
 * @ClassPath： com.tansci.mapper.system.TaskConfigMapper.java
 * @Description： 任务配置
 * @Author： tanyp
 * @Date： 2022/2/25 9:45
 **/
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
  Page<Record> page(Page page, @Param("dto") Record dto);
}
