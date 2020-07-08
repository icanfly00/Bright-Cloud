package com.tml.common.util;

import com.alibaba.fastjson.JSON;
import com.tml.common.api.CommonResult;
import com.tml.common.entity.RestUserDetails;
import com.tml.common.util.BeanConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


/**
 * @Dercription 安全服务工具类
 * @Author TuMingLong
 * @Date 2020/4/21 15:46
 */
@Slf4j
public class SecurityUtil {

    public void writeJavaScript(CommonResult commonResult, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(commonResult));
        printWriter.flush();
    }

    /**
     * 获取Authentication
     */
    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    private static OAuth2Authentication getOauth2Authentication() {
        return (OAuth2Authentication) getAuthentication();
    }

    /**
     * 获取用户
     *
     * @return
     */
    public static RestUserDetails getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            OAuth2Request clientToken = oAuth2Authentication.getOAuth2Request();
            System.out.println(oAuth2Authentication);
            if (authentication.getPrincipal() instanceof RestUserDetails) {
                return (RestUserDetails) authentication.getPrincipal();
            }
            else if (authentication.getPrincipal() instanceof Map) {
                return BeanConvertUtil.mapToObject((Map) authentication.getPrincipal(), RestUserDetails.class);
            } else {
                RestUserDetails restUserDetails = new RestUserDetails();
                restUserDetails.setClientId(clientToken.getClientId());
                restUserDetails.setAuthorities(clientToken.getAuthorities());
                return restUserDetails;
            }
        }
        return null;
    }

    /**
     * 是否拥有权限
     *
     * @param authority
     * @return
     */
    public static Boolean hasAuthority(String authority) {
        RestUserDetails auth = getUser();
        if (auth == null) {
            return false;
        }
        if (AuthorityUtils.authorityListToSet(auth.getAuthorities()).contains(authority)) {
            return true;
        }
        return false;
    }
}
