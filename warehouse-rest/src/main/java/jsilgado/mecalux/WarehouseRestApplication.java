package jsilgado.mecalux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WarehouseRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseRestApplication.class, args);
	}

}
