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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    @Operation(summary = "Create project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Wrong id format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    public GetProjectDTO create(@RequestBody @Valid CreateProjectDTO dto) {
        return mappingService.mapProjectEntityToGetProjectDTO(projectService.create(mappingService.mapCreateProjectDTOToProjectEntity(dto)));
    }

    @PutMapping("/projects/{id}")
    @Operation(summary = "Update project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Wrong id format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})

    public GetProjectDTO update(@RequestBody @Valid CreateProjectDTO dto, @PathVariable Long id) {
        ProjectEntity entity = projectService.update(mappingService.mapCreateProjectDTOToProjectEntity(dto, id));
        if (entity == null) {
            throw new ResourceNotFoundException("Project " + id + " does not exist");
        }
        return mappingService.mapProjectEntityToGetProjectDTO(entity);
    }

    @GetMapping("/projects/{id}")
    @Operation(summary = "Get project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Wrong id format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    public GetProjectDTO findById(@PathVariable Long id) {
        ProjectEntity entity = projectService.findById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("Project " + id + " does not exist");
        }
        return mappingService.mapProjectEntityToGetProjectDTO(entity);
    }

    @GetMapping("/projects")
    @Operation(summary = "Get all projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Projects not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Wrong id format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    public List<GetProjectDTO> findAll() {
        return projectService.findAll().stream().map(mappingService::mapProjectEntityToGetProjectDTO).toList();
    }

    @DeleteMapping("/projects/{id}")
    @Operation(summary = "Delete project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Wrong id format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    public GetProjectDTO delete(@PathVariable Long id) {
        ProjectEntity entity = projectService.delete(id, employeeAssocService);
        if (entity == null) {
            throw new ResourceNotFoundException("Project " + id + " does not exist");
        }
        return mappingService.mapProjectEntityToGetProjectDTO(entity);
    }

    // Employee Assoc

    @GetMapping("/projects/{id}/employees")
    @Operation(summary = "Get employees by project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetEmployeeAssocDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Wrong id format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    public List<GetEmployeeAssocDTO> getEmployeesByProjectId(@PathVariable Long id) {
        return mappingService.mapEmployeeAssocEntitiesToGetEmployeeAssocDTOs(employeeAssocService.getEmployeesFromProject(id));
    }

    @PostMapping("/projects/{id}/employees")
    @Operation(summary = "Add employee to project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee added to project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Wrong id format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    public GetProjectDTO addEmployeeToProject(@PathVariable Long id, @RequestHeader("Authorization") String token, @RequestBody @Valid CreateEmployeeAssocDTO dto) {
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
    @Operation(summary = "Remove all employees from project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees removed from project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetEmployeeAssocDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Wrong id format",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
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
}
