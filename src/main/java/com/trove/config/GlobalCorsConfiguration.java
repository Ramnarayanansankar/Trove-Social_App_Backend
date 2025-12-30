package com.trove.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Handling the CORS Globally Via GlobalCorsConfiguration and WebMvcConfiguration

@Configuration
public class GlobalCorsConfiguration implements WebMvcConfigurer {


    public void addCorsMappings(CorsRegistry registry) {
        // This mapping applies the CORS configuration to all paths in the application
        registry.addMapping("/**")
                // Specifies which origins are allowed to access your resources.
                // For production, replace "*" with the exact URL(s) of your frontend application(s),
                // e.g., "https://myfrontendapp.com"
                .allowedOrigins("http://localhost:4200")

                // Specifies the HTTP methods allowed, which is crucial for RESTful APIs
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")

                // Specifies which request headers can be used
                .allowedHeaders("*")

                // Allows credentials (like cookies or HTTP authentication) to be included in cross-origin requests
                .allowCredentials(true)

                // How long the results of a preflight request (OPTIONS) can be cached by the browser (in seconds)
                .maxAge(3600);
    }
}
