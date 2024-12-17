// package main.java.com.liamfrager.connect;

// import java.util.List;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.http.converter.HttpMessageConverter;
// import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//                 .allowedOrigins("http://localhost:3000")
//                 .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
//                 .allowedHeaders("Content-Type", "Authorization") 
//                 .allowCredentials(true);
//     }

//     @Override
//     public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//         converters.add(new MappingJackson2HttpMessageConverter());
//     }
// }