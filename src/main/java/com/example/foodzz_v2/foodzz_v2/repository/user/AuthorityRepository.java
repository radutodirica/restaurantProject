package com.example.foodzz_v2.foodzz_v2.repository.user;

import com.example.foodzz_v2.foodzz_v2.model.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String authorityName);
}
