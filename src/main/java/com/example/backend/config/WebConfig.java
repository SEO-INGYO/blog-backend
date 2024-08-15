package com.example.backend.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {    
        CorsConfiguration config = new CorsConfiguration();    
        config.setAllowCredentials(false);    
        config.addAllowedOrigin("https://blog.rocd.site");    
        config.addAllowedHeader("*");    
        config.addAllowedMethod("GET");
        config.setMaxAge(6000L);     
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();    
        source.registerCorsConfiguration("/**", config);     
        FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<>(new CorsFilter(source));    
        filterBean.setOrder(0);     
        return filterBean;
    }
}
