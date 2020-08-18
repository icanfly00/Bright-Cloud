package com.tml.server.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.api.system.entity.SysLog;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.BrightConstant;
import com.tml.common.core.utils.SortUtil;
import com.tml.server.system.mapper.SysLogMapper;
import com.tml.server.system.service.ISysLogService;
import com.tml.server.system.utils.AddressUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 日志业务层实现
 * @since 2020-08-10 20:30
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    private final ObjectMapper objectMapper;

    @Override
    public IPage<SysLog> findLogs(SysLog log, QueryRequest request) {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(log.getUsername())) {
            queryWrapper.lambda().eq(SysLog::getUsername, log.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(log.getOperation())) {
            queryWrapper.lambda().like(SysLog::getOperation, log.getOperation());
        }
        if (StringUtils.isNotBlank(log.getLocation())) {
            queryWrapper.lambda().like(SysLog::getLocation, log.getLocation());
        }
        if (StringUtils.isNotBlank(log.getCreateTimeFrom()) && StringUtils.isNotBlank(log.getCreateTimeTo())) {
            queryWrapper.lambda()
                    .ge(SysLog::getCreateTime, log.getCreateTimeFrom())
                    .le(SysLog::getCreateTime, log.getCreateTimeTo());
        }

        Page<SysLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", BrightConstant.ORDER_DESC, true);

        return this.page(page, queryWrapper);
    }

    @Override
    public void deleteLogs(String[] logIds) {
        List<String> list = Arrays.asList(logIds);
        baseMapper.deleteBatchIds(list);
    }

    @Override
    public void saveLog(ProceedingJoinPoint point, Method method, String ip, String operation, String username, long start) {
        SysLog log = new SysLog();
        log.setIp(ip);

        log.setUsername(username);
        log.setTime(System.currentTimeMillis() - start);
        log.setOperation(operation);

        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        log.setMethod(className + "." + methodName + "()");

        Object[] args = point.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            log.setParams(params.toString());
        }
        log.setCreateTime(new Date());
        log.setLocation(AddressUtil.getCityInfo(ip));
        // 保存系统日志
        save(log);
    }

    @SuppressWarnings("all")
    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Map) {
                    Set set = ((Map) args[i]).keySet();
                    List<Object> list = new ArrayList<>();
                    List<Object> paramList = new ArrayList<>();
                    for (Object key : set) {
                        list.add(((Map) args[i]).get(key));
                        paramList.add(key);
                    }
                    return handleParams(params, list.toArray(), paramList);
                } else {
                    if (args[i] instanceof Serializable) {
                        Class<?> aClass = args[i].getClass();
                        try {
                            aClass.getDeclaredMethod("toString", new Class[]{null});
                            // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                        } catch (NoSuchMethodException e) {
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                        }
                    } else if (args[i] instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) args[i];
                        params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
                    } else {
                        params.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
                    }
                }
            }
        } catch (Exception ignore) {
            params.append("参数解析失败");
        }
        return params;
    }
}
