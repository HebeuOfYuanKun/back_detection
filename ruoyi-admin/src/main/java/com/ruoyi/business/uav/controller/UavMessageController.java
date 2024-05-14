package com.ruoyi.business.uav.controller;

import com.ruoyi.business.uav.domain.UavMessage;
import com.ruoyi.business.uav.domain.vo.UavMessageVo;
import com.ruoyi.business.uav.service.UavMessageService;
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
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.controller
 * @Project：ruoyi-vue-service
 * @name：UavMessageController
 * @Date：2024/5/14 16:35
 * @Filename：UavMessageController
 */
@Api(tags = "messageController")
@RestController
@RequestMapping("/uav/message")

public class UavMessageController extends BaseController {
    @Autowired
    private UavMessageService uavMessageService;

    @ApiOperation("查询message列表")
    @PreAuthorize("@ss.hasPermi('uav:message:list')")
    @GetMapping("/list")
    public TableDataInfo<UavMessage> list(UavMessageVo entity) {
        startPage();
        return getDataTable(uavMessageService.queryList(entity));
    }

    @ApiOperation("查询message所有列表")
    @GetMapping("/listAll")
    public AjaxResult listAll(UavMessageVo entity) {
        return AjaxResult.success("查询成功", uavMessageService.list());
    }

    @ApiOperation("导出message列表")
    @PreAuthorize("@ss.hasPermi('uav:message:export')")
    @Log(title = "message", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UavMessageVo entity) {
        List<UavMessage> list = uavMessageService.list();
        ExcelUtil<UavMessage> util = new ExcelUtil<>(UavMessage.class);
        util.exportExcel(response, list, "message数据");
    }

    @ApiOperation("获取message详细信息")
    @PreAuthorize("@ss.hasPermi('uav:message:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success("查询成功", uavMessageService.queryById(id));
    }

    @ApiOperation("新增message")
    @PreAuthorize("@ss.hasPermi('uav:message:add')")
    @Log(title = "message", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody UavMessageVo entity) {
        try {
            return toAjax(uavMessageService.add(entity));
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }

    }

    @ApiOperation("修改message")
    @PreAuthorize("@ss.hasPermi('uav:message:edit')")
    @Log(title = "message", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    public AjaxResult edit(@RequestBody UavMessageVo entity) {
        try {
            return toAjax(uavMessageService.editById(entity));
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation("删除message")
    @PreAuthorize("@ss.hasPermi('uav:message:remove')")
    @Log(title = "message", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(uavMessageService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
