package it.prisma.prismabooking.model.facility;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class FacilityDTO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private boolean flagLuxury;
}
