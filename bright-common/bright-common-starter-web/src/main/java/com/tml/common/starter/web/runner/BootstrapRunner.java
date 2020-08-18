package com.tml.common.starter.web.runner;

import com.tml.common.core.utils.BrightUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@RequiredArgsConstructor
public class BootstrapRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final Environment environment;

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            BrightUtil.printSystemUpBanner(environment);
        }
    }
}
