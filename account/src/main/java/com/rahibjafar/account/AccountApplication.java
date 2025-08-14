package com.rahibjafar.account;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(title = "Account API", version = "v1", description = "Xidmətin REST API-si")
)
@SpringBootApplication
public class AccountApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}
}
