package com.example.foodzz_v2.foodzz_v2.util;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.EstablishmentDTO;
import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.FeatureDTO;
import com.example.foodzz_v2.foodzz_v2.dto.productdto.CategoryDTO;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Feature;
import com.example.foodzz_v2.foodzz_v2.model.product.Category;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Raiting;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ObjectUtils {

    public static Set<Category> setCategoryUuid(Establishment establishment, Set<CategoryDTO> categoryDTOSet){
        Set<Category> categoryList = new HashSet<>();
        for(CategoryDTO categoryDTO : categoryDTOSet){
            categoryDTO.setEstablishmentUUID(establishment.getEstablishmentUUID());
            categoryDTO.setCategoryUUID(UUID.randomUUID().toString());
            Category category = ObjectMapperUtils.map(categoryDTO, Category.class);
            category.setEstablishment(establishment);
            categoryList.add(category);
        }
        return categoryList;
    }

    public static Set<Feature> setFeaturesUuid(Establishment establishment, Set<FeatureDTO> featureDTOSet){
        Set<Feature> featuresList = new HashSet<>();
        for(FeatureDTO featureDTO : featureDTOSet){
            featureDTO.setFeatureUUID(UUID.randomUUID().toString());
            featureDTO.setEstablishmentUUID(establishment.getEstablishmentUUID());
            Feature features = ObjectMapperUtils.map(featureDTO, Feature.class);
            features.setFeaturesEstablishment(establishment);
            featuresList.add(features);
        }
        return featuresList;
    }

    public static List<EstablishmentDTO> mapEstablishmentsToDto(List<Establishment> establishments, String username){
        List<EstablishmentDTO> establishmentDTOS = ObjectMapperUtils.mapAll(establishments, EstablishmentDTO.class);
        int i = 0;
        for(Establishment establishment : establishments){

            //Set user raiting
            establishmentDTOS.get(i).setUserRaiting(getMyRaiting(establishment.getRaitings(), username));

            //Set establishment raiting
            establishmentDTOS.get(i).setEstablishmentRaiting(getEstablishmentRaiting(establishment.getRaitings()));

            //Map the category to categoryDTO
            if(establishment.getCategoryList().size() > 0){
                Set<CategoryDTO> categoryDTOS = ObjectMapperUtils.mapAllSet(establishment.getCategoryList(), CategoryDTO.class);
                establishmentDTOS.get(i).setCategoryList(categoryDTOS);
            }

            //Map the feature to featureDTO
            if(establishment.getFeatures().size() > 0){
                Set<FeatureDTO> featureDTOS = ObjectMapperUtils.mapAllSet(establishment.getFeatures(), FeatureDTO.class);
                establishmentDTOS.get(i).setFeatures(featureDTOS);
            }
            i++;
        }
        return establishmentDTOS;
    }

    public static Set<Category> mapCategoryToUpdate(Set<Category> categories, Set<CategoryDTO> categoryDTOS){
        if(categoryDTOS == null)
            return categories;

        for(Category category : categories){
            for(CategoryDTO categoryDTO : categoryDTOS){
                if(category.getCategoryUUID().equals(categoryDTO.getCategoryUUID()))
                    category.setName(categoryDTO.getName());
            }
        }
        return categories;
    }

    public static Set<Feature> mapFeatureToUpdate(Set<Feature> features, Set<FeatureDTO> featureDTOS){
        if(featureDTOS == null)
            return  features;

        for(Feature feature : features){
            for(FeatureDTO featureDTO : featureDTOS){
                if(feature.getFeatureUUID().equals(featureDTO.getFeatureUUID()))
                    feature.setFeatureContent(featureDTO.getFeatureContent());
            }
        }
        return features;
    }

    public static double getEstablishmentRaiting(List<Raiting> raitings){
        double establishmentRaiting = 0;
        if(raitings.size() > 0){
            for(Raiting raiting : raitings){
                establishmentRaiting = establishmentRaiting + raiting.getRaitingValue();
            }
            return establishmentRaiting/raitings.size();
        }
        else
            return establishmentRaiting;
    }

    public static double getMyRaiting(List<Raiting> raitings, String username){
        double establishmnetRaiting = 0;
        if(raitings.size() > 0){
            for(Raiting raiting : raitings){
                if(raiting.getUsername().equals(username))
                    establishmnetRaiting = raiting.getRaitingValue();
            }
        }
        return establishmnetRaiting;
    }
}
