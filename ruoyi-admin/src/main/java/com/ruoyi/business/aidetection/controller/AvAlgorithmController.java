package com.ruoyi.business.aidetection.controller;

import com.ruoyi.business.aidetection.domain.AvAlgorithm;
import com.ruoyi.business.aidetection.domain.vo.AvAlgorithmVo;
import com.ruoyi.business.aidetection.service.AvAlgorithmService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * algorithmController
 *
 * @author yuankun
 * @date 2024-11-16
 */
@Api(tags = "algorithmController")
@RestController
@RequestMapping("/business/algorithm")
public class AvAlgorithmController extends BaseController {

    @Autowired
    private AvAlgorithmService avAlgorithmService;

    @ApiOperation("查询algorithm列表")
    @PreAuthorize("@ss.hasPermi('business:algorithm:list')")
    @GetMapping("/list")
    public TableDataInfo<AvAlgorithmVo> list(AvAlgorithmVo entity) {
        startPage();
        return getDataTable(avAlgorithmService.queryList(entity));
    }

    @ApiOperation("查询algorithm所有列表")
    @GetMapping("/listAll")
    public AjaxResult listAll(AvAlgorithmVo entity) {
        return AjaxResult.success("查询成功", avAlgorithmService.queryAll(entity));
    }

    @ApiOperation("导出algorithm列表")
    @PreAuthorize("@ss.hasPermi('business:algorithm:export')")
    @Log(title = "algorithm", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AvAlgorithmVo entity) {
        List<AvAlgorithmVo> list = avAlgorithmService.queryAll(entity);
        ExcelUtil<AvAlgorithmVo> util = new ExcelUtil<>(AvAlgorithmVo.class);
        util.exportExcel(response, list, "algorithm数据");
    }

    @ApiOperation("获取algorithm详细信息")
    @PreAuthorize("@ss.hasPermi('business:algorithm:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success("查询成功", avAlgorithmService.queryById(id));
    }

    @ApiOperation("新增algorithm")
    @PreAuthorize("@ss.hasPermi('business:algorithm:add')")
    @Log(title = "algorithm", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody AvAlgorithm entity) {
        return toAjax(avAlgorithmService.save(entity));
    }

    @ApiOperation("修改algorithm")
    @PreAuthorize("@ss.hasPermi('business:algorithm:edit')")
    @Log(title = "algorithm", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    public AjaxResult edit(@RequestBody AvAlgorithm entity) {
        return toAjax(avAlgorithmService.updateById(entity));
    }

    @ApiOperation("删除algorithm")
    @PreAuthorize("@ss.hasPermi('business:algorithm:remove')")
    @Log(title = "algorithm", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(avAlgorithmService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
