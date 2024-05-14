package com.ruoyi.business.uav.controller;


import com.ruoyi.business.uav.domain.UavConfig;
import com.ruoyi.business.uav.domain.vo.UavConfigVo;
import com.ruoyi.business.uav.service.UavConfigService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.controller
 * @Project：ruoyi-vue-service
 * @name：aa
 * @Date：2024/5/12 15:23
 * @Filename：UavConfigController
 */
@Api(tags = "无人机配置信息Controller")
@RestController
@RequestMapping("/uav/uavConfig")

public class UavConfigController extends BaseController {
    @Autowired
    private UavConfigService uavConfigService;

    @ApiOperation("查询无人机配置信息列表")
    @PreAuthorize("@ss.hasPermi('uav:uavConfig:list')")
    @GetMapping("/list")
    public TableDataInfo<UavConfigVo> list(UavConfigVo entity) {
        startPage();
        List uavConfig=uavConfigService.queryList(entity);
        return getDataTable(uavConfig);
    }

    @ApiOperation("查询无人机配置信息所有列表")
    @GetMapping("/listAll")
    public AjaxResult listAll(UavConfigVo entity) {
        return AjaxResult.success("查询成功", uavConfigService.list());
    }

    @ApiOperation("导出无人机配置信息列表")
    @PreAuthorize("@ss.hasPermi('uav:uavConfig:export')")
    @Log(title = "导出无人机配置信息列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UavConfigVo entity) {
        List<UavConfig> list = uavConfigService.list();
        ExcelUtil<UavConfig> util = new ExcelUtil<>(UavConfig.class);
        util.exportExcel(response, list, "无人机配置信息数据");
    }

    @ApiOperation("获取无人机配置信息详细信息")
    @PreAuthorize("@ss.hasPermi('uav:uavConfig:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success("查询成功", uavConfigService.queryById(id));
    }

    @ApiOperation("新增无人机配置信息")
    @PreAuthorize("@ss.hasPermi('uav:uavConfig:add')")
    @Log(title = "新增无人机配置信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody UavConfigVo entity) {
        try {
            return toAjax(uavConfigService.saveUavConfigVo(entity));
        } catch (Exception exception) {
            return AjaxResult.error(exception.getMessage());
        }
    }

    @ApiOperation("修改无人机配置信息")
    @PreAuthorize("@ss.hasPermi('uav:uavConfig:edit')")
    @Log(title = "修改无人机配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody UavConfigVo entity) {
        try {
            return toAjax(uavConfigService.updateConfigById(entity));
        } catch (Exception exception) {
            return AjaxResult.error(exception.getMessage());
        }
    }

    @ApiOperation("连接无人机")
    @PreAuthorize("@ss.hasPermi('uav:uavConfig:connect')")
    @Log(title = "连接无人机", businessType = BusinessType.UPDATE)
    @GetMapping("/connect/{id}")
    public AjaxResult connectUav(@PathVariable Long id) {
        try {
            return toAjax(uavConfigService.connectUav(id));
        } catch (Exception exception) {
            return AjaxResult.error(exception.getMessage());
        }
    }

    @ApiOperation("断开连接无人机")
    @PreAuthorize("@ss.hasPermi('uav:uavConfig:disconnect')")
    @Log(title = "断开连接无人机", businessType = BusinessType.UPDATE)
    @GetMapping("/disconnect/{id}")
    public AjaxResult disconnectUav(@PathVariable Long id) {
        try {
            return toAjax(uavConfigService.disconnectUav(id));
        } catch (Exception exception) {
            return AjaxResult.error(exception.getMessage());
        }
    }

    @ApiOperation("删除无人机配置信息")
    @PreAuthorize("@ss.hasPermi('uav:uavConfig:remove')")
    @Log(title = "删除无人机配置信息", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(uavConfigService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}

