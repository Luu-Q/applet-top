package com.data.mybatisplus.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ln
 * @since 2019-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ServiceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("parent_id")
    private Integer parentId;

    /**
     * 产品编号
     */
    @TableField("item_no")
    private String itemNo;

    @TableField("displayorder")
    private Integer displayorder;

    @TableField("item_path")
    private String itemPath;

    /**
     * 单位
     */
    @TableField("unit")
    private String unit;

    /**
     * 简洁描述
     */
    @TableField("desall")
    private String desall;

    /**
     * 服务描述
     */
    @TableField("described")
    private String described;

    /**
     * 是否是小时类服务
     */
    @TableField("ifHour")
    private Integer ifHour;

    /**
     * 最少可用空闲时间
     */
    @TableField("minHour")
    private Integer minHour;

    /**
     * 最少预占用时间
     */
    @TableField("lockHour")
    private Integer lockHour;

    /**
     * 图标地址
     */
    @TableField("icon")
    private String icon;

    /**
     * 网页头图地址
     */
    @TableField("header_url")
    private String headerUrl;

    /**
     * 购买上限
     */
    @TableField("up_limit")
    private Integer upLimit;

    /**
     * 购买下限
     */
    @TableField("low_limit")
    private Integer lowLimit;

    /**
     * 服务需要男人数量7-26
     */
    @TableField("man_num")
    private Integer manNum;

    /**
     * 0地址不需要面积1地址必填面积
     */
    @TableField("need_area")
    private Integer needArea;

    /**
     * 默认值，大于购买下限
     */
    @TableField("default_val")
    private Integer defaultVal;

    /**
     * 0不可以随时点击完成 1可以随时点击完成
     */
    @TableField("is_whenever")
    private Integer isWhenever;

    /**
     * 0未知1男2女
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 产品状态：0未上架，1上架，2下架，3上架审核中
     */
    @TableField("status")
    private Integer status;

    /**
     * 0正常1删除
     */
    @TableField("is_del")
    private Integer isDel;

    @TableField("create_time")
    private Long createTime;

    @TableField("update_time")
    private Long updateTime;


}
