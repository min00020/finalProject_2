package com.yedamFinal.aco.secure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Value("${file.upload.path}") // C:\\upload
	private String uploadFilePath;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {	
        registry.addResourceHandler("/upload/**") 
        .addResourceLocations("file:"+uploadFilePath + "/")  //  접근시(http://localhost/upload/~~)하면댐
        .setCachePeriod(3600)
        .resourceChain(true)
        .addResolver(new PathResourceResolver());
    }
}

