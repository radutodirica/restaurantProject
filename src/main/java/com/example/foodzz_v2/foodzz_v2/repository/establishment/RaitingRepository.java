package com.example.foodzz_v2.foodzz_v2.repository.establishment;

import com.example.foodzz_v2.foodzz_v2.model.establishment.Raiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaitingRepository extends JpaRepository<Raiting, Long> {
    Raiting findByUsernameAndEstablishmentUUID(String username, String establishmentUUID);
    List<Raiting> findByEstablishmentUUID(String establishmentUUID);
    List<Raiting> findAll();
}
