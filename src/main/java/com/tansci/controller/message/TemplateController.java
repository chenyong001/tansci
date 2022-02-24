package com.tansci.controller.message;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tansci.common.WrapMapper;
import com.tansci.common.Wrapper;
import com.tansci.common.annotation.Log;
import com.tansci.common.constant.Constants;
import com.tansci.domain.message.Template;
import com.tansci.domain.message.dto.TemplateDto;
import com.tansci.service.message.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName： TemplateController
 * @ClassPath： com.tansci.controller.message.TemplateController.java
 * @Description： 模板配置
 * @Author： tanyp
 * @Date： 2021/4/22 10:39
 **/
@RestController
@AllArgsConstructor
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Log(modul = "模板配置-模板列表", type = Constants.SELECT, desc = "模板列表")
    @GetMapping("/page")
    public Wrapper<IPage<Template>> page(Page page, TemplateDto dto) {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, templateService.page(page, dto));
    }

    @Log(modul = "模板配置-添加模板", type = Constants.INSERT, desc = "添加模板")
    @PostMapping("/save")
    public Wrapper save(@RequestBody Template template) {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, templateService.saveTemplate(template));
    }

    @Log(modul = "模板配置-删除模板", type = Constants.DELETE, desc = "删除模板")
    @GetMapping("/delete")
    public Wrapper delete(@RequestParam String id) {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, templateService.delTemplate(id));
    }

    @Log(modul = "模板配置-根据id修改模板", type = Constants.UPDATE, desc = "根据id修改模板")
    @PostMapping("/update")
    public Wrapper update(@RequestBody Template template) {
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, templateService.updateTemplate(template));
    }

}
