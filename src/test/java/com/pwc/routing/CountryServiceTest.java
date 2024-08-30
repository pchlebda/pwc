package com.pwc.routing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CountryServiceTest {

    @Autowired
    private CountryService countryService;


    @ValueSource(strings = {"CZE", "ITA", "USA"})
    @ParameterizedTest
    void testFoundCountry(String code) {
        assertTrue(countryService.doesCountryCodeExist(code));
        assertTrue(countryService.getCountryByCca3(code).isPresent());
    }

    @ValueSource(strings = {"XYZ", "A", "acxcv"})
    @ParameterizedTest
    void testNotFoundCountry(String code) {
        assertFalse(countryService.doesCountryCodeExist(code));
        assertTrue(countryService.getCountryByCca3(code).isEmpty());
    }
}