package it.prisma.prismabooking.model.building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Building {
    private String id;
    private String telephoneNumber;
    private String emailWiFi;
    private BuildingType buildingType;
    private String street;
    private String city;
    private String country;
}
