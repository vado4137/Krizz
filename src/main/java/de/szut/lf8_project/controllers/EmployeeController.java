package de.szut.lf8_project.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import de.szut.lf8_project.dtos.GetProjectDTO;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.services.EmployeeAssocService;
import de.szut.lf8_project.services.EmployeeService;
import de.szut.lf8_project.services.MappingService;
import de.szut.lf8_project.services.ProjectService;
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
    public JsonNode getEmployeeById(@RequestHeader("Authorization") String token, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectGet("/employees/" + id, token, JsonNode.class);
    }

    @PutMapping("/{id}")
    public JsonNode updateEmployeeById(@RequestHeader("Authorization") String token, @RequestBody JsonNode body, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectPut("/employees/" + id, token, JsonNode.class, body);
    }

    @DeleteMapping("/{id}")
    public JsonNode deleteEmployeeById(@RequestHeader("Authorization") String token, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectDelete("/employees/" + id, token, JsonNode.class);
    }

    @GetMapping
    public JsonNode getAllEmployees(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        return employeeService.redirectGet("/employees", token, JsonNode.class);
    }

    @PostMapping
    public JsonNode createEmployee(@RequestHeader("Authorization") String token, @RequestBody JsonNode body, HttpServletRequest request) {
        return employeeService.redirectPost("/employees", token, JsonNode.class, body);
    }

    @GetMapping("/{id}/qualifications")
    public JsonNode getQualificationsByEmployeeId(@RequestHeader("Authorization") String token, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectGet("/employees/" + id + "/qualifications", token, JsonNode.class);
    }

    @PostMapping("/{id}/qualifications")
    public JsonNode addQualificationToEmployeeId(@RequestHeader("Authorization") String token, @RequestBody JsonNode body, HttpServletRequest request, @PathVariable String id) {
        return employeeService.redirectPost("/employees/" + id + "/qualifications", token, JsonNode.class, body);
    }

    @GetMapping("/{id}/projects")
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
