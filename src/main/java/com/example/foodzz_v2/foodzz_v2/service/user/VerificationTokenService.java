package com.example.foodzz_v2.foodzz_v2.service.user;

import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.model.user.VerificationToken;

public interface VerificationTokenService {
    String generateVerificationToken();
    VerificationToken getVerificationToken(String token);
    void sendVerificationToken(String username);
    void deleteVerificationToken(String token);
}
