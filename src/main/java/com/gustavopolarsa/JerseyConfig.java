package com.gustavopolarsa;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.gustavopolarsa.ws.Functions;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(Functions.class);
    }
}