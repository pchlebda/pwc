package com.pwc.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class RouteValidatorTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private RouteValidator routeValidator;


    @Test
    void testValidate_OriginAndDestinationAreTheSame() {
        String origin = "CZE";
        String destination = "CZE";

        when(countryService.doesCountryCodeExist("CZE")).thenReturn(true);

        List<MessageCode> messageCodes = routeValidator.validate(origin, destination);

        assertEquals(1, messageCodes.size());
        assertEquals("Origin and destination are the same", messageCodes.get(0).message());
    }

    @Test
    void testValidate_OriginDoesNotExist() {
        String origin = "XYZ";
        String destination = "CZE";

        when(countryService.doesCountryCodeExist(origin)).thenReturn(false);
        when(countryService.doesCountryCodeExist(destination)).thenReturn(true);

        List<MessageCode> messageCodes = routeValidator.validate(origin, destination);

        assertEquals(1, messageCodes.size());
        assertEquals("Origin does not exist", messageCodes.get(0).message());
    }

    @Test
    void testValidate_DestinationDoesNotExist() {
        String origin = "CZE";
        String destination = "XYZ";

        when(countryService.doesCountryCodeExist(origin)).thenReturn(true);
        when(countryService.doesCountryCodeExist(destination)).thenReturn(false);

        List<MessageCode> messageCodes = routeValidator.validate(origin, destination);

        assertEquals(1, messageCodes.size());
        assertEquals("Destination does not exist", messageCodes.get(0).message());
    }

    @Test
    void testValidate_BothOriginAndDestinationDoNotExist() {
        String origin = "XYZ";
        String destination = "ABC";

        when(countryService.doesCountryCodeExist(origin)).thenReturn(false);
        when(countryService.doesCountryCodeExist(destination)).thenReturn(false);

        List<MessageCode> messageCodes = routeValidator.validate(origin, destination);

        assertEquals(2, messageCodes.size());
        assertEquals("Origin does not exist", messageCodes.get(0).message());
        assertEquals("Destination does not exist", messageCodes.get(1).message());
    }

    @Test
    void testValidate_ValidOriginAndDestination() {
        String origin = "CZE";
        String destination = "ITA";

        when(countryService.doesCountryCodeExist(origin)).thenReturn(true);
        when(countryService.doesCountryCodeExist(destination)).thenReturn(true);

        List<MessageCode> messageCodes = routeValidator.validate(origin, destination);

        assertEquals(0, messageCodes.size());
    }
}
