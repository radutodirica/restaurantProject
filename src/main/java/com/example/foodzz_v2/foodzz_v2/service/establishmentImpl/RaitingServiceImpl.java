package com.example.foodzz_v2.foodzz_v2.service.establishmentImpl;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.RaitingDTO;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Raiting;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.EstablishmentRepository;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.RaitingRepository;
import com.example.foodzz_v2.foodzz_v2.service.establishment.RaitingService;
import com.example.foodzz_v2.foodzz_v2.util.ObjectMapperUtils;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaitingServiceImpl implements RaitingService {

    private final RaitingRepository raitingRepository;
    private final EstablishmentRepository establishmentRepository;

    @Autowired
    public RaitingServiceImpl(RaitingRepository raitingRepository, EstablishmentRepository establishmentRepository) {
        this.raitingRepository = raitingRepository;
        this.establishmentRepository = establishmentRepository;
    }

    @Override
    public RaitingDTO findMyRaitingByEstablishment(String establishmentUUID, String username) {
        Raiting raiting = raitingRepository.findByUsernameAndEstablishmentUUID(username, establishmentUUID);

        if(raiting != null)
            return ObjectMapperUtils.map(raiting, RaitingDTO.class);
        else
            return null;
    }

    @Override
    public void addRaiting(RaitingDTO raitingDTO, String username) throws DefinedEntityException {

        //Check if the user already set a raiting
        if(raitingRepository.findByUsernameAndEstablishmentUUID(username, raitingDTO.getEstablishmentUUID()) != null)
            throw new DefinedEntityException("This user : " + username + " already set a raiting for the establishment : " + raitingDTO.getEstablishmentUUID());
        else
        {
            Raiting raiting = ObjectMapperUtils.map(raitingDTO, Raiting.class);
            raiting.setUsername(username);
            raiting.setEstablishment(establishmentRepository.findByEstablishmentUUID(raitingDTO.getEstablishmentUUID()));
            raitingRepository.save(raiting);
        }
    }

    @Override
    public void removeRaiting(RaitingDTO raitingDTO, String username) throws MissingEntityException {

        Raiting raiting = raitingRepository.findByUsernameAndEstablishmentUUID(username, raitingDTO.getEstablishmentUUID());

        //Check if the raiting is set
        if(raiting != null)
            raitingRepository.delete(raiting);
        else
            throw new MissingEntityException("The raiting you're trying to remove couldn't be found!");

    }

    @Override
    public void editRaiting(RaitingDTO raitingDTO, String username) throws MissingEntityException {

        Raiting raiting = raitingRepository.findByUsernameAndEstablishmentUUID(username, raitingDTO.getEstablishmentUUID());

        //Check if the raiting is set
        if (raiting != null)
        {
            raiting.setRaitingValue(raitingDTO.getRaitingValue());
            raitingRepository.save(raiting);
        }
        else
            throw new MissingEntityException("The raiting you're trying to update couldn't be found!");
    }
}
