package com.example.foodzz_v2.foodzz_v2.repository;

import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

    Establishment findByName(String name);
    Establishment findById(Long id);
    Establishment findByEstablishmentUUID(String establishmentUUID);
}
