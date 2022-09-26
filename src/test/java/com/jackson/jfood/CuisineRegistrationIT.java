package com.jackson.jfood;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.repository.CuisineRepository;
import com.jackson.jfood.util.DatabaseCleaner;
import com.jackson.jfood.util.ResourceUtils;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CuisineRegistrationIT {

	private static final int NON_EXISTING_CUISINE_ID = 999;
	@LocalServerPort
	private int port;
	private int sizeInitialCuisines;
	private Cuisine americanCuisine;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cuisines";
		
		databaseCleaner.clearTables();
		setUpData();
	}
	
	@Test
	public void mustReturnStatus200_WhenGetCuisines() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void mustMatchInicialCuisinesSize_WhenGetCuisines() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(sizeInitialCuisines))
			.body("name", Matchers.hasItems(americanCuisine.getName()));
	}
	
	@Test
	public void mustReturnStatus201_WhenNewCuisineRegistration() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/Cuisine/mustReturnStatus201_WhenNewCuisineRegistration.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void mustReturnValidResponseAndStatus_WhenQueryExistingCuisine() {
		given()
			.pathParam("cuisineId", americanCuisine.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cuisineId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(americanCuisine.getName()));
	}
	
	@Test
	public void mustReturnStatus404_WhenQueryNonExistingCuisine() {
		given()
			.pathParam("cuisineId", NON_EXISTING_CUISINE_ID)
			.accept(ContentType.JSON)
		.when()
			.get("/{cuisineId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void setUpData() {
		Cuisine cuisine1 = new Cuisine();
		cuisine1.setName("Tailandesa");
		cuisineRepository.save(cuisine1);
		sizeInitialCuisines++;
		
		americanCuisine = new Cuisine();
		americanCuisine.setName("American");
		cuisineRepository.save(americanCuisine);
		sizeInitialCuisines++;
	}
}
