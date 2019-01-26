package com.example.foodzz_v2.foodzz_v2.repository.user;

import com.example.foodzz_v2.foodzz_v2.model.user.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
