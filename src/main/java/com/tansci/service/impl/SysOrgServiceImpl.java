package com.tansci.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tansci.domain.SysOrg;
import com.tansci.domain.SysOrgRole;
import com.tansci.mapper.SysOrgMapper;
import com.tansci.service.SysOrgRoleService;
import com.tansci.service.SysOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName： SysOrgServiceImpl.java
 * @ClassPath： com.tansci.service.impl.SysOrgServiceImpl.java
 * @Description： 组织机构
 * @Author： tanyp
 * @Date： 2021/10/22 16:29
 **/
@Slf4j
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

    @Autowired
    private SysOrgRoleService sysOrgRoleService;

    @Override
    public IPage<SysOrg> page(Page page, SysOrg sysOrg) {
        return this.baseMapper.selectPage(page,
                Wrappers.<SysOrg>lambdaQuery()
                        .eq(SysOrg::getDelFlag, 0)
                        .like(Objects.nonNull(sysOrg.getName()), SysOrg::getName, sysOrg.getName())
                        .orderByDesc(SysOrg::getCreateTime)
        );
    }

    @Override
    public List<SysOrg> list(SysOrg sysOrg) {
        List<SysOrg> orgList = this.baseMapper.selectList(Wrappers.lambdaQuery());
        List<SysOrg> newOrgList = orgList.stream().filter(item -> item.getParentId() == 0).map(item -> {
            item.setChildren(this.getChildrens(item, orgList));
            return item;
        }).sorted((item1, item2) -> {
            return (item1.getSort() == null ? 0 : item1.getSort()) - (item2.getSort() == null ? 0 : item2.getSort());
        }).collect(Collectors.toList());
        return newOrgList;
    }

    /**
     * @MonthName： getChildrens
     * @Description： 封装树形数据
     * @Author： tanyp
     * @Date： 2021/7/6 16:10
     * @Param： [dic, list]
     * @return： java.util.List<com.kuiper.qms.domain.SysOrg>
     **/
    public List<SysOrg> getChildrens(SysOrg org, List<SysOrg> list) {
        List<SysOrg> treeOrg = list.stream().filter(item -> Objects.equals(item.getParentId(), org.getId())).map(item -> {
            // 递归添加子数据
            List<SysOrg> childrens = getChildrens(item, list);
            item.setChildren(childrens);
            return item;
        }).sorted((item1, item2) -> { // 排序
            return (item1.getSort() == null ? 0 : item1.getSort()) - (item2.getSort() == null ? 0 : item2.getSort());
        }).collect(Collectors.toList());
        return treeOrg;
    }

    @Override
    public boolean del(Integer id) {
        List<SysOrg> orgList = this.baseMapper.getOrgChildrens(id);
        if (Objects.nonNull(orgList) && orgList.size() > 0) {
            List<Integer> ids = orgList.stream().map(SysOrg::getId).collect(Collectors.toList());
            this.baseMapper.deleteBatchIds(ids);
            return sysOrgRoleService.remove(Wrappers.<SysOrgRole>lambdaQuery().in(SysOrgRole::getOrgId, ids));
        }
        return false;
    }

    @Override
    public List<SysOrg> getOrgChildrens(Integer id) {
        return this.baseMapper.getOrgChildrens(id);
    }
}
