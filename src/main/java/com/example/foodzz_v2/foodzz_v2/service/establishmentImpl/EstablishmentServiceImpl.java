package com.example.foodzz_v2.foodzz_v2.service.establishmentImpl;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.EstablishmentDTO;
import com.example.foodzz_v2.foodzz_v2.filter.EstablishmentFilterObject;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Feature;
import com.example.foodzz_v2.foodzz_v2.model.product.Category;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.CommentRepository;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.EstablishmentRepository;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.FeatureRepository;
import com.example.foodzz_v2.foodzz_v2.repository.product.CategoryRepository;
import com.example.foodzz_v2.foodzz_v2.repository.user.UserRepository;
import com.example.foodzz_v2.foodzz_v2.service.establishment.EstablishmentService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.service.userImpl.AuthorityServiceImpl;
import com.example.foodzz_v2.foodzz_v2.util.ObjectMapperUtils;
import com.example.foodzz_v2.foodzz_v2.util.ObjectUtils;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.*;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {

    private final EstablishmentRepository establishmentRepository;
    private final CategoryRepository categoryRepository;
    private final FeatureRepository featureRepository;
    private final UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityServiceImpl authorityService;

    @Autowired
    public EstablishmentServiceImpl(EstablishmentRepository establishmentRepository, CategoryRepository categoryRepository,
                                    FeatureRepository featureRepository, UserRepository userRepository){
        this.establishmentRepository = establishmentRepository;
        this.categoryRepository = categoryRepository;
        this.featureRepository = featureRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<EstablishmentDTO> getAllEstablishments(String username) {
        //Pageable pageable = new PageRequest(establishmentFilterObject.getPage(), establishmentFilterObject.getCount());

        //Page<Establishment> establishmentPage = establishmentRepository.findAll(pageable);
        //return ObjectMapperUtils.mapAll(establishmentPage.getContent(), EstablishmentDTO.class);
        List<Establishment> establishmentList = establishmentRepository.findAll();
        return ObjectUtils.mapEstablishmentsToDto(establishmentList, username);
    }

    @Override
    public List<EstablishmentDTO> getAllEstablishmentsByUser(EstablishmentFilterObject establishmentFilterObject, String username) {
        List<Establishment> establishmentList = establishmentRepository.findAllBy(username);
        return ObjectUtils.mapEstablishmentsToDto(establishmentList, username);
    }

    @Override
    public EstablishmentDTO getByUUID(String establishmentUUID) {
        return ObjectMapperUtils.map(establishmentRepository.findByEstablishmentUUID(establishmentUUID), EstablishmentDTO.class);
    }

    @Override
    public EstablishmentDTO getByName(String name) {
        return ObjectMapperUtils.map(establishmentRepository.findByName(name), EstablishmentDTO.class);
    }

    @Override
    public void createEstablishment(EstablishmentDTO establishmentDTO, String username) throws PersistenceException, UserRightsException, DefinedEntityException {

        if(establishmentRepository.findByName(establishmentDTO.getName()) == null) {
            Establishment establishment = ObjectMapperUtils.map(establishmentDTO, Establishment.class);
            String establishmentUUID = UUID.randomUUID().toString();
            List<User> users = new ArrayList<>();
            User user = userService.getByUsername(username);
            Set<Establishment> establishments = user.getEstablishments();

            users.add(user);
            establishment.setUsers(users);
            establishment.setEstablishmentUUID(establishmentUUID);

            //Set the categories
            if (establishmentDTO.getCategoryList() != null) {
                Set<Category> categorySet = ObjectUtils.setCategoryUuid(establishment, establishmentDTO.getCategoryList());
                establishment.setCategoryList(categorySet);
            }

            //Set the features
            if (establishmentDTO.getFeatures() != null) {
                Set<Feature> featureSet = ObjectUtils.setFeaturesUuid(establishment, establishmentDTO.getFeatures());
                establishment.setFeatures(featureSet);
            }

            //Persist the establishment
            if (authorityService.isUser(user) || authorityService.isAdmin(user))
                establishmentRepository.save(establishment);
            else
                throw new UserRightsException("This user don't have rights to add this establishment!");

            //Persist the user
            establishments.add(establishment);
            user.setEstablishments(establishments);
            userRepository.save(user);
        }
        else
            throw new DefinedEntityException("This establishment is already defined!");

    }

    @Override
    public void updateEstablishment(EstablishmentDTO establishmentDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException {
        Establishment establishment = establishmentRepository.findByEstablishmentUUID(establishmentDTO.getEstablishmentUUID());
        User user = userService.getByUsername(username);

        //Check if the establishment exists
        if(establishment == null)
            throw new MissingEntityException("This establishment do not exist!");

        //Set the new properties to the establishment
        establishment.setName(establishmentDTO.getName());
        establishment.setDescription(establishmentDTO.getDescription());
        establishment.setCuisine(establishmentDTO.getCuisine());
        establishment.setLatitude(establishmentDTO.getLatitude());
        establishment.setLongitude(establishmentDTO.getLongitude());
        establishment.setCity(establishmentDTO.getCity());
        establishment.setCounty(establishmentDTO.getCounty());
        establishment.setCountry(establishmentDTO.getCountry());
        establishment.setCategoryList(ObjectUtils.mapCategoryToUpdate(establishment.getCategoryList(), establishmentDTO.getCategoryList()));
        establishment.setFeatures(ObjectUtils.mapFeatureToUpdate(establishment.getFeatures(), establishmentDTO.getFeatures()));

        //Persist the  changes to DB
        if((authorityService.isUser(user) && establishment.getUsers().contains(user)) || authorityService.isAdmin(user))
            establishmentRepository.save(establishment);
        else
            throw new UserRightsException("This user doesn't have the rights to update this establishment!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEstablishment(String establishmentUUID, String username) throws PersistenceException, UserRightsException, MissingEntityException {
        Establishment establishment = establishmentRepository.findByEstablishmentUUID(establishmentUUID);
        User user = userService.getByUsername(username);

        //Check if the establishment exists
        if(establishment == null)
            throw new MissingEntityException("This establishment do not exist!");

        //Delete the establishment
        if((authorityService.isUser(user) && establishment.getUsers().contains(user)) || authorityService.isAdmin(user)) {
            establishmentRepository.delete(establishment);
            user.getEstablishments().remove(establishment);
            userRepository.save(user);
        }
        else
            throw new UserRightsException("This user doesn't have the rights to delete this establishment!");
    }
}
