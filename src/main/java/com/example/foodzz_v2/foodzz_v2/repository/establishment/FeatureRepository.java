package com.example.foodzz_v2.foodzz_v2.repository.establishment;

import com.example.foodzz_v2.foodzz_v2.model.establishment.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findAllBy(String establishmentUUID);
    Feature findBy(String featureUUID);
}
