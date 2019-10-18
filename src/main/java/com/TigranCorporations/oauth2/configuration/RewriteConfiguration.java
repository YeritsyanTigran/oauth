package com.TigranCorporations.oauth2.configuration;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

@Component
public class RewriteConfiguration extends HttpConfigurationProvider {
    @Override
    public Configuration getConfiguration(ServletContext servletContext) {
        return ConfigurationBuilder.begin()
                .addRule(Join.path("/{path}").to("/{path}.xhtml"));
    }

    @Override
    public int priority() {
        return 0;
    }
}
