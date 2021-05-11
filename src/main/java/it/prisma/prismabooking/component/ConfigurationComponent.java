package it.prisma.prismabooking.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("prisma-booking")
@Data
public class ConfigurationComponent {
    private Integer defaultPageSizeLimit;
    private Integer maxPageSizeLimit;
}