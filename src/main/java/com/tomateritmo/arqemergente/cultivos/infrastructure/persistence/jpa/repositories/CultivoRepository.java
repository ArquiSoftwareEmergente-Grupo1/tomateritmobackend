package com.tomateritmo.arqemergente.cultivos.infrastructure.persistence.jpa.repositories;

import com.tomateritmo.arqemergente.cultivos.domain.model.aggregates.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Long> {}
