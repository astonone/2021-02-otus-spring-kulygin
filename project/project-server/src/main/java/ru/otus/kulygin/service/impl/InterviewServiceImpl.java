package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.dto.pageable.InterviewerPageableDto;
import ru.otus.kulygin.repository.InterviewerRepository;
import ru.otus.kulygin.service.InterviewService;
import ru.otus.kulygin.service.impl.util.MappingService;

@Service
public class InterviewServiceImpl implements InterviewService {

    private final InterviewerRepository interviewerRepository;
    private final MappingService mappingService;

    public InterviewServiceImpl(InterviewerRepository interviewerRepository, MappingService mappingService) {
        this.interviewerRepository = interviewerRepository;
        this.mappingService = mappingService;
    }

    @Override
    public InterviewerPageableDto findAll(Pageable pageable) {
        val interviewers = interviewerRepository.findAll(pageable);

        return InterviewerPageableDto.builder()
                .page(interviewers.getPageable().getPageNumber())
                .pageSize(interviewers.getPageable().getPageSize())
                .currentPageSize(interviewers.getContent().size())
                .totalSize(interviewers.getTotalElements())
                .totalPageSize(interviewers.getTotalPages())
                .interviewers(mappingService.mapAsList(interviewers.getContent(), InterviewerDto.class))
                .build();
    }

}
