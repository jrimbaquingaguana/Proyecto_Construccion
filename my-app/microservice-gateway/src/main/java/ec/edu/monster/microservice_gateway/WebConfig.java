package ec.edu.monster.microservice_gateway;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Aplica a todos los endpoints
                .allowedOrigins("http://localhost:8080")  // URL de tu Frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos permitidos
                .allowedHeaders("*")  // Permitir todos los headers
                .allowCredentials(true);  // Permitir credenciales (cookies, autenticación)
    }
}

