package ec.edu.monster.eureka.retiro.microservice_retiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceRetiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceRetiroApplication.class, args);
	}

}
