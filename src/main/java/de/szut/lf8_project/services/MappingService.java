package de.szut.lf8_project.services;

import de.szut.lf8_project.dtos.*;
import de.szut.lf8_project.entities.*;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class MappingService {

    private final EmployeeAssocService employeeService;

    // Project

    public ProjectEntity mapCreateProjectDTOToProjectEntity(CreateProjectDTO dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setDescription(dto.getDescription());
        entity.setContactName(dto.getContactName());
        entity.setClientId(dto.getClientId());
        return entity;
    }

    public ProjectEntity mapCreateProjectDTOToProjectEntity(CreateProjectDTO dto, Long id) {
        ProjectEntity entity = mapCreateProjectDTOToProjectEntity(dto);
        entity.setId(id);
        return entity;
    }

    public GetProjectDTO mapProjectEntityToGetProjectDTO(ProjectEntity entity) {
        if (entity == null) {
            return null;
        }
        GetProjectDTO dto = new GetProjectDTO();
        dto.setClientId(entity.getClientId());
        dto.setDescription(entity.getDescription());
        dto.setContactName(entity.getContactName());
        dto.setEmployees(employeeService.getEmployeesFromProject(entity.getId()).stream()
                .map(this::mapEmployeeAssocEntityToGetEmployeeAssocDTO).toList());
        return dto;
    }

    // Employee Assoc

    public GetEmployeeAssocDTO mapEmployeeAssocEntityToGetEmployeeAssocDTO(EmployeeAssocEntity entity) {
        if (entity == null) {
            return null;
        }
        GetEmployeeAssocDTO dto = new GetEmployeeAssocDTO();
        dto.setRole(entity.getRole());
        dto.setEmployeeId(entity.getId().getEmployeeId());
        return dto;
    }

    public List<GetEmployeeAssocDTO> mapEmployeeAssocEntitiesToGetEmployeeAssocDTOs(List<EmployeeAssocEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        return entities.stream().map(this::mapEmployeeAssocEntityToGetEmployeeAssocDTO).toList();
    }

    public EmployeeAssocEntity mapCreateEmployeeDTOToEmployeeAssocEntity(CreateEmployeeAssocDTO dto, Long projectId) {
        EmployeeAssocEntity entity = new EmployeeAssocEntity();
        entity.setRole(dto.getRole());
        EmployeeAssocId id = new EmployeeAssocId();
        id.setEmployeeId(dto.getEmployeeId());
        id.setProjectId(projectId);
        entity.setId(id);
        return entity;
    }
}
