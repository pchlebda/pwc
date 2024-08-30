package com.pwc.routing;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoutingServiceTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private RoutingService routingService;


    @Test
    void testFindRoute_Success() {
        // given
        Country cze = new Country("CZE", of("AUT", "POL", "SVK"));
        Country aut = new Country("AUT", of("CZE", "ITA"));

        when(countryService.getCountryByCca3("CZE")).thenReturn(Optional.of(cze));
        when(countryService.getCountryByCca3("AUT")).thenReturn(Optional.of(aut));

        // when
        Optional<Route> route = routingService.findRoute("CZE", "ITA");

        // then
        assertTrue(route.isPresent());
        assertEquals(of("CZE", "AUT", "ITA"), route.get().route());
    }

    @Test
    void testFindRoute_NoRouteFound() {
        // given
        Country cze = new Country("CZE", of("AUT"));
        Country aut = new Country("AUT", of("CZE"));

        when(countryService.getCountryByCca3("CZE")).thenReturn(Optional.of(cze));
        when(countryService.getCountryByCca3("AUT")).thenReturn(Optional.of(aut));

        // when
        Optional<Route> route = routingService.findRoute("CZE", "USA");

        // then
        assertTrue(route.isEmpty());
    }
}