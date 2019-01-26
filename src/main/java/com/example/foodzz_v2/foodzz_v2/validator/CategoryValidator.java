package com.example.foodzz_v2.foodzz_v2.validator;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.CategoryDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CategoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CategoryDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "text.error.category.name.empty");
    }
}
