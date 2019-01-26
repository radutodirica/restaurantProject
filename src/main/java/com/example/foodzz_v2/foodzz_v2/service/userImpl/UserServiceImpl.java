package com.example.foodzz_v2.foodzz_v2.service.userImpl;

import com.example.foodzz_v2.foodzz_v2.dto.userdto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.repository.user.AuthorityRepository;
import com.example.foodzz_v2.foodzz_v2.repository.user.UserRepository;
import com.example.foodzz_v2.foodzz_v2.service.mail.MailService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.service.user.VerificationTokenService;
import com.example.foodzz_v2.foodzz_v2.util.AuthorityName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    VerificationTokenService verificationTokenService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User saveUsername(UserDTO userDTO) throws PersistenceException{

        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User();

        userDTO = userDTO.getUserDTO();

        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setFirstname(userDTO.getFirstName());
        user.setLastname(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setAuthority(authorityRepository.findByName(userDTO.getAuthorityName()));
        user.setEnabled(false);

        this.userRepository.save(user);

        verificationTokenService.sendVerificationToken(userDTO.getUsername());

        return user;
    }

    @Override
    public void updateUser(UserDTO userDTO) throws PersistenceException {

        //userDTO = userDTO.getUserDTO();

        long userId;

        userId = this.userRepository.findByUsername(userDTO.getUsername()).getId();

        this.userRepository.saveUserData(userId,userDTO.getFirstName(),userDTO.getLastName(),userDTO.getEmail());
    }

    @Override
    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

}
