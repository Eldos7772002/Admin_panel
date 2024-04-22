package kz.postkz.AdminCrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminCrudApplication.class, args);
	}

}
