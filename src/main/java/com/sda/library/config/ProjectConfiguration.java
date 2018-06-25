package com.sda.library.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class ProjectConfiguration {

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        //Config.loadProperties();
    }
}