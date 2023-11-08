package de.szut.lf8_project.services;

import de.szut.lf8_project.entities.EmployeeAssocEntity;
import de.szut.lf8_project.repos.EmployeeAssocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeAssocService {

    private final EmployeeAssocRepository repository;

    public List<Long> getProjectIdsFromEmployee(Long id) {
        return repository.findAll().stream()
                .filter(entity -> entity.getId().getEmployeeId().equals(id))
                .map(entity -> entity.getId().getProjectId())
                .toList();
    }

    public List<EmployeeAssocEntity> getEmployeesFromProject(Long id) {
        return repository.findAll().stream()
                .filter(entity -> entity.getId().getProjectId().equals(id))
                .toList();
    }

    public EmployeeAssocEntity addToProject(EmployeeAssocEntity entity) {
        return repository.save(entity);
    }

    public EmployeeAssocEntity removeFromProject(Long projectId, Long employeeId) {
        Optional<EmployeeAssocEntity> optional = repository.findAll().stream()
                .filter(entity -> entity.getId().getProjectId().equals(projectId) && entity.getId().getEmployeeId().equals(employeeId))
                .findAny();
        optional.ifPresent(repository::delete);
        return optional.orElse(null);
    }

    public List<EmployeeAssocEntity> removeAllFromProject(Long projectId) {
        List<EmployeeAssocEntity> entities = repository.findAll().stream()
                .filter(entity -> entity.getId().getProjectId().equals(projectId))
                .toList();
        repository.deleteAll(entities);
        return entities;
    }
}
