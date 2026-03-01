package csd230.lab1.repositories;

import csd230.lab1.entities.ElectronicsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectronicsRepository extends JpaRepository<ElectronicsEntity, Long> {
}