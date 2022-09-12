package com.cpcd.microservices.app.usuarios.microserviciousuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EntityScan({"com.cpcd.microservices.app.servicescommons.models.entity"})
@SpringBootApplication
public class MicroserviciousuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciousuariosApplication.class, args);
	}

}
