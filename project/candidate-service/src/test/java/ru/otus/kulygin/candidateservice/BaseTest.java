package ru.otus.kulygin.candidateservice;

import lombok.val;
import org.apache.http.entity.ContentType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.candidateservice.domain.Candidate;
import ru.otus.kulygin.candidateservice.dto.CandidateDto;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BaseTest {

    protected static final String TEST_INPUT_FILE_NAME = "criterias.csv";
    protected static final String TEST_INPUT_FILE_NAME_CV = "cvExample.pdf";

    protected static Pageable getPageRequestP0PS10() {
        return PageRequest.of(0, 10);
    }

    protected static List<Candidate> getCandidates() {
        val candidate = Candidate.builder()
                .id("1")
                .firstName("Виктор")
                .lastName("Кулыгин")
                .claimingPosition("Java Software Architect")
                .build();

        val candidate2 = Candidate.builder()
                .id("2")
                .firstName("Ирина")
                .lastName("Кулыгина")
                .claimingPosition("Java Developer")
                .interviewerComment("Должен быть толковый джун")
                .build();

        val candidate3 = Candidate.builder()
                .id("3")
                .firstName("Андрей")
                .lastName("Оськин")
                .claimingPosition("Java Senior Developer")
                .build();

        return Arrays.asList(candidate, candidate2, candidate3);
    }

    protected static List<CandidateDto> getCandidatesDto() {
        val candidate = CandidateDto.builder()
                .id("1")
                .firstName("Виктор")
                .lastName("Кулыгин")
                .claimingPosition("Java Software Architect")
                .build();

        val candidate2 = CandidateDto.builder()
                .id("2")
                .firstName("Ирина")
                .lastName("Кулыгина")
                .claimingPosition("Java Developer")
                .interviewerComment("Должен быть толковый джун")
                .build();

        val candidate3 = CandidateDto.builder()
                .id("3")
                .firstName("Андрей")
                .lastName("Оськин")
                .claimingPosition("Java Senior Developer")
                .build();

        return Arrays.asList(candidate, candidate2, candidate3);
    }

    protected static MultipartFile getMockCsvMultipartFile() {
        var classLoader = BaseTest.class.getClassLoader();

        var testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_FILE_NAME)).getFile(),
                StandardCharsets.UTF_8);
        FileSystemResource testInputFileResource = new FileSystemResource(testInputFileName);
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(testInputFileResource.getFile().getName(),
                    testInputFileResource.getFile().getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), testInputFileResource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return multipartFile;
    }

    protected static MultipartFile getMockCVMultipartFile() {
        var classLoader = BaseTest.class.getClassLoader();

        var testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource(TEST_INPUT_FILE_NAME_CV)).getFile(),
                StandardCharsets.UTF_8);
        FileSystemResource testInputFileResource = new FileSystemResource(testInputFileName);
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(testInputFileResource.getFile().getName(),
                    testInputFileResource.getFile().getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), testInputFileResource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return multipartFile;
    }
}
