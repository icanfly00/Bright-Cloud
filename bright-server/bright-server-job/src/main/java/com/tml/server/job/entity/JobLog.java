package com.tml.server.job.entity;

import com.tml.common.core.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@TableName("t_schedule_job_log")
@Excel("调度日志信息表")
public class JobLog implements Serializable {

    /**
     * 任务执行成功
     */
    public static final String JOB_SUCCESS = "0";
    /**
     * 任务执行失败
     */
    public static final String JOB_FAIL = "1";
    private static final long serialVersionUID = -7114915445674333148L;
    @TableId(value = "LOG_ID", type = IdType.AUTO)
    private Long logId;

    @TableField("JOB_ID")
    private Long jobId;
    /**
     * 任务类型 0-本地方法，1-远程服务
     */
    @TableField("JOB_TYPE")
    @ExcelField(value = "任务类型")
    private String jobType;
    /**
     *Bean名称
     */
    @TableField("BEAN_NAME")
    @ExcelField(value = "Bean名称")
    private String beanName;
    /**
     * 方法名称
     */
    @TableField("METHOD_NAME")
    @ExcelField(value = "方法名称")
    private String methodName;
    /**
     * 服务名
     */
    @TableField("SERVICE_ID")
    @ExcelField(value = "服务名")
    private String serviceId;
    /**
     * 请求路径
     */
    @TableField("PATH")
    @ExcelField(value = "请求路径")
    private String path;
    /**
     * 请求类型
     */
    @TableField("REQUEST_METHOD")
    @ExcelField(value = "请求类型")
    private String requestMethod;
    /**
     * 响应类型
     */
    @TableField("CONTENT_TYPE")
    @ExcelField(value = "响应类型")
    private String contentType;

    @TableField("PARAMS")
    @ExcelField(value = "方法参数")
    private String params;
    /**
     * 告警邮箱
     */
    @TableField("ALARM_MAIL")
    @ExcelField(value = "告警邮箱")
    private String alarmMail;
    /**
     * 状态
     */
    @TableField("STATUS")
    @ExcelField(value = "状态", writeConverterExp = "0=成功,1=失败")
    private String status;
    /**
     * 异常信息
     */
    @TableField("ERROR")
    @ExcelField(value = "异常信息")
    private String error;
    /**
     * 耗时（毫秒）
     */
    @TableField("TIMES")
    @ExcelField(value = "耗时（毫秒）")
    private Long times;
    /**
     *
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "执行时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @TableField(exist = false)
    private  String createTimeFrom;
    @TableField(exist = false)
    private  String createTimeTo;

}
