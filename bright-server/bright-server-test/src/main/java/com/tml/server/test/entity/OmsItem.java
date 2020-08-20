package com.tml.server.test.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 商品表 Entity
*
* @author JacksonTu
* @date 2020-08-20 11:49:45
*/
@Data
@TableName("t_oms_item")
@Excel("商品表")
public class OmsItem {

    /**
     * 商品主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名
     */
    @TableField("ITEM_NAME")
    @ExcelField(value = "商品名", required = true, maxLength = 200,
            comment = "提示：必填，长度不能超过200个字符")
    private String itemName;

    /**
     * 商品编号
     */
    @TableField("ITEM_CODE")
    @ExcelField(value = "商品编号", required = true, maxLength = 50,
            comment = "提示：必填，长度不能超过50个字符")
    private String itemCode;

    /**
     * 库存
     */
    @TableField("ITEM_STOCK")
    @ExcelField(value = "库存", required = true, maxLength = 11, regularExp = "[0-9]+",
            regularExpMessage = "必须是数字", comment = "提示: 必填，只能填写数字，并且长度不能超过11位")
    private Integer itemStock;

    /**
     * 采购时间
     */
    @TableField("PURCHASE_TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseTime;

    /**
     * 状态（1=是,0=否）
     */
    @TableField("STATUS")
    @ExcelField(value = "状态",required = true)
    private String status;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 创建用户ID
     */
    @TableField("CREATE_USER_ID")
    private Long createUserId;

    /**
     * 更新用户ID
     */
    @TableField("UPDATE_USER_ID")
    private Long updateUserId;

    /**
     * 部门ID
     */
    @TableField("DEPT_ID")
    private Long deptId;

}