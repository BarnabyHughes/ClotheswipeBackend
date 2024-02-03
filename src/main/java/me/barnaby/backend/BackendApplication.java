package me.barnaby.backend;

import lombok.Getter;
import me.barnaby.backend.apis.APIManager;
import me.barnaby.backend.product.Product;
import me.barnaby.backend.product.productArgs.Price;
import me.barnaby.backend.product.productArgs.Seller;
import me.barnaby.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Autowired @Getter
	private ProductService productService;

	@Bean
	public APIManager apiManager() {
		APIManager apiManager = new APIManager(this);
		apiManager.initializeAPIs();
		return apiManager;
	}
}
