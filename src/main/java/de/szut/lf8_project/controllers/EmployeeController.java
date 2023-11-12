package de.szut.lf8_project.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import de.szut.lf8_project.dtos.GetProjectDTO;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.services.EmployeeAssocService;
import de.szut.lf8_project.services.EmployeeService;
import de.szut.lf8_project.services.MappingService;
import de.szut.lf8_project.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeAssocService employeeAssocService;

    private MappingService mappingService;

    public EmployeeController() {
        this.mappingService = new MappingService(employeeAssocService);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = JsonNode.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public JsonNode getEmployeeById(@RequestHeader("Authorization") String token, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectGet("/employees/" + id, token, JsonNode.class);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonNode.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public JsonNode updateEmployeeById(@RequestHeader("Authorization") String token, @RequestBody JsonNode body, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectPut("/employees/" + id, token, JsonNode.class, body);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonNode.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public JsonNode deleteEmployeeById(@RequestHeader("Authorization") String token, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectDelete("/employees/" + id, token, JsonNode.class);
    }

    @GetMapping
    @Operation(summary = "Get all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonNode.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public JsonNode getAllEmployees(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        return employeeService.redirectGet("/employees", token, JsonNode.class);
    }

    @PostMapping
    @Operation(summary = "Create employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonNode.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public JsonNode createEmployee(@RequestHeader("Authorization") String token, @RequestBody JsonNode body, HttpServletRequest request) {
        return employeeService.redirectPost("/employees", token, JsonNode.class, body);
    }

    @GetMapping("/{id}/qualifications")
    @Operation(summary = "Get qualifications by employee id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Qualifications found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonNode.class))}),
            @ApiResponse(responseCode = "404", description = "Qualifications not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public JsonNode getQualificationsByEmployeeId(@RequestHeader("Authorization") String token, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectGet("/employees/" + id + "/qualifications", token, JsonNode.class);
    }

    @PostMapping("/{id}/qualifications")
    @Operation(summary = "Add qualification to employee id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Qualification added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonNode.class))}),
            @ApiResponse(responseCode = "404", description = "Qualification not added"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public JsonNode addQualificationToEmployeeId(@RequestHeader("Authorization") String token, @RequestBody JsonNode body, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectPost("/employees/" + id + "/qualifications", token, JsonNode.class, body);
    }

    @GetMapping("/{id}/projects")
    @Operation(summary = "Get projects by employee id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonNode.class))}),
            @ApiResponse(responseCode = "404", description = "Projects not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
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
}
