package de.szut.lf8_project.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import de.szut.lf8_project.dtos.*;
import de.szut.lf8_project.entities.EmployeeAssocEntity;
import de.szut.lf8_project.entities.ProjectEntity;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.exceptionHandling.WrongIdFormatException;
import de.szut.lf8_project.services.EmployeeAssocService;
import de.szut.lf8_project.services.EmployeeService;
import de.szut.lf8_project.services.MappingService;
import de.szut.lf8_project.services.ProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping
public class ProjectController {

    private final ProjectService projectService;
    private final EmployeeAssocService employeeAssocService;
    private final EmployeeService employeeService;

    private final MappingService mappingService;

    public ProjectController(ProjectService projectService, EmployeeAssocService employeeAssocService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeAssocService = employeeAssocService;
        this.employeeService = employeeService;
        this.mappingService = new MappingService(employeeAssocService);
    }

    // Project

    @PostMapping("/projects")
    public GetProjectDTO create(@RequestBody CreateProjectDTO dto) {
        dto.getInvalids();
        return mappingService.mapProjectEntityToGetProjectDTO(projectService.create(mappingService.mapCreateProjectDTOToProjectEntity(dto)));
    }

    @PostMapping("/projects/{id}")
    public GetProjectDTO update(@RequestBody CreateProjectDTO dto, @PathVariable Long id) {
        ProjectEntity entity = projectService.update(mappingService.mapCreateProjectDTOToProjectEntity(dto, id));
        if (entity == null) {
            throw new ResourceNotFoundException("Project " + id + " does not exist");
        }
        return mappingService.mapProjectEntityToGetProjectDTO(entity);
    }

    @GetMapping("/projects/{id}")
    public GetProjectDTO findById(@PathVariable Long id) {
        ProjectEntity entity = projectService.findById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("Project " + id + " does not exist");
        }
        return mappingService.mapProjectEntityToGetProjectDTO(entity);
    }

    @GetMapping("/projects")
    public List<GetProjectDTO> findAll() {
        return projectService.findAll().stream().map(mappingService::mapProjectEntityToGetProjectDTO).toList();
    }

    @DeleteMapping("/projects/{id}")
    public GetProjectDTO delete(@PathVariable Long id) {
        ProjectEntity entity = projectService.delete(id, employeeAssocService);
        if (entity == null) {
            throw new ResourceNotFoundException("Project " + id + " does not exist");
        }
        return mappingService.mapProjectEntityToGetProjectDTO(entity);
    }

    // Employee Assoc

    @GetMapping("/employees/{id}/projects")
    public List<GetProjectDTO> getProjectsByEmployeeId(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            employeeService.findById(id, token);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ResourceNotFoundException("Employee " + id + " does not exist");
        }
        return employeeAssocService.getProjectIdsFromEmployee(id).stream()
                .map(projectService::findById)
                .map(mappingService::mapProjectEntityToGetProjectDTO)
                .toList();
    }

    @GetMapping("/projects/{id}/employees")
    public List<GetEmployeeAssocDTO> getEmployeesByProjectId(@PathVariable Long id) {
        return mappingService.mapEmployeeAssocEntitiesToGetEmployeeAssocDTOs(employeeAssocService.getEmployeesFromProject(id));
    }

    @PostMapping("/projects/{id}/employees")
    public GetProjectDTO addEmployeeToProject(@PathVariable Long id, @RequestHeader("Authorization") String token, @RequestBody CreateEmployeeAssocDTO dto) {
        ProjectEntity entity = projectService.findById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("Project " + id + " does not exist");
        }
        EmployeeAssocEntity employeeAssocEntity = mappingService.mapCreateEmployeeDTOToEmployeeAssocEntity(dto, id);
        Long employeeId = dto.getEmployeeId();
        try {
            employeeService.findById(employeeId, token);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ResourceNotFoundException("Employee " + employeeId + " does not exist");
        }
        employeeAssocService.addToProject(employeeAssocEntity);
        return mappingService.mapProjectEntityToGetProjectDTO(entity);
    }

    @DeleteMapping("/projects/{id}/employees")
    public GetProjectDTO removeEmployeeToProject(@PathVariable Long id, @RequestBody String body) {
        try {
            ProjectEntity entity = projectService.findById(id);
            if (entity == null) {
                throw new ResourceNotFoundException("Project " + id + " does not exist");
            }
            if (employeeAssocService.removeFromProject(id, Long.parseLong(body)) == null) {
                throw new ResourceNotFoundException("The employee " + body + " does not work on project " + id);
            }
            return mappingService.mapProjectEntityToGetProjectDTO(entity);
        } catch (NumberFormatException e) {
            throw new WrongIdFormatException("An error occurred while parsing the id to a number '" + body + "'");
        }
    }

    // Employee

    @GetMapping("/**")
    public JsonNode employeeRedirectGet(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        return employeeService.redirectGet(request.getRequestURI(), token, JsonNode.class);
    }

    @DeleteMapping("/**")
    public JsonNode employeeRedirectDelete(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        return employeeService.redirectDelete(request.getRequestURI(), token, JsonNode.class);
    }

    @PostMapping("/**")
    public <B> JsonNode employeeRedirectPost(@RequestHeader("Authorization") String token, @RequestBody B body,
                                           HttpServletRequest request) {
        return employeeService.redirectPost(request.getRequestURI(), token, JsonNode.class, body);
    }

    @PutMapping("/**")
    public <B> JsonNode employeeRedirectPut(@RequestHeader("Authorization") String token, @RequestBody B body,
                                          HttpServletRequest request) {
        return employeeService.redirectPut(request.getRequestURI(), token, JsonNode.class, body);
    }
}
