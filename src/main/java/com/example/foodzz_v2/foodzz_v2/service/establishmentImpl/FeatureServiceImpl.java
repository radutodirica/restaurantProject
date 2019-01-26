package com.example.foodzz_v2.foodzz_v2.service.establishmentImpl;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.FeatureDTO;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Feature;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.EstablishmentRepository;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.FeatureRepository;
import com.example.foodzz_v2.foodzz_v2.repository.user.UserRepository;
import com.example.foodzz_v2.foodzz_v2.service.establishment.FeatureService;
import com.example.foodzz_v2.foodzz_v2.service.userImpl.AuthorityServiceImpl;
import com.example.foodzz_v2.foodzz_v2.util.ObjectMapperUtils;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService{

    private final FeatureRepository featureRepository;
    private final UserRepository userRepository;
    private final EstablishmentRepository establishmentRepository;

    @Autowired
    AuthorityServiceImpl authorityService;

    @Autowired
    public FeatureServiceImpl(FeatureRepository featureRepository, UserRepository userRepository, EstablishmentRepository establishmentRepository) {
        this.featureRepository = featureRepository;
        this.userRepository = userRepository;
        this.establishmentRepository = establishmentRepository;
    }

    @Override
    public List<FeatureDTO> getAllFeatureByEstablishment(String establishmentUUID) {
        return ObjectMapperUtils.mapAll(featureRepository.findAllBy(establishmentUUID), FeatureDTO.class);
    }

    @Override
    public FeatureDTO getByUUID(String featureUUID) {
        return ObjectMapperUtils.map(featureRepository.findBy(featureUUID), FeatureDTO.class);
    }

    @Override
    public void createFeature(FeatureDTO featureDTO, String username, String establishmentUUID) throws PersistenceException, UserRightsException {
        Feature feature = ObjectMapperUtils.map(featureDTO, Feature.class);

        //Find the user
        User user = userRepository.findByUsername(username);

        //Persist the feature to DB
        if(authorityService.isUser(user) && feature.getFeaturesEstablishment().getUsers().contains(user)
                || authorityService.isAdmin(user)){
            feature.setFeaturesEstablishment(establishmentRepository.findByEstablishmentUUID(establishmentUUID));
            featureRepository.save(feature);
        }
        else
            throw new UserRightsException("This user don't have rights to create feature for this establishment!");
    }

    @Override
    public void updateFeature(FeatureDTO featureDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException {
        Feature feature = featureRepository.findBy(featureDTO.getFeatureUUID());

        //Check if the feature exists
        if(feature == null)
            throw new MissingEntityException("The feature you are trying to update doesn't exist!");

        //Set the new content of the feature
        feature.setFeatureContent(featureDTO.getFeatureContent());

        //Persist the feature to DB
        User user = userRepository.findByUsername(username);
        if(authorityService.isUser(user) && feature.getFeaturesEstablishment().getUsers().contains(user)
                || authorityService.isAdmin(user))
            featureRepository.save(feature);
        else
            throw new UserRightsException("This user don't have rights to update feature for this establishment!");
    }

    @Override
    public void deleteFeature(FeatureDTO featureDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException {
        Feature feature = featureRepository.findBy(featureDTO.getFeatureUUID());

        //Check if the feature exists
        if(feature == null)
            throw new MissingEntityException("The feature you're trying to delete doesn't exists!");

        //Find the user
        User user = userRepository.findByUsername(username);

        //Delete the feature
        if(authorityService.isUser(user) && feature.getFeaturesEstablishment().getUsers().contains(user)
                || authorityService.isAdmin(user))
            featureRepository.delete(feature);
        else
            throw new UserRightsException("This user don't have rights to delete feature for this establishment!");
    }
}
