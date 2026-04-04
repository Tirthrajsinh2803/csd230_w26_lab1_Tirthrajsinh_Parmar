package csd230.lab1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import csd230.lab1.entities.MagazineEntity;

public interface MagazineEntityRepository extends JpaRepository<MagazineEntity, Long> {
}