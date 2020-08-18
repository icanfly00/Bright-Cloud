package com.tml.common.core.validator;

import com.tml.common.core.annotation.IsMobile;
import com.tml.common.core.entity.constant.RegexpConstant;
import com.tml.common.core.utils.BrightUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 * @Author TuMingLong
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE;
                return BrightUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
