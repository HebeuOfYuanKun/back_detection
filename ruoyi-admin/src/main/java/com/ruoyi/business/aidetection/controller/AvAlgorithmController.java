package com.ruoyi.business.aidetection.controller;

import com.ruoyi.business.aidetection.domain.vo.AvAlgorithmVo;
import com.ruoyi.business.aidetection.service.AvAlgorithmService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.controller
 * @Project：ruoyi-vue-service
 * @name：AvAl
 * @Date：2024/4/19 11:51
 * @Filename：AvAl
 */

@Api(tags = "算法Controller")
@RestController
@RequestMapping("/business/algorithm")
public class AvAlgorithmController extends BaseController {
    @Autowired
    private AvAlgorithmService avAlgorithmService;

    @ApiOperation("查询算法列表")
    @PreAuthorize("@ss.hasPermi('business:algorithm:list')")
    @GetMapping("/list")
    public TableDataInfo<AvAlgorithmVo> list(AvAlgorithmVo entity) {
        return avAlgorithmService.queryList(entity);
    }

    @ApiOperation("查询算法所有列表")
    @GetMapping("/listAll")
    public AjaxResult listAll(AvAlgorithmVo entity) {
        return AjaxResult.success("查询成功", avAlgorithmService.queryAll(entity));
    }

    @ApiOperation("导出算法列表")
    @PreAuthorize("@ss.hasPermi('business:algorithm:export')")
    @Log(title = "算法", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AvAlgorithmVo entity) {
        List<AvAlgorithmVo> list = avAlgorithmService.queryAll(entity);
        ExcelUtil<AvAlgorithmVo> util = new ExcelUtil<>(AvAlgorithmVo.class);
        util.exportExcel(response, list, "算法数据");
    }

    @ApiOperation("获取算法详细信息")
    @PreAuthorize("@ss.hasPermi('business:algorithm:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success("查询成功", avAlgorithmService.queryById(id));
    }

    @ApiOperation("新增算法")
    @PreAuthorize("@ss.hasPermi('business:algorithm:add')")
    @Log(title = "算法", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody AvAlgorithmVo entity) {
        return toAjax(avAlgorithmService.save(entity));
    }

    @ApiOperation("修改算法")
    @PreAuthorize("@ss.hasPermi('business:algorithm:edit')")
    @Log(title = "算法", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    public AjaxResult edit(@RequestBody AvAlgorithmVo entity) {
        return toAjax(avAlgorithmService.updateById(entity));
    }

    @ApiOperation("删除算法")
    @PreAuthorize("@ss.hasPermi('business:algorithm:remove')")
    @Log(title = "算法", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(avAlgorithmService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}

