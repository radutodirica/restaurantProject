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
        ValidationUtils.rejectIfEmpty(errors, "name", "text.error.establishment.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "description", "text.error.establishment.description.empty");
        ValidationUtils.rejectIfEmpty(errors, "cuisine", "text.error.establishment.cuisine.empty");
        ValidationUtils.rejectIfEmpty(errors, "latitude", "text.error.establishment.latitude.empty");
        ValidationUtils.rejectIfEmpty(errors, "longitude", "text.error.establishment.longitude.empty");
        ValidationUtils.rejectIfEmpty(errors, "city", "text.error.establishment.cityUUID.empty");
        ValidationUtils.rejectIfEmpty(errors, "county", "text.error.establishment.countyUUID.empty");
        ValidationUtils.rejectIfEmpty(errors, "country", "text.error.establishment.countryUUID.empty");
//        ValidationUtils.rejectIfEmpty(errors, "establishmentPrice", "text.error.establishment.price.empty");
//        ValidationUtils.rejectIfEmpty(errors, "establishmentFeatures", "text.error.establishment.features.empty");
//        ValidationUtils.rejectIfEmpty(errors, "establishmentMeals", "text.error.establishment.meals.empty");
//        ValidationUtils.rejectIfEmpty(errors, "establishmentQualities", "text.error.establishment.qualities.empty");
    }
}
