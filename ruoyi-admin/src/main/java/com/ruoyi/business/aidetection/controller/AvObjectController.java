package com.ruoyi.business.aidetection.controller;
import com.ruoyi.business.aidetection.domain.AvObject;
import com.ruoyi.business.aidetection.domain.vo.AvObjectVo;
import com.ruoyi.business.aidetection.service.AvObjectService;
import com.ruoyi.common.annotation.Anonymous;
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
 * 识别物体Controller
 *
 * @author yuankun
 * @date 2024-04-06
 */
@Api(tags = "识别物体Controller")
@RestController
@RequestMapping("/business/object")

public class AvObjectController extends BaseController {
    @Autowired
    private   AvObjectService avObjectService;

    @ApiOperation("查询识别物体列表")
    //@PreAuthorize("@ss.hasPermi('business:object:list')")
    @Anonymous
    @GetMapping("/list")
    public TableDataInfo<AvObjectVo> list(AvObjectVo entity) {
        return avObjectService.queryList(entity);
    }

    @ApiOperation("查询识别物体所有列表")
    @Anonymous
    @GetMapping("/listAll")
    public AjaxResult listAll(AvObjectVo entity) {
        return AjaxResult.success("查询成功", avObjectService.list());
    }

    @ApiOperation("导出识别物体列表")
    @PreAuthorize("@ss.hasPermi('business:object:export')")
    @Log(title = "识别物体", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AvObjectVo entity) {
        List<AvObject> list = avObjectService.queryAll(entity);
        ExcelUtil<AvObject> util = new ExcelUtil<>(AvObject.class);
        util.exportExcel(response, list, "识别物体数据");
    }

    @ApiOperation("获取识别物体详细信息")
    @PreAuthorize("@ss.hasPermi('business:object:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success("查询成功", avObjectService.queryById(id));
    }

    @ApiOperation("新增识别物体")
    @PreAuthorize("@ss.hasPermi('business:object:add')")
    @Log(title = "识别物体", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody AvObjectVo entity) {
        return toAjax(avObjectService.save(entity));
    }

    @ApiOperation("修改识别物体")
    @PreAuthorize("@ss.hasPermi('business:object:edit')")
    @Log(title = "识别物体", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    public AjaxResult edit(@RequestBody AvObjectVo entity) {
        return toAjax(avObjectService.updateById(entity));
    }

    @ApiOperation("删除识别物体")
    @PreAuthorize("@ss.hasPermi('business:object:remove')")
    @Log(title = "识别物体", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(avObjectService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
