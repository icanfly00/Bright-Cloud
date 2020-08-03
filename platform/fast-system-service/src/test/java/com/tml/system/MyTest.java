package com.tml.system;


import com.tml.common.util.AddressUtil;
import org.apache.commons.text.StringEscapeUtils;

/**
 * @Description com.tml
 * @Author TuMingLong
 * @Date 2020/4/6 16:25
 */
public class MyTest {
    public static void main(String[] args) {

        System.out.println(AddressUtil.getCityInfo("127.0.0.1"));
        String sysPredicates="[{\"name\":\"Path\",\"args\":{\"pattern\":\"/system/**\"}}]";
        System.out.println(StringEscapeUtils.escapeJson(sysPredicates));


    }
}
