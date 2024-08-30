package com.pwc.routing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutingService {

    private final CountryService countryService;

    public Optional<Route> findRoute(String origin, String destination) {
        Map<String, String> previousCountry = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty()) {
            String currentCountry = queue.poll();
            var maybeCountry = countryService.getCountryByCca3(currentCountry);

            if (maybeCountry.isPresent()) {
                for (String neighbor : maybeCountry.get().borders()) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                        previousCountry.put(neighbor, currentCountry);

                        if (neighbor.equals(destination)) {
                            return Optional.of(buildRoute(previousCountry, destination));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }


    private Route buildRoute(Map<String, String> previousCountry, String destination) {
        List<String> route = new LinkedList<>();
        for (String at = destination; at != null; at = previousCountry.get(at)) {
            route.add(at);
        }
        return new Route(route.reversed());
    }
}
