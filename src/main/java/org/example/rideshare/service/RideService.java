package org.example.rideshare.service;

import org.example.rideshare.RideStatus;
import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.NotFoundException;
import org.example.rideshare.exception.UnauthorizedException;
import org.example.rideshare.model.Ride;
import org.example.rideshare.model.Role;
import org.example.rideshare.model.User;
import org.example.rideshare.repository.RideRepository;
import org.example.rideshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    public RideResponse createRide(CreateRideRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getRole() != Role.ROLE_USER) {
            throw new BadRequestException("Only users with ROLE_USER can request rides");
        }

        Ride ride = Ride.builder()
                .userId(user.getId())
                .pickupLocation(request.getPickupLocation())
                .dropLocation(request.getDropLocation())
                .status(RideStatus.REQUESTED)
                .createdAt(new Date())
                .build();

        Ride savedRide = rideRepository.save(ride);
        return mapToResponse(savedRide);
    }

    public List<RideResponse> getRequestedRides() {
        Iterable<Ride> rides = rideRepository.findByStatus(RideStatus.REQUESTED);
        return StreamSupport.stream(rides.spliterator(), false)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RideResponse acceptRide(String rideId, String username) {
        User driver = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Driver not found"));

        if (driver.getRole() != Role.ROLE_DRIVER) {
            throw new UnauthorizedException("Only users with ROLE_DRIVER can accept rides");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new BadRequestException("Only rides with status REQUESTED can be accepted");
        }

        ride.setDriverId(driver.getId());
        ride.setStatus(RideStatus.ACCEPTED);

        Ride updatedRide = rideRepository.save(ride);
        return mapToResponse(updatedRide);
    }

    public RideResponse completeRide(String rideId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new BadRequestException("Only rides with status ACCEPTED can be completed");
        }

        boolean isPassenger = ride.getUserId().equals(user.getId());
        boolean isDriver = ride.getDriverId() != null && ride.getDriverId().equals(user.getId());

        if (!isPassenger && !isDriver) {
            throw new UnauthorizedException("You are not authorized to complete this ride");
        }

        ride.setStatus(RideStatus.COMPLETED);

        Ride updatedRide = rideRepository.save(ride);
        return mapToResponse(updatedRide);
    }

    public List<RideResponse> getUserRides(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Iterable<Ride> rides = rideRepository.findByUserId(user.getId());
        return StreamSupport.stream(rides.spliterator(), false)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private RideResponse mapToResponse(Ride ride) {
        return RideResponse.builder()
                .id(ride.getId())
                .userId(ride.getUserId())
                .driverId(ride.getDriverId())
                .pickupLocation(ride.getPickupLocation())
                .dropLocation(ride.getDropLocation())
                .status(ride.getStatus())
                .createdAt(ride.getCreatedAt())
                .build();
    }
}
