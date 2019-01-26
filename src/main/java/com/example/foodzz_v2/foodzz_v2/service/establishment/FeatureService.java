package com.example.foodzz_v2.foodzz_v2.service.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.FeatureDTO;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;

import javax.persistence.PersistenceException;
import java.util.List;

public interface FeatureService {

    List<FeatureDTO> getAllFeatureByEstablishment(String establishmentUUID);
    FeatureDTO getByUUID(String featureUUID);
    void createFeature(FeatureDTO featureDTO, String username, String establishmentUUID) throws PersistenceException, UserRightsException;
    void updateFeature(FeatureDTO featureDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException;
    void deleteFeature(FeatureDTO featureDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException;
}
