package ru.otus.kulygin.candidateservice.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

@Service
public class CandidateServiceImpl implements CandidateService {

    public static final String PDF = "pdf";
    public static final String DOT = ".";

    private final CandidateRepository candidateRepository;
    private final MappingService mappingService;
    private final FileService fileService;
    private final String storagePath;

    public CandidateServiceImpl(CandidateRepository candidateRepository, MappingService mappingService,
                                FileService fileService,
                                @Value("${file.storage.path}") String storagePath) {
        this.candidateRepository = candidateRepository;
        this.mappingService = mappingService;
        this.fileService = fileService;
        this.storagePath = storagePath;
    }

    @Override
    public CandidatePageableDto findAll(Pageable pageable) {
        val candidates = candidateRepository.findAll(pageable);

        return CandidatePageableDto.builder()
                .page(candidates.getPageable().getPageNumber())
                .pageSize(candidates.getPageable().getPageSize())
                .currentPageSize(candidates.getContent().size())
                .totalSize(candidates.getTotalElements())
                .totalPageSize(candidates.getTotalPages())
                .candidates(mappingService.mapAsList(candidates.getContent(), CandidateDto.class))
                .build();
    }

    @Override
    public CandidatePageableDto findAll() {
        val candidates = candidateRepository.findAll();

        return CandidatePageableDto.builder()
                .candidates(mappingService.mapAsList(candidates, CandidateDto.class))
                .build();
    }

    @Override
    public CandidateDto save(CandidateDto candidateDto, MultipartFile uploadedFile) throws FileWritingException, WrongCvFileFormatException {
        Candidate forSave = Candidate.builder().build();
        Optional<Candidate> candidateById = Optional.empty();
        if (candidateDto.getId() != null) {
            candidateById = candidateRepository.findById(candidateDto.getId());
            if (candidateById.isEmpty()) {
                throw new CandidateDoesNotExistException();
            }
        }
        forSave.setId(candidateById.map(Candidate::getId).orElse(null));
        forSave.setFirstName(candidateDto.getFirstName());
        forSave.setLastName(candidateDto.getLastName());
        forSave.setClaimingPosition(candidateDto.getClaimingPosition());
        forSave.setInterviewerComment(candidateDto.getInterviewerComment());

        if (uploadedFile != null) {
            if (candidateDto.getId() != null) {
                deletePreviousCvFileIfExists(candidateDto.getId());
            }
            if (!getFileExtension(uploadedFile).equalsIgnoreCase(PDF)) {
                throw new WrongCvFileFormatException();
            }
            String filePath = getFinalPath(uploadedFile);
            fileService.writeFile(filePath, uploadedFile);
            forSave.setPathToCvFile(filePath);
        } else {
            forSave.setPathToCvFile(candidateById.map(Candidate::getPathToCvFile).orElse(null));
        }

        return mappingService.map(candidateRepository.save(forSave), CandidateDto.class);
    }

    private void deletePreviousCvFileIfExists(String id) {
        candidateRepository.findById(id).ifPresent(candidate -> {
            if (candidate.getPathToCvFile() != null) {
                fileService.deleteFile(candidate.getPathToCvFile());
            }
        });
    }

    private String getFinalPath(MultipartFile multipartFile) {
        return storagePath + multipartFile.getOriginalFilename();
    }

    private String getFileExtension(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(DOT) + 1);
    }

    @Override
    public void deleteById(String id) {
        if (candidateRepository.existsById(id)) {
            deletePreviousCvFileIfExists(id);
            candidateRepository.deleteById(id);
        }
    }

    @Override
    public CandidateDto getById(String id) {
        return candidateRepository.findById(id)
                .map(candidate -> mappingService.map(candidate, CandidateDto.class))
                .orElseThrow(CandidateDoesNotExistException::new);
    }
}
