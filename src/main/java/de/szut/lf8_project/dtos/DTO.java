package de.szut.lf8_project.dtos;

import de.szut.lf8_project.exceptionHandling.CanBeNull;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DTO {

    private List<Field> notNullFields;

    private void fillNotNullFields() {
        notNullFields = new ArrayList<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(CanBeNull.class)) {
                continue;
            }
            notNullFields.add(field);
        }
    }

    public void getInvalids() {
        if (notNullFields == null) {
            fillNotNullFields();
        }
        List<String> invalids = new ArrayList<>();
        try {
            for (Field field : notNullFields) {
                if (field.get(this) == null) {
                    invalids.add(field.getName());
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (!invalids.isEmpty()) {
            StringBuilder invalidsString = new StringBuilder();
            for (int i = 0; i < invalids.size(); i++) {
                invalidsString.append("'").append(invalids.get(i)).append("'");
                if (i < invalids.size() - 2) {
                    invalidsString.append(", ");
                } else if (i == invalids.size() - 2) {
                    invalidsString.append(" and ");
                }
            }
            throw new ResourceNotFoundException("Invalid data: The field" + (invalids.size() > 1 ? "s" : "") + " " + invalidsString + " cannot be null");
        }
    }
}
