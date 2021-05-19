package it.prisma.prismabooking.utils;

import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.building.BuildingDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BuildingMapper {

    BuildingMapper INSTANCE = Mappers.getMapper(BuildingMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBuildingFromDTO(BuildingDTO dto, @MappingTarget Building entity);
}
