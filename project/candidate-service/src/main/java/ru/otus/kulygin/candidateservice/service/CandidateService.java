package ru.otus.kulygin.candidateservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.candidateservice.dto.CandidateDto;
import ru.otus.kulygin.candidateservice.dto.pageable.CandidatePageableDto;
import ru.otus.kulygin.candidateservice.exception.FileWritingException;
import ru.otus.kulygin.candidateservice.exception.WrongCvFileFormatException;

public interface CandidateService {

    CandidatePageableDto findAll(Pageable pageable);

    CandidatePageableDto findAll();

    CandidateDto save(CandidateDto candidateDto, MultipartFile uploadedFile) throws FileWritingException, WrongCvFileFormatException;

    void deleteById(String id);

    CandidateDto getById(String id);
}
