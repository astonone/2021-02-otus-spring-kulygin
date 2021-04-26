package ru.otus.kulygin.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.exception.FileWritingException;
import ru.otus.kulygin.service.FileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    private final String storageDir;

    public FileServiceImpl(@Value("${file.storage.path}") String storageDir) {
        this.storageDir = storageDir;
    }

    @Override
    public void writeFile(String storagePath, MultipartFile file) throws FileWritingException {
        createStorageDir();
        File cv = new File(storagePath);
        try (FileOutputStream fos = new FileOutputStream(cv)) {
            fos.write(file.getBytes());
            fos.flush();
        } catch (IOException e) {
            throw new FileWritingException();
        }
    }

    private void createStorageDir() {
        File directory = new File(storageDir);
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
