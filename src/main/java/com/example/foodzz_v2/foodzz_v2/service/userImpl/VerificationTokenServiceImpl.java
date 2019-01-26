package com.example.foodzz_v2.foodzz_v2.service.userImpl;

import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.model.user.VerificationToken;
import com.example.foodzz_v2.foodzz_v2.repository.user.UserRepository;
import com.example.foodzz_v2.foodzz_v2.repository.user.VerificationTokenRepository;
import com.example.foodzz_v2.foodzz_v2.service.mail.MailService;
import com.example.foodzz_v2.foodzz_v2.service.user.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private static final int EXPIRATION = 60 * 24;
    private static final String URL = "http://localhost:8080/registrationConfirm.html?token=";

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public void sendVerificationToken(String username) {
        User user = userRepository.findByUsername(username);

        //Create verification token
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(generateVerificationToken());
        verificationToken.setUser(user);
        verificationToken.setExpirationDate(calculateExpiryDate(EXPIRATION));

        //Persist the verification token
        verificationTokenRepository.save(verificationToken);
        //Create verificaiton link
        String verificationLink = URL + verificationToken.getToken();
        //Send the verificaiton mail
        mailService.sendVerificationMail(user.getEmail(), user.getUsername(), verificationLink);
    }

    @Override
    public void deleteVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        User user = verificationToken.getUser();

        //remove the verification token
        user.setVerificationToken(null);
        //Persist the user
        userRepository.save(user);
        //Delete the token from DB
        verificationTokenRepository.delete(verificationToken);
        //Send confirmation e-mail
        mailService.sendConfirmationMail(user.getEmail(), user.getUsername());
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}
