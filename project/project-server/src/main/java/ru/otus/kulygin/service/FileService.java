package ru.otus.kulygin.service;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.exception.FileWritingException;

public interface FileService {

    void writeFile(String storagePath, MultipartFile file) throws FileWritingException;

    void deleteFile(String pathToCvFile);

}
