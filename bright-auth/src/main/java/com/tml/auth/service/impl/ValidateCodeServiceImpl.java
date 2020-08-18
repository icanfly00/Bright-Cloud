package com.tml.auth.service.impl;

import com.tml.auth.properties.BrightAuthProperties;
import com.tml.auth.properties.BrightValidateCodeProperties;
import com.tml.auth.service.ValidateCodeService;
import com.tml.common.core.entity.constant.BrightConstant;
import com.tml.common.core.entity.constant.ImageTypeConstant;
import com.tml.common.core.entity.constant.ParamsConstant;
import com.tml.common.core.exception.ValidateCodeException;
import com.tml.common.starter.redis.service.RedisService;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码服务
 *
 * @Author TuMingLong
 */
@Service
@RequiredArgsConstructor
public class ValidateCodeServiceImpl implements ValidateCodeService {

    private final RedisService redisService;
    private final BrightAuthProperties properties;
    private final BrightValidateCodeProperties codeProperties;

    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        String key = request.getParameter(ParamsConstant.VALIDATE_CODE_KEY);
        if (StringUtils.isBlank(key)) {
            throw new ValidateCodeException("验证码key不能为空");
        }
        BrightValidateCodeProperties code = codeProperties;
        setHeader(response, code.getType());

        Captcha captcha = createCaptcha(code);
        redisService.set(BrightConstant.CODE_PREFIX + key, StringUtils.lowerCase(captcha.text()), code.getTime());
        captcha.out(response.getOutputStream());
    }

    @Override
    public void check(String key, String value) throws ValidateCodeException {
        Object codeInRedis = redisService.get(BrightConstant.CODE_PREFIX + key);
        if (StringUtils.isBlank(value)) {
            throw new ValidateCodeException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new ValidateCodeException("验证码不正确");
        }
    }

    private Captcha createCaptcha(BrightValidateCodeProperties code) {
        Captcha captcha = null;
        if (StringUtils.equalsIgnoreCase(code.getType(), ImageTypeConstant.GIF)) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

    private void setHeader(HttpServletResponse response, String type) {
        if (StringUtils.equalsIgnoreCase(type, ImageTypeConstant.GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }
}
