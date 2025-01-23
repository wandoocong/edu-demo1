package com.example.demo.service;

import com.example.demo.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * packageName    : com.example.demo.service
 * fileName       : UserService
 * author         : doong2s
 * date           : 2025. 1. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 1. 12.        doong2s       최초 생성
 */
@Service
public class UserService {

    private final String appRunType;
    private final RestTemplate restTemplate;

    @Value("${API_URL}") // 환경 변수에서 값을 가져옴
    private String externalApiBaseUrl;

    public UserService(@Value("${app.run.type:local}") String appRunType, RestTemplate restTemplate) {
        this.appRunType = appRunType;
        this.restTemplate = restTemplate;
    }

    public UserDto getUserByuserNo(String userNo) {
        UserDto userDto = UserDto.builder()
                .userNo(userNo)
                .userName(appRunType + "-" + userNo)
                .build();

        String externalApiUrl = externalApiBaseUrl + userNo;
        String externalResponse = restTemplate.getForObject(externalApiUrl, String.class);

        System.out.println("External API Response: " + externalResponse);
        userDto.setExternalResponse(externalResponse);

        return userDto;
    }
}