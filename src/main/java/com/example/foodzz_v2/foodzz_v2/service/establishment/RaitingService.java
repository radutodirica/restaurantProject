package com.example.foodzz_v2.foodzz_v2.service.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.RaitingDTO;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;

public interface RaitingService {
    RaitingDTO findMyRaitingByEstablishment(String establishmentUUID, String username);
    void addRaiting(RaitingDTO raitingDTO, String username) throws DefinedEntityException;
    void removeRaiting(RaitingDTO raitingDTO, String username) throws MissingEntityException;
    void editRaiting(RaitingDTO raitingDTO, String username) throws MissingEntityException;
}
