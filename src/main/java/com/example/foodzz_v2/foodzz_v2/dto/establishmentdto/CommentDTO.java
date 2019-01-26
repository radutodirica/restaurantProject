package com.example.foodzz_v2.foodzz_v2.dto.establishmentdto;

public class CommentDTO {

    public CommentDTO(){}

    public String getCommentUUID() {
        return commentUUID;
    }

    public void setCommentUUID(String commentUUID) {
        this.commentUUID = commentUUID;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    private String commentUUID;
    private String commentContent;
}
