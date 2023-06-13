package calendario.calendario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@SpringBootApplication
public class CalendarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarioApplication.class, args);
	}



}
