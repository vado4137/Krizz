package de.szut.lf8_project.service.project;

import de.szut.lf8_project.service.employee.dto.CreateProjectDTO;
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
        ProjectEntity old = findById(entity.getId());
        if (old == null) {
            return null;
        }
        old.setDescription(entity.getDescription());
        old.setClientId(entity.getClientId());
        //old.setActualEnd(entity.getActualEnd());
        old.setContactName(entity.getContactName());
       // old.setEmployeeId(entity.getEmployeeId());
        //old.setScheduledEnd(entity.getScheduledEnd());
       // old.setStart(entity.getStart());
        return repository.save(entity);
    }

    public ProjectEntity deleteById(Long id) {
        ProjectEntity old = findById(id);
        if (old == null) {
            return null;
        }
        repository.deleteById(id);
        return old;
    }
}
