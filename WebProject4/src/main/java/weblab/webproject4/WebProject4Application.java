package weblab.webproject4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties
public class WebProject4Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(WebProject4Application.class, args);
	}
}
