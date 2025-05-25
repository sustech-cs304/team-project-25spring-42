package sustech.cs304.AIDE.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @author chatgpt
 * @description WebConfig class to configure resource handlers for serving static files.
 */

/**
 * Spring MVC configuration class that maps static resources from the local file system.
 */
@Configuration
public class WebMevConfig implements WebMvcConfigurer {
    /**
     * Adds custom resource handlers for serving static files.
     *
     * @param registry the ResourceHandlerRegistry used to register the custom resource handler
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:/home/cse12311018/Documents/Save/");
    }
}


