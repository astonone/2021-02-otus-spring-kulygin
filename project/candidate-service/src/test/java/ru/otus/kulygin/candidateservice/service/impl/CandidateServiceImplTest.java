package ru.otus.kulygin.candidateservice.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.candidateservice.BaseTest;
import ru.otus.kulygin.candidateservice.domain.Candidate;
import ru.otus.kulygin.candidateservice.dto.CandidateDto;
import ru.otus.kulygin.candidateservice.dto.pageable.CandidatePageableDto;
import ru.otus.kulygin.candidateservice.exception.CandidateDoesNotExistException;
import ru.otus.kulygin.candidateservice.exception.FileWritingException;
import ru.otus.kulygin.candidateservice.exception.WrongCvFileFormatException;
import ru.otus.kulygin.candidateservice.repository.CandidateRepository;
import ru.otus.kulygin.candidateservice.service.CandidateService;
import ru.otus.kulygin.candidateservice.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = CandidateServiceImpl.class)
class CandidateServiceImplTest extends BaseTest {

    @Autowired
    private CandidateService candidateService;

    @MockBean
    private CandidateRepository candidateRepository;

    @MockBean
    private MappingService mappingService;

    @MockBean
    private FileService fileService;

    @Test
    void shouldFindAllPageable() {
        final Page<Candidate> candidatePage = new PageImpl<>(getCandidates(), getPageRequestP0PS10(), 10);
        when(candidateRepository.findAll(getPageRequestP0PS10())).thenReturn(candidatePage);
        when(mappingService.mapAsList(candidatePage.getContent(), CandidateDto.class)).thenReturn(getCandidatesDto());

        final CandidatePageableDto result = candidateService.findAll(getPageRequestP0PS10());

        assertThat(result).isNotNull();
        assertThat(result.getPage()).isEqualTo(candidatePage.getPageable().getPageNumber());
        assertThat(result.getPageSize()).isEqualTo(candidatePage.getPageable().getPageSize());
        assertThat(result.getCurrentPageSize()).isEqualTo(candidatePage.getContent().size());
        assertThat(result.getTotalSize()).isEqualTo(candidatePage.getTotalElements());
        assertThat(result.getTotalPageSize()).isEqualTo(candidatePage.getTotalPages());
        assertThat(result.getCandidates()).isEqualTo(getCandidatesDto());
    }

    @Test
    void shouldFindAll() {
        when(candidateRepository.findAll()).thenReturn(getCandidates());
        when(mappingService.mapAsList(getCandidates(), CandidateDto.class)).thenReturn(getCandidatesDto());

        final CandidatePageableDto result = candidateService.findAll();

        assertThat(result).isNotNull();
        assertThat(result.getCandidates()).isEqualTo(getCandidatesDto());
    }

    @Test
    void shouldSaveCreateModeWithCv() throws FileWritingException, WrongCvFileFormatException {
        val candidateDto = getCandidatesDto().get(0);
        candidateDto.setId(null);
        val candidateForSave = getCandidates().get(0);
        candidateForSave.setId(null);
        candidateForSave.setPathToCvFile("test/cvExample.pdf");
        when(candidateRepository.save(candidateForSave)).thenReturn(candidateForSave);
        when(mappingService.map(candidateForSave, CandidateDto.class)).thenReturn(candidateDto);

        final CandidateDto result = candidateService.save(candidateDto, getMockCVMultipartFile());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(candidateDto);
    }

    @Test
    void shouldSaveCreateModeWithoutCv() throws FileWritingException, WrongCvFileFormatException {
        val candidateDto = getCandidatesDto().get(0);
        candidateDto.setId(null);
        val candidateForSave = getCandidates().get(0);
        candidateForSave.setId(null);
        when(candidateRepository.save(candidateForSave)).thenReturn(candidateForSave);
        when(mappingService.map(candidateForSave, CandidateDto.class)).thenReturn(candidateDto);

        final CandidateDto result = candidateService.save(candidateDto, null);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(candidateDto);
    }

    @Test
    void shouldNotSaveCreateModeWithCvBecauseWrongFileFormat() {
        val candidateDto = getCandidatesDto().get(0);
        candidateDto.setId(null);
        val candidateForSave = getCandidates().get(0);
        candidateForSave.setId(null);
        when(candidateRepository.save(candidateForSave)).thenReturn(candidateForSave);
        when(mappingService.map(candidateForSave, CandidateDto.class)).thenReturn(candidateDto);

        assertThatThrownBy(() -> candidateService.save(candidateDto, getMockCsvMultipartFile()))
                .isInstanceOf(WrongCvFileFormatException.class);
    }

    @Test
    void shouldSaveUpdateModeWithCv() throws FileWritingException, WrongCvFileFormatException {
        val candidateDto = getCandidatesDto().get(0);
        val candidateForSave = getCandidates().get(0);
        candidateForSave.setPathToCvFile("test/cvExample.pdf");
        val existedCandidate = Candidate.builder()
                .id("1")
                .firstName("Виктор")
                .lastName("Кулыгин")
                .claimingPosition("Team Lead")
                .pathToCvFile("test/cvExample2.pdf")
                .build();
        when(candidateRepository.findById("1")).thenReturn(Optional.of(existedCandidate));
        when(candidateRepository.save(candidateForSave)).thenReturn(candidateForSave);
        when(mappingService.map(candidateForSave, CandidateDto.class)).thenReturn(candidateDto);

        final CandidateDto result = candidateService.save(candidateDto, getMockCVMultipartFile());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(candidateDto);
        verify(fileService).deleteFile(existedCandidate.getPathToCvFile());
        verify(fileService).writeFile(eq(candidateForSave.getPathToCvFile()), any());
    }

    @Test
    void shouldSaveUpdateModeBecauseCandidateDoesntExists() {
        val candidateDto = getCandidatesDto().get(0);

        assertThatThrownBy(() -> candidateService.save(candidateDto, getMockCsvMultipartFile()))
                .isInstanceOf(CandidateDoesNotExistException.class);
    }

    @Test
    void shouldDeleteById() {
        when(candidateRepository.existsById("1")).thenReturn(true);
        candidateService.deleteById("1");

        verify(candidateRepository).existsById("1");
        verify(candidateRepository).deleteById("1");
    }

    @Test
    void shouldGetById() {
        when(candidateRepository.findById("1")).thenReturn(Optional.of(getCandidates().get(0)));
        when(mappingService.map(getCandidates().get(0), CandidateDto.class)).thenReturn(getCandidatesDto().get(0));

        final CandidateDto result = candidateService.getById("1");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(getCandidatesDto().get(0));
    }

    @Test
    void shouldNotGetByIdBecauseUserDoesNotExists() {
        assertThatThrownBy(() -> candidateService.getById("1"))
                .isInstanceOf(CandidateDoesNotExistException.class);
    }
}