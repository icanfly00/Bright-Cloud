package com.tml.common.starter.web.jackson.converter;

import org.apache.commons.lang3.StringUtils;

/**
 * <code>
 * <pre>
 * 空字符串("")转换成Integer的null
 *
 * </pre>
 * </code>
 *
 * @author TuMingLong
 * @date 2020-04-02
 */
public class StringToIntegerUtil {

    public static Integer convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        Integer i = Integer.parseInt(source);
        return i;
    }
}
