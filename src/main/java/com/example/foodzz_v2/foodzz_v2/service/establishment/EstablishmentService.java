package com.example.foodzz_v2.foodzz_v2.service.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.EstablishmentDTO;
import com.example.foodzz_v2.foodzz_v2.filter.EstablishmentFilterObject;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;

import javax.persistence.PersistenceException;
import java.util.List;

public interface EstablishmentService {

    List<EstablishmentDTO> getAllEstablishments(String username);
    List<EstablishmentDTO> getAllEstablishmentsByUser(EstablishmentFilterObject establishmentFilterObject, String username);
    EstablishmentDTO getByUUID(String establishmentUUID);
    EstablishmentDTO getByName(String name);
    void createEstablishment(EstablishmentDTO establishmentDTO, String username) throws PersistenceException, UserRightsException, DefinedEntityException;
    void updateEstablishment(EstablishmentDTO establishmentDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException;
    void deleteEstablishment(String establishmentUUID, String username) throws PersistenceException, UserRightsException, MissingEntityException;

}
