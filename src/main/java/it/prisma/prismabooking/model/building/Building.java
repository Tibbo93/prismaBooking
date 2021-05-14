package it.prisma.prismabooking.model.building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Building {
    private String id;
    private String telephoneNumber;
    private String email;
    private boolean flagWiFi;
    private BuildingType buildingType;
    private String street;
    private String city;
    private String country;
    private List<String> facilitiesId;
}
