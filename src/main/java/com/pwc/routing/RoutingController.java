package com.pwc.routing;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/routing")
public class RoutingController {

    private final RoutingService routingService;
    private final RouteValidator routeValidator;

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<?> getRoute(@PathVariable String origin,
            @PathVariable String destination) {

        var upperCaseOrigin = origin.toUpperCase();
        var upperCaseDestination = destination.toUpperCase();

        var errors = routeValidator.validate(upperCaseOrigin, upperCaseDestination);
        if (!errors.isEmpty()) {
            String errorMessage = errors.stream().map(MessageCode::message).collect(Collectors.joining(","));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        var maybeRoute = routingService.findRoute(upperCaseOrigin, upperCaseDestination);

        return maybeRoute.isPresent() ? ResponseEntity.ok(maybeRoute.get()) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageCode("Route does not exist"));
    }
}