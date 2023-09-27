package jsilgado.mecalux.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jsilgado.mecalux.feign.dto.CountryDTO;

@FeignClient(name = "rest-countries",url = "https://restcountries.com/v3.1")
public interface CountryClient {

	@GetMapping("/alpha/{cca3}")
	public List<CountryDTO> getCountrybycca3(@PathVariable("cca3") String cca3);
}