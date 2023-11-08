package de.szut.lf8_project.service.project;

import de.szut.lf8_project.service.MappingService;
import de.szut.lf8_project.service.employee.EmployeeEntity;
import de.szut.lf8_project.service.employee.EmployeeService;
import de.szut.lf8_project.service.employee.dto.CreateProjectDTO;
import de.szut.lf8_project.service.employee.dto.GetEmployeeDTO;
import de.szut.lf8_project.service.project.dto.GetProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/projects")
public class ProjectController {

    private final ProjectService service;
    private final EmployeeService employeeService;
    private final MappingService mappingService = new MappingService();

    private ResponseEntity<GetProjectDTO> create(CreateProjectDTO entity) {
        return new ResponseEntity<>(mappingService.mapProjectEntityToGetProjectDTO(service.create(mappingService.mapCreateProjectDTOToProjectEntity(entity))), HttpStatus.CREATED);
    }

//    private ResponseEntity<GetProjectDTO> update(CreateProjectDTO entity) {
//        return new ResponseEntity<>(mappingService.mapProjectEntityToGetProjectDTO(service.update(entity)), HttpStatus.OK);
//    }

    @PostMapping
    private ResponseEntity<GetProjectDTO> save(@RequestBody CreateProjectDTO entity) {
//        ProjectEntity result = service.update(entity);
//        if (result == null) {
            return new ResponseEntity<>(mappingService.mapProjectEntityToGetProjectDTO(service.create(mappingService.mapCreateProjectDTOToProjectEntity(entity))), HttpStatus.CREATED);
//        }
        //return new ResponseEntity<>(mappingService.mapProjectEntityToGetProjectDTO(mappingService.mapCreateProjectDTOToProjectEntity(entity)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GetProjectDTO>> findAll() {
        return new ResponseEntity<>(service.findAll().stream().map(mappingService::mapProjectEntityToGetProjectDTO).toList(), HttpStatus.OK);
    }

    @GetMapping( "/{id}")
    public ResponseEntity<GetProjectDTO> findById(@PathVariable long id) {
        ProjectEntity entity = service.findById(id);
        if (entity == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mappingService.mapProjectEntityToGetProjectDTO(entity), HttpStatus.OK);
    }

    @GetMapping( "/{id}/employees")
    public ResponseEntity<GetEmployeeDTO> findEmployeesByProjectId(@RequestHeader(name="Authorization") String token, @PathVariable long id) {
        ResponseEntity<GetProjectDTO> responseEntity = findById(id);
        if (responseEntity.getBody() == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mappingService.mapEmployeeEntityToGetEmployeeDTO(employeeService.getById(token, responseEntity.getBody().getEmployeeId())), HttpStatus.OK);
    }
}
