package ru.otus.kulygin.criteriaservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface StorageService {
    File store(MultipartFile multipartFile);
}
