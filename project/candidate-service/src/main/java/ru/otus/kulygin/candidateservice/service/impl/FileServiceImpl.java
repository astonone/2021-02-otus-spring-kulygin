package ru.otus.kulygin.candidateservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.candidateservice.exception.FileWritingException;
import ru.otus.kulygin.candidateservice.service.FileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public void writeFile(String storagePath, MultipartFile file) throws FileWritingException {
        createStorageDir(storagePath);
        File cv = new File(storagePath);
        try (FileOutputStream fos = new FileOutputStream(cv)) {
            fos.write(file.getBytes());
            fos.flush();
        } catch (IOException e) {
            throw new FileWritingException();
        }
    }

    private void createStorageDir(String storagePath) {
        File directory = new File(storagePath.substring(0, storagePath.lastIndexOf("/")));
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    @Override
    public void deleteFile(String pathToCvFile) {
        File cv = new File(pathToCvFile);
        cv.delete();
    }
}
