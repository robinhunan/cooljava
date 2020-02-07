package com.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;

/**
 * 20191119
 * @auther lfp
 */
@Configuration
public class IndexConfig {
    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
    }
}