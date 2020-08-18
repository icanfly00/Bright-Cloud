package com.tml.common.starter.web.jackson.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * <code>
 *
 * </code>
 *
 * @author TuMingLong
 * @date 2020-04-02
 */
public class StringToDoubleConverter implements Converter<String, Double> {

    @Override
    public Double convert(String source) {
        return StringToDoubleUtil.convert(source);
    }
}
