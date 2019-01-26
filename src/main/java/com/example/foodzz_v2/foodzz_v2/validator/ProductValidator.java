package com.example.foodzz_v2.foodzz_v2.validator;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.ProductDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ProductDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "text.error.product.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "price", "text.error.product.price.empty");
    }
}
