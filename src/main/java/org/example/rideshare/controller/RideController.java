package org.example.rideshare.controller;

import jakarta.validation.Valid;
import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/rides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RideResponse> createRide(
            @Valid @RequestBody CreateRideRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        RideResponse response = rideService.createRide(request, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/driver/rides/requests")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<List<RideResponse>> getRequestedRides() {
        List<RideResponse> rides = rideService.getRequestedRides();
        return ResponseEntity.ok(rides);
    }

    @PostMapping("/driver/rides/{rideId}/accept")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<RideResponse> acceptRide(
            @PathVariable String rideId,
            Authentication authentication) {
        String username = authentication.getName();
        RideResponse response = rideService.acceptRide(rideId, username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rides/{rideId}/complete")
    @PreAuthorize("hasRole('USER') or hasRole('DRIVER')")
    public ResponseEntity<RideResponse> completeRide(
            @PathVariable String rideId,
            Authentication authentication) {
        String username = authentication.getName();
        RideResponse response = rideService.completeRide(rideId, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/rides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<RideResponse>> getUserRides(Authentication authentication) {
        String username = authentication.getName();
        List<RideResponse> rides = rideService.getUserRides(username);
        return ResponseEntity.ok(rides);
    }
}
