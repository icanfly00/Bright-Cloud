package com.tml.server.job.entity;

import com.tml.common.core.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@TableName("t_schedule_job")
@Excel("定时任务信息表")
public class Job implements Serializable {

    /**
     * 任务调度参数 key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
    private static final long serialVersionUID = 400066840871805700L;
    @TableId(value = "JOB_ID", type = IdType.AUTO)
    private Long jobId;
    /**
     * 任务类型 0-本地方法，1-远程服务
     */
    @TableField("JOB_TYPE")
    @NotBlank(message = "{required}")
    @ExcelField(value = "任务类型")
    private String jobType;
    /**
     * Bean名称
     */
    @TableField("BEAN_NAME")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "Bean名称")
    private String beanName;
    /**
     * 方法名称
     */
    @TableField("METHOD_NAME")
    @Size(max = 50, message = "{noMoreThan}")
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
    /**
     * 方法参数
     */
    @TableField("PARAMS")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "方法参数")
    private String params;
    /**
     * 告警邮箱
     */
    @TableField("ALARM_MAIL")
    @ExcelField(value = "告警邮箱")
    private String alarmMail;
    /**
     * Cron表达式
     */
    @TableField("CRON_EXPRESSION")
    @NotBlank(message = "{required}")
    @ExcelField(value = "Cron表达式")
    private String cronExpression;
    /**
     * 状态
     */
    @TableField("STATUS")
    @ExcelField(value = "状态", writeConverterExp = "0=正常,1=暂停")
    private String status;
    /**
     * 备注
     */
    @TableField("REMARK")
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "备注")
    private String remark;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;
    /**
     * 方法名称
     */
    @TableField(exist = false)
    private  String createTimeFrom;
    /**
     * 方法名称
     */
    @TableField(exist = false)
    private  String createTimeTo;

    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL("0"),
        /**
         * 暂停
         */
        PAUSE("1");

        private String value;

        ScheduleStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
