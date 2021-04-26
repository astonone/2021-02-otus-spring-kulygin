package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.domain.Candidate;
import ru.otus.kulygin.dto.CandidateDto;
import ru.otus.kulygin.dto.pageable.CandidatePageableDto;
import ru.otus.kulygin.exception.FileWritingException;
import ru.otus.kulygin.exception.WrongCvFileFormatException;

import java.util.Optional;

public interface CandidateService {

    CandidatePageableDto findAll(Pageable pageable);

    CandidateDto save(Candidate candidate, MultipartFile uploadedFile) throws FileWritingException, WrongCvFileFormatException;

    Optional<CandidateDto> getById(String id);

    void deleteById(String id);

}
