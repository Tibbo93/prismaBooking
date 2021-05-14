package it.prisma.prismabooking.service;

import com.google.gson.Gson;
import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class BaseService<T> {
    protected ConfigurationComponent config;
    protected List<T> list = new ArrayList<>();
    @Autowired
    protected Gson gson;

    public PagedRes<T> findPage(Integer offset, Integer limit) {
        return createPage(offset, limit, list);
    }

    public PagedRes<T> findPage(Integer offset, Integer limit, List<T> content) {
        return createPage(offset, limit, content);
    }

    public T findResource(String id) {
        return list.stream()
                .filter(t -> Arrays.stream(t.getClass().getDeclaredMethods())
                        .filter(method -> method.getName().equals("getId"))
                        .map(method -> {
                            try {
                                return method.invoke(t);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                log.error(e.getMessage());
                            }
                            return "";
                        })
                        .findFirst()
                        .get()
                        .equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Resource not found with id: " + id));
    }

    public PagedRes<T> createPage(Integer offset, Integer limit, List<T> content) {
        limit = handleLimit(limit);
        offset = handleOffset(offset);
        int start = offset * limit;
        int end = Math.min(start + limit, content.size());

        List<T> sublist = list.subList(start, end);
        return PagedRes.<T>builder()
                .data(sublist)
                .offset(offset.longValue())
                .totalElements((long) sublist.size())
                .build();
    }

    public Integer handleLimit(Integer limit) {
        limit = Optional.ofNullable(limit)
                .filter(l -> l <= config.getMaxPageSizeLimit() && l >= 0)
                .orElse(config.getDefaultPageSizeLimit());

        if (limit > list.size())
            return list.size();

        return limit;
    }

    public Integer handleOffset(Integer offset) {
        return Optional.ofNullable(offset)
                .filter(o -> o >= 0)
                .orElse(0);
    }

    protected void loadJSON(Resource resourceFile, Class<T> clazz) {
        if (!resourceFile.exists())
            return;
        try (Stream<String> lines = Files.lines(resourceFile.getFile().toPath())) {
            lines.forEach(line -> list.add(gson.fromJson(line, clazz)));
        } catch (IOException e) {
            log.error("Error parsing line with cause: {}", e.getMessage());
        }
    }

    protected void writeOnJSON(Resource resourceFile, T resource) {
        try {
            Files.write(resourceFile.getFile().toPath(),
                    gson.toJson(resource).concat(System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    protected void deleteFromJSON(Resource resourceFile) {
        try {
            Files.write(resourceFile.getFile().toPath(), list.stream()
                            .map(resource -> gson.toJson(resource))
                            .collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator())).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
