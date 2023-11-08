package de.szut.lf8_project.service;

import de.szut.lf8_project.service.employee.EmployeeEntity;
import de.szut.lf8_project.service.employee.dto.CreateEmployeeDTO;
import de.szut.lf8_project.service.employee.dto.CreateProjectDTO;
import de.szut.lf8_project.service.employee.dto.GetEmployeeDTO;
import de.szut.lf8_project.service.project.ProjectEntity;
import de.szut.lf8_project.service.project.dto.GetProjectDTO;

public class MappingService {

    public EmployeeEntity mapCreateEmployeeDTOToEmployeeEntity(CreateEmployeeDTO createEmployeeDTO) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(createEmployeeDTO.getId());
        entity.setRole(createEmployeeDTO.getRole());
        return entity;
    }
    public ProjectEntity mapCreateProjectDTOToProjectEntity(CreateProjectDTO createProjectDTO) {
        ProjectEntity project = new ProjectEntity();
        project.setDescription(createProjectDTO.getDescription());
        project.setClientId(createProjectDTO.getClientId());
        project.setContactName(createProjectDTO.getContactName());
        project.setEmployees(createProjectDTO.getEmployees().stream().map(this::mapCreateEmployeeDTOToEmployeeEntity).toList());
        return project;
    }

    public GetProjectDTO mapProjectEntityToGetProjectDTO(ProjectEntity entity) {
        GetProjectDTO getProjectDTO = new GetProjectDTO();
       // getProjectDTO.setId(entity.getId());
       // getProjectDTO.setStart(entity.getStart());
        getProjectDTO.setDescription(entity.getDescription());
        getProjectDTO.setClientId(entity.getClientId());
        getProjectDTO.setContactName(entity.getContactName());
//        getProjectDTO.setScheduledEnd(entity.getScheduledEnd());
//        getProjectDTO.setActualEnd(entity.getActualEnd());
        //getProjectDTO.setEmployeeId(entity.getEmployeeId());
        return getProjectDTO;
    }

    public GetEmployeeDTO mapEmployeeEntityToGetEmployeeDTO(EmployeeEntity entity) {
        GetEmployeeDTO getEmployeeDTO = new GetEmployeeDTO();
      //  getEmployeeDTO.setId(entity.getId());
        return getEmployeeDTO;
    }
}
