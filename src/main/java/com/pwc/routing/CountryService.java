package com.pwc.routing;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private final Map<String, Country> countriesByCode;

    CountryService(@Value("classpath:countries.json") Resource resourceFile, ObjectMapper objectMapper)
            throws IOException {
        countriesByCode = Arrays.stream(
                        objectMapper.readValue(Files.readAllBytes(resourceFile.getFile().toPath()), Country[].class))
                .collect(Collectors.toMap(Country::cca3, Function.identity()));
    }

    Optional<Country> getCountryByCca3(String currentCountry) {
        return Optional.ofNullable(countriesByCode.get(currentCountry));
    }

    boolean doesCountryCodeExist(String cca3) {
        return countriesByCode.containsKey(cca3);
    }
}
