package com.api.pasarela_dressy.external.imageservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageUploaderService
{
    List<String> imageUploader(List<MultipartFile> images);
}
