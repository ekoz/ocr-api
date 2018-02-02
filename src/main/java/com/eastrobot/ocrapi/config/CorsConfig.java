/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @see <a href="https://www.jianshu.com/p/f2060a6d6e3b">Spring Boot 之路[6]--允许跨域请求</a>
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年2月2日 上午11:54:06
 * @version 1.0
 */
@Configuration
public class CorsConfig {

	private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); //允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); //允许任何头
        corsConfiguration.addAllowedMethod("*"); //允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

}
