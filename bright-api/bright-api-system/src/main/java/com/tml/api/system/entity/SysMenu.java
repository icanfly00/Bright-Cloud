package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tml.common.core.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 菜单信息表
 * @since 2020-08-10 20:30
 */
@Data
@TableName("t_sys_menu")
@Excel("菜单信息表")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 7187628714679791771L;
    /**
     * 菜单
     */
    public static final String TYPE_MENU = "0";
    /**
     * 按钮
     */
    public static final String TYPE_BUTTON = "1";
    public static final Long TOP_MENU_ID = 0L;
    /**
     * 菜单/按钮ID
     */
    @TableId(value = "MENU_ID", type = IdType.AUTO)
    private Long menuId;
    /**
     * 上级菜单ID
     */
    @TableField("PARENT_ID")
    private Long parentId;
    /**
     * 菜单/按钮名称
     */
    @TableField("MENU_NAME")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    @ExcelField(value = "菜单/按钮名称")
    private String menuName;
    /**
     * 菜单URL
     */
    @TableField("PATH")
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "菜单URL")
    private String path;
    /**
     * 对应Vue组件
     */
    @TableField("COMPONENT")
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "对应Vue组件")
    private String component;
    /**
     * 权限标识
     */
    @TableField("PERMS")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "权限标识")
    private String perms;
    /**
     * 图标
     */
    @TableField("ICON")
    @ExcelField(value = "图标")
    private String icon;
    /**
     * 类型 0菜单 1按钮
     */
    @TableField("TYPE")
    @NotBlank(message = "{required}")
    @ExcelField(value = "类型", writeConverterExp = "0=菜单,1=按钮")
    private String type;
    /**
     * 排序
     */
    @TableField("ORDER_NUM")
    @ExcelField(value = "排序")
    private Integer orderNum;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    private transient String createTimeFrom;

    private transient String createTimeTo;

}