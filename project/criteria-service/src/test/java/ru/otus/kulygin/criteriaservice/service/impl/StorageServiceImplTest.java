package ru.otus.kulygin.criteriaservice.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.criteriaservice.BaseTest;
import ru.otus.kulygin.criteriaservice.service.StorageService;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
@SpringBootTest(classes = StorageServiceImpl.class)
class StorageServiceImplTest extends BaseTest {

    @Autowired
    private StorageService storageService;

    @Test
    void shouldStoreFile() {
        val result = storageService.store(getMockCsvMultipartFile());

        assertThat(result).isNotNull();
        assertThat(result.exists()).isTrue();
        deleteTestFile(result.getAbsolutePath());
    }

    private static void deleteTestFile(String absolutePath) {
        File file = new File(absolutePath);
        file.delete();
    }
}