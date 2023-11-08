package de.szut.lf8_project.services;

import de.szut.lf8_project.entities.ProjectEntity;
import de.szut.lf8_project.repos.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public ProjectEntity create(ProjectEntity entity) {
        return repository.save(entity);
    }

    public ProjectEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ProjectEntity> findAll() {
        return repository.findAll();
    }

    public ProjectEntity update(ProjectEntity entity) {
        if (findById(entity.getId()) == null) {
            return null;
        }
        return repository.save(entity);
    }

    public ProjectEntity delete(Long id, EmployeeAssocService employeeAssocService) {
        ProjectEntity entity = findById(id);
        if (entity == null) {
            return null;
        }
        repository.delete(entity);
        if (employeeAssocService != null) {
            employeeAssocService.removeAllFromProject(id);
        }
        return entity;
    }

    @Deprecated
    public ProjectEntity delete(Long id) {
        return delete(id, null);
    }
}
