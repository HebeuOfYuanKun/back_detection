package com.ruoyi.business.aidetection.controller;

import com.ruoyi.business.aidetection.domain.AvAlarm;
import com.ruoyi.business.aidetection.domain.vo.AvAlarmVo;
import com.ruoyi.business.aidetection.service.AvAlarmService;
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
 * alarmController
 *
 * @author yuankun
 * @date 2024-04-06
 */
@Api(tags = "alarmController")
@RestController
@RequestMapping("/business/alarm")

public class AvAlarmController extends BaseController {
    @Autowired
    private AvAlarmService avAlarmService;

    @ApiOperation("查询alarm列表")
    @PreAuthorize("@ss.hasPermi('business:alarm:list')")
    @GetMapping("/list")
    public TableDataInfo<AvAlarm> list(AvAlarmVo entity) {
        startPage();
        //System.out.println(entity);
        List<AvAlarm> unsafeInfos = avAlarmService.queryList(entity);
        return getDataTable(unsafeInfos);
    }

    @ApiOperation("查询alarm所有列表")
    @GetMapping("/listAll")
    public AjaxResult listAll(AvAlarmVo entity) {
        return AjaxResult.success("查询成功", avAlarmService.queryAll(entity));
    }

    @ApiOperation("导出alarm列表")
    @PreAuthorize("@ss.hasPermi('business:alarm:export')")
    @Log(title = "alarm", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AvAlarmVo entity) {
        List<AvAlarm> list = avAlarmService.queryAll(entity);
        ExcelUtil<AvAlarm> util = new ExcelUtil<>(AvAlarm.class);
        util.exportExcel(response, list, "预警数据");
    }

    @ApiOperation("获取alarm详细信息")
    @PreAuthorize("@ss.hasPermi('business:alarm:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success("查询成功", avAlarmService.queryById(id));
    }

    @ApiOperation("新增alarm")
    @PreAuthorize("@ss.hasPermi('business:alarm:add')")
    @Log(title = "alarm", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody AvAlarmVo entity) {
        return toAjax(avAlarmService.save(entity));
    }

    @ApiOperation("修改alarm")
    @PreAuthorize("@ss.hasPermi('business:alarm:edit')")
    @Log(title = "alarm", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    public AjaxResult edit(@RequestBody AvAlarmVo entity) {
        return toAjax(avAlarmService.updateById(entity));
    }

    @ApiOperation("删除alarm")
    @PreAuthorize("@ss.hasPermi('business:alarm:remove')")
    @Log(title = "alarm", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(avAlarmService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}