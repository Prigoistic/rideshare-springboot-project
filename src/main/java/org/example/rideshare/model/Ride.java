package org.example.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rideshare.RideStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("rides")
public class Ride {
    @Id
    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private RideStatus status;
    private Date createdAt;
}
