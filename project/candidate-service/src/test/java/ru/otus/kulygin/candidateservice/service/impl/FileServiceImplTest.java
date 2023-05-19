package ru.otus.kulygin.candidateservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.candidateservice.BaseTest;
import ru.otus.kulygin.candidateservice.exception.FileWritingException;
import ru.otus.kulygin.candidateservice.service.FileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(classes = FileServiceImpl.class)
class FileServiceImplTest extends BaseTest {

    public static final String TEST_INPUT_FILE_NAME_DELETION = "testDeleteFolder/criterias.csv";
    public static final String TEST_INPUT_DIR_NAME = "testWriteFolder";
    public static final String TEST_INPUT_DIR_NAME_DELETION = "testDeleteFolder";

    @Autowired
    private FileService fileService;

    @Test
    void shouldWriteFile() throws IOException, FileWritingException {
        var classLoader = FileServiceImplTest.class.getClassLoader();
        var testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_FILE_NAME)).getFile(),
                StandardCharsets.UTF_8);
        String storagePath = testInputFileName.substring(0, testInputFileName.lastIndexOf("/")) +
                "/" + TEST_INPUT_DIR_NAME + "/" + TEST_INPUT_FILE_NAME;

        fileService.writeFile(storagePath, getMockCsvMultipartFile());

        var testOutputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_DIR_NAME + "/" + TEST_INPUT_FILE_NAME)).getFile(),
                StandardCharsets.UTF_8);

        assertThat(testOutputFileName).isNotNull();
        deleteAfterWriteTestFile();
    }

    @Test
    void shouldDeleteFile() throws IOException, FileWritingException {
        createTestFileAndDir();
        var classLoader = FileServiceImplTest.class.getClassLoader();

        var testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_FILE_NAME_DELETION)).getFile(),
                StandardCharsets.UTF_8);

        fileService.deleteFile(testInputFileName);

        assertThatThrownBy(() -> URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_FILE_NAME_DELETION)).getFile(),
                StandardCharsets.UTF_8))
                .isInstanceOf(NullPointerException.class);
    }

    private static void createTestFileAndDir() throws IOException, FileWritingException {
        var classLoader = FileServiceImplTest.class.getClassLoader();

        createStorageDir(classLoader);

        var testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_FILE_NAME)).getFile(),
                StandardCharsets.UTF_8);
        String storagePath = testInputFileName.substring(0, testInputFileName.lastIndexOf("/")) +
                "/" + TEST_INPUT_FILE_NAME_DELETION;

        File csv = new File(storagePath);
        try (FileOutputStream fos = new FileOutputStream(csv)) {
            fos.write(getMockCsvMultipartFile().getBytes());
            fos.flush();
        } catch (IOException e) {
            throw new FileWritingException();
        }
    }

    private static String createStorageDir(ClassLoader classLoader) {
        var testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_FILE_NAME)).getFile(),
                StandardCharsets.UTF_8);

        File directory = new File(testInputFileName.substring(0, testInputFileName.lastIndexOf("/"))
                + "/" + TEST_INPUT_DIR_NAME_DELETION);
        if (!directory.exists()) {
            directory.mkdir();
            return testInputFileName.substring(0, testInputFileName.lastIndexOf("/"));
        } else {
            return null;
        }
    }

    private static void deleteAfterWriteTestFile() {
        var classLoader = FileServiceImplTest.class.getClassLoader();
        var testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_DIR_NAME + "/" + TEST_INPUT_FILE_NAME)).getFile(),
                StandardCharsets.UTF_8);

        File file = new File(testInputFileName);
        file.delete();
    }
}