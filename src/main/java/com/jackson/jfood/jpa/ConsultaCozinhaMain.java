package com.jackson.jfood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.jackson.jfood.JfoodApiApplication;
import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.repository.CityRepository;
import com.jackson.jfood.domain.repository.CuisineRepository;
import com.jackson.jfood.domain.repository.PaymentTypeRepository;
import com.jackson.jfood.domain.repository.PermissionRepository;
import com.jackson.jfood.domain.repository.RestaurantRepository;
import com.jackson.jfood.domain.repository.StateRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(JfoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CuisineRepository cuisineRepository = applicationContext.getBean(CuisineRepository.class);
		
		Cuisine cuisine = new Cuisine();
		cuisine.setName("Brasileira");
		Cuisine cuisineAdd = cuisineRepository.persist(cuisine);
		System.out.println("Added: " + cuisineAdd.getId() + "." + cuisineAdd.getName());
		
		cuisineRepository.list().stream().forEach(x -> System.out.println("List: " + x.getName()));
		
		Cuisine find = cuisineRepository.find(1L);
		System.out.println("FindById: " + find.getId() + "." + find.getName());
		
		find.setName(find.getName() + " - Updated");
		
		cuisineRepository.persist(find);
		
		find = cuisineRepository.find(1L);
		System.out.println("FindById - After Update: " + find.getId() + "." + find.getName());
		
		cuisineRepository.remove(cuisineAdd);
		System.out.println("COZINHAS -----------------");
		cuisineRepository.list().stream().forEach(x -> System.out.println("List after remove: " + x));
		
		System.out.println("RESTAURANTES -----------------");
		RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);
		restaurantRepository.list().forEach(x -> System.out.println(x));
		
		System.out.println("ESTADOS -----------------");
		StateRepository stateRepository = applicationContext.getBean(StateRepository.class);
		stateRepository.list().forEach(x -> System.out.println(x));
		
		System.out.println("CIDADES -----------------");
		CityRepository cityRepository = applicationContext.getBean(CityRepository.class);
		cityRepository.list().forEach(x -> System.out.println(x));
		
		System.out.println("PERMISSOES -----------------");
		PermissionRepository permissionRepository = applicationContext.getBean(PermissionRepository.class);
		permissionRepository.list().forEach(x -> System.out.println(x));
		
		System.out.println("FORMAS DE PAGAMENTO -----------------");
		PaymentTypeRepository paymentTypeRepository = applicationContext.getBean(PaymentTypeRepository.class);
		paymentTypeRepository.list().forEach(x -> System.out.println(x));
	}
}
