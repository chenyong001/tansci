package com.tansci.service.impl.record;

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
import com.tansci.mapper.record.RecordParamMapper;
import com.tansci.mapper.system.RecordMapper;
import com.tansci.service.record.RecordParamService;
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
public class RecordParamServiceImpl extends ServiceImpl<RecordParamMapper, RecordParam> implements RecordParamService {

  @Override
  public IPage<RecordParam> page(Page page, RecordParam recordParam) {
    IPage<RecordParam> iPage = this.baseMapper.selectPage(page, Wrappers.<RecordParam>lambdaQuery()
        .eq(StringUtils.isNotBlank(recordParam.getDocId()), RecordParam::getDocId, recordParam.getDocId())
        .eq(RecordParam::getType, recordParam.getType()).orderByDesc(RecordParam::getCreateTime));
    return iPage;
  }

  @Override
  public boolean save(RecordParam recordParam) {
    this.baseMapper.insert(recordParam);
    return true;
  }

  @Override
  public RecordParam selectOne(RecordParam recordParam) {
    return this.baseMapper
        .selectOne(Wrappers.<RecordParam>lambdaQuery().eq(RecordParam::getId, recordParam.getId()));
  }

  @Override
  public List<RecordParam> selectListByType(RecordParam recordParam) {
    return this.baseMapper
        .selectList(Wrappers.<RecordParam>lambdaQuery().eq(RecordParam::getDocId, recordParam.getDocId())
        .eq(RecordParam::getType, recordParam.getType())
        );
  }

  @Override
  public RecordParam selectOneByName(RecordParam recordParam) {
    return this.baseMapper
        .selectOne(Wrappers.<RecordParam>lambdaQuery().eq(RecordParam::getDocId, recordParam.getDocId())
            .eq(RecordParam::getName, Constants.ANALYSIS_NAME)
        );
  }
  @Override
  public void update(RecordParam recordParam) {
    this.baseMapper
        .update(recordParam, Wrappers.<RecordParam>lambdaQuery().eq(RecordParam::getId, recordParam.getId()));
  }

  @Override
  public boolean del(RecordParam recordParam) {
    int row = this.baseMapper.deleteById(recordParam.getId());
    if (row > 0) {
      return true;
    }
    return false;
  }

}
