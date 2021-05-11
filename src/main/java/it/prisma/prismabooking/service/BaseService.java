package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.PagedRes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BaseService<T> {
    protected ConfigurationComponent config;
    protected List<T> list = new ArrayList<>();

    public PagedRes<T> findPage(Integer offset, Integer limit) {
        limit = handleLimit(limit);
        offset = Optional.ofNullable(offset).orElse(0);
        int start = offset * limit;
        int end = Math.min(start + limit, list.size());

        List<T> sublist = list.subList(start, end);
        return PagedRes.<T>builder()
                .data(sublist)
                .offset(offset.longValue())
                .totalElements((long) sublist.size())
                .build();
    }

    public Integer handleLimit(Integer limit) {
        limit = Optional.ofNullable(limit)
                .filter(l -> l <= config.getMaxPageSizeLimit())
                .orElse(config.getDefaultPageSizeLimit());

        if (limit > list.size())
            return list.size();

        return limit;
    }
}
