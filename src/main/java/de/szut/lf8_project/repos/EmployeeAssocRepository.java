package de.szut.lf8_project.repos;

import de.szut.lf8_project.entities.EmployeeAssocEntity;
import de.szut.lf8_project.entities.EmployeeAssocId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeAssocRepository extends JpaRepository<EmployeeAssocEntity, EmployeeAssocId> {
}
