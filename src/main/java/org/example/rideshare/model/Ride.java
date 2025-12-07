package org.example.rideshare.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rides")
public class Ride {
    @Id
    private String id;
    private String riderUserId;
    private String driverUserId;
    private String pickup;
    private String dropoff;
}
