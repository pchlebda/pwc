package com.pwc.routing;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RouteValidator {

    private final CountryService countryService;

    public List<MessageCode> validate(String origin, String destination) {
        List<MessageCode> messageCodes = new ArrayList<>();
        if (origin.equals(destination)) {
            messageCodes.add(new MessageCode("Origin and destination are the same"));
        }
        if (!countryService.doesCountryCodeExist(origin)) {
            messageCodes.add(new MessageCode("Origin does not exist"));
        }
        if (!countryService.doesCountryCodeExist(destination)) {
            messageCodes.add(new MessageCode("Destination does not exist"));
        }
        return messageCodes;
    }
}
