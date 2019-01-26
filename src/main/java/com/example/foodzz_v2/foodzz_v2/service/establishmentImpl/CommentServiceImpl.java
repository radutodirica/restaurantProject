package com.example.foodzz_v2.foodzz_v2.service.establishmentImpl;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.CommentDTO;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Comment;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.CommentRepository;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.EstablishmentRepository;
import com.example.foodzz_v2.foodzz_v2.service.establishment.CommentService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.service.userImpl.AuthorityServiceImpl;
import com.example.foodzz_v2.foodzz_v2.util.ObjectMapperUtils;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EstablishmentRepository establishmentRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityServiceImpl authorityService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, EstablishmentRepository establishmentRepository) {
        this.commentRepository = commentRepository;
        this.establishmentRepository = establishmentRepository;
    }


    @Override
    public List<CommentDTO> getAllEstablishmentCommentsBy(String establishmentUUID) {
        List<Comment> comments = commentRepository.findAllBy(establishmentUUID);
        return ObjectMapperUtils.mapAll(comments, CommentDTO.class);
    }

    @Override
    public CommentDTO getComment(String commentUUID) {
        return ObjectMapperUtils.map(commentRepository.findByCommentUUID(commentUUID), CommentDTO.class);
    }

    @Override
    public void createEstablishmentComment(CommentDTO commentDTO, String establishmentUUID, String username) throws PersistenceException, UserRightsException {
        Comment comment = ObjectMapperUtils.map(commentDTO, Comment.class);
        User user = userService.getByUsername(username);

        //Persist the new comment to DB
        if(authorityService.isAdmin(user) || authorityService.isUser(user)){
            comment.setUser(user);
            comment.setEstablishment(establishmentRepository.findByEstablishmentUUID(establishmentUUID));
            comment.setCommentUUID(UUID.randomUUID().toString());
            commentRepository.save(comment);
        }
        else
            throw new UserRightsException("This user don't have rights to add this comment!");

    }

    @Override
    public void updateEstablishmentComment(CommentDTO commentDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException {
        Comment comment = commentRepository.findByCommentUUID(commentDTO.getCommentUUID());
        User user = userService.getByUsername(username);

        //Check if the comment exists
        if(comment == null)
            throw new MissingEntityException("The comment you are trying to update doesn't exist!");

        //Set the new content of the comment
        comment.setCommentContent(commentDTO.getCommentContent());

        //Persist the comment to DB
        if(authorityService.isAdmin(user) || (authorityService.isUser(user) && comment.getUser().equals(user)))
            commentRepository.save(comment);
        else
            throw new UserRightsException("This user don't have rights to edit this comment!");
    }

    @Override
    public void deleteEstablishmentComment(CommentDTO commentDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException {
        Comment comment = commentRepository.findByCommentUUID(commentDTO.getCommentUUID());
        User user = userService.getByUsername(username);

        //Check if the comment exists
        if(comment == null)
            throw new MissingEntityException("This comment you're trying to delete doesn't exist!");

        //Persist the comment to DB
        if(authorityService.isAdmin(user) || (authorityService.isUser(user) && comment.getUser().equals(user))) {
            Establishment establishment = establishmentRepository.findByEstablishmentUUID(comment.getEstablishment().getEstablishmentUUID());
            establishment.getComments().remove(comment);
            establishmentRepository.save(establishment);
        }
        else
            throw  new UserRightsException("This user don't have rights to delete this comment!");

    }
}
