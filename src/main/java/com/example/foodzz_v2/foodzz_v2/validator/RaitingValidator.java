package com.example.foodzz_v2.foodzz_v2.validator;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.RaitingDTO;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Raiting;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RaitingValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return RaitingDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "raitingValue", "text.error.raiting.value.empty");
        ValidationUtils.rejectIfEmpty(errors, "establishmentUUID", "text.error.raiting.establishmentUUID.empty");
    }
}
