package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.GoldenTokenRepository;
import com.authme.authme.data.service.GoldenTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GoldenTokenServiceImpl implements GoldenTokenService {
    private final Long expiryPeriod = 1L;

    private final GoldenTokenRepository goldenTokenRepository;
    private final AuthMeUserRepository userRepository;

    public GoldenTokenServiceImpl(GoldenTokenRepository goldenTokenRepository, AuthMeUserRepository userRepository) {
        this.goldenTokenRepository = goldenTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void deleteToken(String goldenToken) {
        if(goldenTokenRepository.existsById(goldenToken)){
            goldenTokenRepository.deleteById(goldenToken);
        }
    }

    @Override
    public String generateFor(AuthMeUserEntity user) {
        GoldenToken newToken =
                new GoldenToken()
                        .setId(UUID.randomUUID().toString())
                        .setExpiry(LocalDateTime.now().plusMinutes(expiryPeriod))
                        .setUser(user);
        newToken = goldenTokenRepository.saveAndFlush(newToken);
        user.setGoldenToken(newToken);
        userRepository.saveAndFlush(user);
        return newToken.getId();
    }
}
