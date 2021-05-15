package ru.otus.kulygin.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.kulygin.dto.CandidateDto;
import ru.otus.kulygin.dto.pageable.CandidatePageableDto;
import ru.otus.kulygin.exception.FileWritingException;
import ru.otus.kulygin.exception.WrongCvFileFormatException;

public interface CandidateService {

    CandidatePageableDto findAll(Pageable pageable);

    CandidatePageableDto findAll();

    CandidateDto save(CandidateDto candidateDto, MultipartFile uploadedFile) throws FileWritingException, WrongCvFileFormatException;

    void deleteById(String id);

}
