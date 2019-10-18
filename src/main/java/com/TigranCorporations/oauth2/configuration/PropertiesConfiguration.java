package com.TigranCorporations.oauth2.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/settings/config.${myhost}.properties")
public class PropertiesConfiguration {
}
