package com.example.foodzz_v2.foodzz_v2.validator;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.EstablishmentDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class EstablishmentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return EstablishmentDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "description", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "cuisine", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "latitude", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "longitude", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "cityUUID", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "countyUUID", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "countryUUID", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "establishmentPrice", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "establishmentFeatures", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "establishmentMeals", "text.error.article.empty");
        ValidationUtils.rejectIfEmpty(errors, "establishmentQualities", "text.error.article.empty");
    }
}
