package it.prisma.prismabooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PagedRes<T> {
    List<T> data;
    Long offset;
    Long totalElements;
}
