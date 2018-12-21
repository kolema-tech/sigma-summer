package com.sigma.sigmadatamybatisrest;

import com.sigma.sigmacore.web.SigmaRequest;
import com.sigma.sigmacore.web.SigmaResponse;
import com.sigma.sigmadatamybatiscore.context.AuditContextHandler;
import com.sigma.sigmadatamybatiscore.entity.SigmaPage;
import com.sigma.sigmadatamybatiscore.service.BaseService;
import com.sigma.sigmadatamybatiscore.util.StringEscapeEditor;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class BaseController<Biz extends BaseService, Entity, PK> {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected Biz biz;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringEscapeEditor());
        binder.registerCustomEditor(String[].class, new StringEscapeEditor());
    }

    @ApiOperation("新增单个对象")
    @PostMapping(value = "add")
    @ResponseBody
    public SigmaResponse<Boolean> add(@RequestBody SigmaRequest<Entity> entity) {
        biz.insertSelective(entity.getData());
        return SigmaResponse.ok(true);
    }

    @ApiOperation("查询单个对象")
    @PostMapping(value = "id")
    @ResponseBody
    public SigmaResponse<Entity> get(@RequestBody SigmaRequest<PK> id) {
        return SigmaResponse.ok((Entity) biz.selectByPrimaryKey(id.getData()));
    }

    @ApiOperation("更新单个对象")
    @PostMapping(value = "update")
    @ResponseBody
    public SigmaResponse<Boolean> update(@RequestBody SigmaRequest<Entity> entity) {
        biz.updateByPrimaryKeySelective(entity.getData());
        return SigmaResponse.ok(true);
    }

    @ApiOperation("移除单个对象")
    @PostMapping(value = "delete")
    @ResponseBody
    public SigmaResponse<Boolean> delete(@RequestBody SigmaRequest<PK> id) {
        biz.delete(id.getData());
        return SigmaResponse.ok(true);
    }

    @ApiOperation("获取所有数据")
    @PostMapping(value = "list")
    @ResponseBody
    public SigmaResponse<List<Entity>> all() {
        return SigmaResponse.ok(biz.selectAll());
    }

    @ApiOperation("分页获取数据")
    @PostMapping(value = "page")
    @ResponseBody
    public SigmaResponse list(@RequestParam SigmaRequest<Map<String, Object>> params) {
        //查询列表数据
        SigmaPage query = new SigmaPage(params.getData());
//        return SigmaResponse.ok(biz.selectByQuery(query));
        return null;
    }

    @ApiOperation("获取当前用户ID")
    @PostMapping(value = "user")
    @ResponseBody
    public SigmaResponse<String> getCurrentUserId() {
        return SigmaResponse.ok(AuditContextHandler.getUserId());
    }
}
