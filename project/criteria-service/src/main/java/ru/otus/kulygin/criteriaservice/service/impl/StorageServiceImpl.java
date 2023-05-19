package ru.otus.kulygin.criteriaservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.criteriaservice.exception.FileWritingException;
import ru.otus.kulygin.criteriaservice.service.StorageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageServiceImpl implements StorageService {

    private final String storagePath;

    public StorageServiceImpl(@Value("${file.storage.csv.path}") String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public File store(MultipartFile multipartFile) {
        createStorageDir();
        File file = new File(storagePath + multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            fos.flush();
        } catch (IOException e) {
            throw new FileWritingException();
        }
        return file;
    }

    private void createStorageDir() {
        File directory = new File(storagePath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
