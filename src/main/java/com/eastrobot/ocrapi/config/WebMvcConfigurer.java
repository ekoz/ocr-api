/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 下午2:14:09
 * @version 1.0
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController( "/demo" ).setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
		super.addViewControllers(registry);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars*")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
