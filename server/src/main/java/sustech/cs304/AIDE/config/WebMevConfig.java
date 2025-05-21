package sustech.cs304.AIDE.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @author chatgpt
 * @description WebConfig class to configure resource handlers for serving static files.
 */

@Configuration
public class WebMevConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:/home/cse12311018/Documents/Save/");
    }
}


