package com.example.foodzz_v2.foodzz_v2.service.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.CommentDTO;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;

import javax.persistence.PersistenceException;
import java.util.List;

public interface CommentService {
    List<CommentDTO> getAllEstablishmentCommentsBy(String establishmentUUID);
    CommentDTO getComment(String commentUUID);
    void createEstablishmentComment(CommentDTO commentDTO, String establishmentUUID, String username) throws PersistenceException, UserRightsException;
    void updateEstablishmentComment(CommentDTO commentDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException;
    void deleteEstablishmentComment(CommentDTO commentDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException;
}
