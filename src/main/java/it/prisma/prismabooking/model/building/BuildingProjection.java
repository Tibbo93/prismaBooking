package it.prisma.prismabooking.model.building;

public interface BuildingProjection {

    Integer getId();

    String getTelephoneNumber();

    String getEmail();

    Boolean getFlagWiFi();

    BuildingType getBuildingType();

    String getStreet();

    String getCity();

    String getCountry();
}
