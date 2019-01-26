package com.example.foodzz_v2.foodzz_v2.repository.establishment;

import com.example.foodzz_v2.foodzz_v2.model.establishment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findAllBy(String establishmentUUID);
    Comment findByCommentUUID(String commentUUID);
//    Comment findByName(String name);
//    Comment findByEstablishmentCommentUUID(String establishmentCommentUUID);
}
