package com.example.foodzz_v2.foodzz_v2.service.implementation;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.EstablishmentDTO;
import com.example.foodzz_v2.foodzz_v2.filter.RestaurantFilterObject;
import com.example.foodzz_v2.foodzz_v2.model.User;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;
import com.example.foodzz_v2.foodzz_v2.repository.EstablishmentRepository;
import com.example.foodzz_v2.foodzz_v2.service.EstablishmentService;
import com.example.foodzz_v2.foodzz_v2.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    @Autowired
    public EstablishmentServiceImpl(EstablishmentRepository establishmentRepository){
        this.establishmentRepository = establishmentRepository;
    }

    @Override
    public List<EstablishmentDTO> getAllEstablishmentsBy(RestaurantFilterObject restaurantFilterObject) {
        Pageable pageable = new PageRequest(restaurantFilterObject.getPage(), restaurantFilterObject.getCount(),
                Sort.Direction.DESC, restaurantFilterObject.getCityUUID(), restaurantFilterObject.getCountyUUID(), restaurantFilterObject.getCountryUUID());

        Page<Establishment> restaurantPage = establishmentRepository.findAll(pageable);
        return ObjectMapperUtils.mapAll(restaurantPage.getContent(), EstablishmentDTO.class);
    }

    @Override
    public Establishment getByUUID(String establishmentUUID) {
        return this.establishmentRepository.findByEstablishmentUUID(establishmentUUID);
    }

    @Override
    public Establishment getByName(String name) {
        return this.establishmentRepository.findByName(name);
    }

    @Override
    public Establishment createEstablishment(EstablishmentDTO establishmentDTO, User user) throws PersistenceException{
        return establishmentRepository.save(ObjectMapperUtils.map(establishmentDTO, Establishment.class));
    }

    @Override
    public Establishment updateEstablishment(EstablishmentDTO establishmentDTO, User user) throws PersistenceException {
        return establishmentRepository.save(ObjectMapperUtils.map(establishmentDTO, Establishment.class));
    }

    @Override
    public void deleteEstablishment(String establishmentUUID, User user) throws PersistenceException {
        establishmentRepository.delete(establishmentRepository.findByEstablishmentUUID(establishmentUUID));
    }
}
