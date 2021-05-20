package it.prisma.prismabooking.model.facility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FacilityDTO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Boolean flagLuxury;
}
