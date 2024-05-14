package com.ruoyi.business.uav.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav
 * @Project：ruoyi-vue-service
 * @name：uav
 * @Date：2024/5/12 15:21
 * @Filename：uav
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UavConfig extends BaseEntity {

    /**
     * $column.columnComment
     */
    private Long id;


    /**
     * 设备编号
     */
    @Excel(name = "设备编号")
    private String uavId;

    /**
     * 连接id
     */
    @Excel(name = "连接id")
    private String uavClient;

    /**
     * 无人机型号
     */
    @Excel(name = "无人机型号")
    private String uavModel;

    /**
     * 无人机图片
     */
    @Excel(name = "无人机图片")
    private String uavImage;

    /**
     * 无人机识别状态
     */
    @Excel(name = "无人机识别状态")
    private Integer uavAiState;

    /**
     * 无人机是否开机 1开机，0关机，2未知
     */
    @Excel(name = "无人机是否开机 1开机，0关机，2未知")
    private Integer uavState;

    /**
     * 无人机默认开启的布控ID
     */
    @Excel(name = "无人机默认开启的布控ID")
    private String uavControlId;

    /**
     * 连接用户名
     */
    @Excel(name = "连接用户名")
    private String username;

    /**
     * 连接密码
     */
    @Excel(name = "连接密码")
    private String password;

    /**
     * 连接地址
     */
    @Excel(name = "连接地址")
    private String address;

    /**
     * 连接端口
     */
    @Excel(name = "连接端口")
    private Long port;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Long state;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 无人机品牌
     */
    @Excel(name = "无人机品牌")
    private String uavBrand;

}
