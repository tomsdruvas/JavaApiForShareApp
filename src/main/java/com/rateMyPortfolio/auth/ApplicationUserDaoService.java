package com.rateMyPortfolio.auth;

import static com.rateMyPortfolio.security.ApplicationUserRole.ADMIN;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository("fake")
public class ApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUserDetails> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
            .stream()
            .filter(applicationUser -> username.equals(applicationUser.getUsername()))
            .findFirst();
    }

    private List<ApplicationUserDetails> getApplicationUsers() {
        return Lists.newArrayList(
            new ApplicationUserDetails(
                "admin",
                passwordEncoder.encode("admin"),
                ADMIN.getGrantedAuthorities(),
                true,
                true,
                true,
                true
            )
        );
    }

}
