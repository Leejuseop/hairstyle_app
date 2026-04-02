package com.juseop.hair_simulator.service.apiService;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.FileSystemResource;

@Service
public class BackgroundRemovalApiService {

    private final String API_KEY = "S6kYvqXoh9dwkymc6nZoZy4a";
    private final String API_URL = "https://api.remove.bg/v1.0/removebg";

    public byte[] removeBackground(String sourcePath) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", API_KEY);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image_file", new FileSystemResource(sourcePath));
            body.add("size", "auto");
            body.add("bg_color", "white");

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<byte[]> response = restTemplate.postForEntity(API_URL, requestEntity, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception e) {
            System.err.println("누끼 API 호출 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}