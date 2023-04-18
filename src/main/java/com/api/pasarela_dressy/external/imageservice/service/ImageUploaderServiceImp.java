package com.api.pasarela_dressy.external.imageservice.service;

import com.api.pasarela_dressy.external.imageservice.dto.FileUploadResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class ImageUploaderServiceImp
    implements IImageUploaderService
{
    @Autowired
    private RestTemplate restTemplate;

    @Value("${microservices.imageuploader.basepath}")
    private String baseUrl;

    @Override
    public List<String> imageUploader(List<MultipartFile> images)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        for (MultipartFile image : images)
        {
            body.add("files", image.getResource());
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<FileUploadResponseDto> response = restTemplate.postForEntity(
            baseUrl + "images", requestEntity, FileUploadResponseDto.class);

        return Objects.requireNonNull(response.getBody()).urls();
    }
}
