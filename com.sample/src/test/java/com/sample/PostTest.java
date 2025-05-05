package com.sample;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PostTest {

    @Test
    public void testLargePostPayload() {
        // Set Base URI
        RestAssured.baseURI = "https://reqres.in/api";

        // Create large payload manually without `.repeat()` (just for example)
        String largeText = "A long text...".repeat(100); // This should be done in your code logic, not directly in JSON
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Sathish");
        payload.put("job", "Software Engineer");
        payload.put("description", largeText);

        // Send POST request with API key in headers
        Response response = RestAssured
        		.given()
                // Set Bearer Token for authorization
                .header("Authorization", "reqres-free-v1") // Replace with your actual Bearer Token
                // Set API Key
                .header("x-api-key", "reqres-free-v1") // Replace with your actual API Key
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/users")
            .then()
                .statusCode(201) // Expected status for successful creation
                .extract()
                .response();
        
        System.out.println("Response Body: " + response.getBody().asString());

        // Assertions
        String id = response.jsonPath().getString("id");
        String name = response.jsonPath().getString("name");

        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertEquals("Sathish", name, "Name should match the request payload");
    }
}

