package it.prisma.prismabooking.model.building;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BuildingDTO {
    private Integer id;
    private String telephoneNumber;
    private String email;
    private boolean flagWiFi;
    private BuildingType buildingType;
    private String street;
    private String city;
    private String country;
}
