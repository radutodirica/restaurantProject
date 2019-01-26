package com.example.foodzz_v2.foodzz_v2.repository.establishment;

import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

    Establishment findByName(String name);
    List<Establishment> findAllBy(String username);
    Establishment findById(Long id);
    Establishment findByEstablishmentUUID(String establishmentUUID);
}
