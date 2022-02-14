package com.authme.authme.data.service.impl;

import com.authme.authme.data.dto.LocationDTO;
import com.authme.authme.data.service.IpLocatorService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IpLocatorServiceImpl implements IpLocatorService {
    private static final String ipLocatorProvider = "http://ip-api.com/json/";
    private final RestTemplate restTemplate;

    public IpLocatorServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getLocationDetailsString(String remoteAddress) {
        LocationDTO location = restTemplate.getForObject(ipLocatorProvider + remoteAddress, LocationDTO.class);
        if(location.getStatus().equals("success")){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(location.getCity());
            stringBuilder.append(", ");
            stringBuilder.append(location.getCountry());
            stringBuilder.append(", lat: ");
            stringBuilder.append(location.getLat());
            stringBuilder.append(", lon: ");
            stringBuilder.append(location.getLon());
            stringBuilder.append(", ISP: ");
            stringBuilder.append(location.getIsp());
            return stringBuilder.toString();
        }
        return "Unable to retrieve ip address location!";
    }
}
