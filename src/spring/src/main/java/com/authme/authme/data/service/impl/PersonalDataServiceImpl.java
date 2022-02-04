package com.authme.authme.data.service.impl;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.service.PersonalDataService;
import com.authme.authme.exceptions.CommonErrorMessages;
import com.authme.authme.utils.ClassMapper;
import com.authme.authme.utils.RemoteEndpoints;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class PersonalDataServiceImpl implements PersonalDataService {
    private final RestTemplate restTemplate;
    private final AuthMeUserRepository userRepository;
    private final ClassMapper classMapper;

    public PersonalDataServiceImpl(RestTemplateBuilder restTemplateBuilder, AuthMeUserRepository userRepository, ClassMapper classMapper) {
        restTemplate = restTemplateBuilder.build();
        this.userRepository = userRepository;
        this.classMapper = classMapper;
    }

    @Override
    public Long newEntry() {
        return restTemplate.getForObject(RemoteEndpoints.entry(), Long.class);
    }

    @Override
    public ProfileBindingModel getBindingModel() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AuthMeUserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> CommonErrorMessages.username(username));
        ProfileDTO profileDTO = restTemplate.getForObject(RemoteEndpoints.profile(user.getDataId()), ProfileDTO.class);
        return classMapper.toProfileBindingModel(profileDTO);
    }

    @Override
    public void patchProfile(ProfileBindingModel profileBindingModel) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AuthMeUserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> CommonErrorMessages.username(username));

        ProfileDTO profileDTO = classMapper.toProfileDTO(profileBindingModel);
        restTemplate.postForLocation(RemoteEndpoints.profile(user.getDataId()), profileDTO);
    }
}
