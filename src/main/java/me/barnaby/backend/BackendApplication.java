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



	private static List<Product> createExampleProducts() throws MalformedURLException {
		List<Product> exampleProducts = new ArrayList<>();

		for (int i = 1; i <= 20; i++) {
			Product product = new Product(
					i,
					"Product" + i,
					new URL("https://example.com/product" + i),
					new URL("https://example.com/image" + i),
					100 * i,
					"Brand" + i,
					"LARGE",
					new Price(10.99, "USD", 1),
					new Seller("test", new URL("https://www.google.com"), new URL("https://www.google.com"))
			);

			exampleProducts.add(product);
		}

		return exampleProducts;
	}

}
