package ec.edu.monster.eureka.deposito.microservice_deposito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceDepositoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceDepositoApplication.class, args);
	}
	{

	}
}
