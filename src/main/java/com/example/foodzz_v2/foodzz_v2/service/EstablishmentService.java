package com.example.foodzz_v2.foodzz_v2.service;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.EstablishmentDTO;
import com.example.foodzz_v2.foodzz_v2.filter.RestaurantFilterObject;
import com.example.foodzz_v2.foodzz_v2.model.User;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;

import javax.persistence.PersistenceException;
import java.util.List;

public interface EstablishmentService {

    public List<EstablishmentDTO> getAllEstablishmentsBy(RestaurantFilterObject restaurantFilterObject);
    public Establishment getByUUID(String establishmentUUID);
    public Establishment getByName(String name);
    public Establishment createEstablishment(EstablishmentDTO establishmentDTO, User user)throws PersistenceException;
    public Establishment updateEstablishment(EstablishmentDTO establishmentDTO, User user)throws PersistenceException;
    public void deleteEstablishment(String establishmentUUID, User user)throws PersistenceException;

}
