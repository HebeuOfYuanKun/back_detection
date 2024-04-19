package com.ruoyi.business.aidetection.controller;

import com.ruoyi.business.aidetection.config.Analyzer;
import com.ruoyi.business.aidetection.config.ZLMediaKit;
import com.ruoyi.business.aidetection.domain.AvAlarm;
import com.ruoyi.business.aidetection.domain.AvControl;
import com.ruoyi.business.aidetection.domain.vo.AvControlVo;
import com.ruoyi.business.aidetection.service.AvControlService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.utils.ControlCodeGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.controller
 * @Project：ruoyi-vue-service
 * @name：AvController
 * @Date：2024/4/9 11:28
 * @Filename：AvController
 */
@Api(tags = "controlController")
@RestController
@RequestMapping("/business/control")

public class AvControlController extends BaseController {
    @Autowired
    private AvControlService avControlService;
    @Autowired
    private Analyzer analyzer;
    @Autowired
    private ZLMediaKit zlMediaKit;
    
    @ApiOperation("查询control列表")
    @PreAuthorize("@ss.hasPermi('business:control:list')")
    @GetMapping("/list")
    public Map<String,Object> list(AvControlVo entity) {
        //startPage();
        Map<String,Object> avControlList= avControlService.queryList(entity);

        return avControlList;
    }

    @ApiOperation("查询control所有列表")
    @GetMapping("/listAll")
    public AjaxResult listAll(AvControlVo entity) {
        return AjaxResult.success("查询成功", avControlService.queryAll(entity));
    }

    @ApiOperation("导出control列表")
    @PreAuthorize("@ss.hasPermi('business:control:export')")
    @Log(title = "control", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AvControlVo entity) {
        List<AvControlVo> list = avControlService.queryAll(entity);
        ExcelUtil<AvControlVo> util = new ExcelUtil<>(AvControlVo.class);
        util.exportExcel(response, list, "control数据");
    }

    @ApiOperation("获取control详细信息")
    @PreAuthorize("@ss.hasPermi('business:control:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success("查询成功", avControlService.getById(id));
    }

    /**
     * 待开发保存的视频流是否推流，默认写死推流
     */
    @ApiOperation("新增control")
    @PreAuthorize("@ss.hasPermi('business:control:add')")
    @Log(title = "control", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody AvControlVo entity) {
        entity.setCode(ControlCodeGenerator.genControlCode());
        entity.setPushStreamApp("analyzer");
        entity.setPushStreamName(entity.getCode());
        entity.setPushStream(1L);
        return toAjax(avControlService.save(entity));
    }

    @ApiOperation("加入识别")
    @PreAuthorize("@ss.hasPermi('business:control:detection')")
    @Log(title = "detection", businessType = BusinessType.INSERT)
    @PostMapping("detection/{id}")
    public Map<String, Object> addDetection(@PathVariable("id") Long id) {
        AvControl avControlVo = avControlService.getById(id);
        Map<String, Object> map = analyzer.controlAdd(avControlVo.getCode(), avControlVo.getAlgorithmCode(), avControlVo.getObjectCode(), avControlVo.getMinInterval(),
                avControlVo.getClassThresh(), avControlVo.getOverlapThresh(), zlMediaKit.getRtspUrl(avControlVo.getStreamApp(), avControlVo.getStreamName()),
                avControlVo.getPushStream(), zlMediaKit.getRtspUrl(avControlVo.getPushStreamApp(), avControlVo.getPushStreamName()));
        return map;
    }

    @ApiOperation("取消识别")
    @PreAuthorize("@ss.hasPermi('business:control:cancel')")
    @Log(title = "detection", businessType = BusinessType.INSERT)
    @DeleteMapping("detection/{id}")
    public Map<String, Object> deleteDetection(@PathVariable("id") Long id) {
        AvControl avControlVo = avControlService.getById(id);
        Map<String, Object> map = analyzer.controlCancel(avControlVo.getCode());
        return map;
    }


    @ApiOperation("修改control")
    @PreAuthorize("@ss.hasPermi('business:control:edit')")
    @Log(title = "control", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    public AjaxResult edit(@RequestBody AvControlVo entity) {
        return toAjax(avControlService.updateById(entity));
    }

    @ApiOperation("删除control")
    @PreAuthorize("@ss.hasPermi('business:control:remove')")
    @Log(title = "control", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(avControlService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}