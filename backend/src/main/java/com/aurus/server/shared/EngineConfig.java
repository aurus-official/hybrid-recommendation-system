package com.aurus.server.shared;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngineConfig {

    @Bean
    KieContainer kieContainer() {
        return KieServices.Factory.get().getKieClasspathContainer();
    }

}
