package com.company.prices.integration;

import com.company.prices.infrastructure.config.PricesApiApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("local")// Use 'test' profile for integration tests
@AutoConfigureMockMvc
@SpringBootTest(classes = PricesApiApplication.class)
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Test 1: When day 14 and hour 10, product 35455, brandId 1, expects price 35.50 EUR")
    void test1WhenDay14AndHour10Product35455BrandId1ExpectsPrice() throws Exception {

        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));


    }

    @Test
    @DisplayName("Test 2: When day 14 and hour 16, product 35455, brandId 1, expects price 25.45 EUR")
    void test2WhenDay14AndHour16Product35455BrandId1ExpectsPrice() throws Exception {

        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 3: When day 14 and hour 21, product 35455, brandId 1, expects price 35.50 EUR")
    void test3WhenDay14AndHour21Product35455BrandId1ExpectsPrice() throws Exception {

        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-06-14T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 4: When day 15 and hour 10, product 35455, brandId 1, expects price 30.50 EUR")
    void test4WhenDay15AndHour10Product35455BrandId1ExpectsPrice() throws Exception {

        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-06-15T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 5: When day 16 and hour 21, product 35455, brandId 1, expects price 38.95 EUR")
    void test5WhenDay16AndHour21Product35455BrandId1ExpectsPrice() throws Exception {

        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-06-16T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"));

    }

    @Test
    @DisplayName("Should return 400 when required parameters are missing")
    void shouldReturn400WhenParametersMissing() throws Exception {
        mockMvc.perform(get("/api/v1/prices"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 404 when price not found")
    void shouldReturn404WhenPriceNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-01-01T10:00:00")
                        .param("productId", "99999")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Should return price when valid request")
    void shouldReturnPriceWhenValidRequest() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists());
    }
}
