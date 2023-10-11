package jsilgado.mecalux;

import org.query.filter.jpa.openapi.data.jpa.annotations.EnableQueryFilterOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.github.acoboh.query.filter.jpa.annotations.EnableQueryFilter;
import jsilgado.mecalux.controller.WarehouseController;
import jsilgado.mecalux.persistence.entity.filter.WarehouseFilterDef;

@EnableFeignClients
@EnableCaching
@EnableQueryFilter(basePackageClasses = WarehouseFilterDef.class)
@EnableQueryFilterOpenApi(basePackageClasses = WarehouseController.class)
@SpringBootApplication
public class WarehouseRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseRestApplication.class, args);
	}

}
