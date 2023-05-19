package ru.otus.kulygin.candidateservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.candidateservice.exception.FileWritingException;

public interface FileService {

    void writeFile(String storagePath, MultipartFile file) throws FileWritingException;

    void deleteFile(String pathToCvFile);

}
