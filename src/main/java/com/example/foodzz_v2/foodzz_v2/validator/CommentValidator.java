package com.example.foodzz_v2.foodzz_v2.validator;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.CommentDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CommentValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CommentDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "commentContent", "text.error.comment.commentContent.empty");
    }
}
