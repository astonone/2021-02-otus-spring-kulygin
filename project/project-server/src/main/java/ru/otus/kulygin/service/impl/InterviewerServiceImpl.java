package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.dto.pageable.InterviewerPageableDto;
import ru.otus.kulygin.repository.InterviewerRepository;
import ru.otus.kulygin.service.InterviewerService;
import ru.otus.kulygin.service.impl.util.MappingService;

import java.util.Optional;

@Service
public class InterviewerServiceImpl implements InterviewerService {

    private final InterviewerRepository interviewerRepository;
    private final MappingService mappingService;

    public InterviewerServiceImpl(InterviewerRepository interviewerRepository, MappingService mappingService) {
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

    @Override
    public InterviewerDto update(Interviewer interviewer) {
        return mappingService.map(interviewerRepository.save(interviewer), InterviewerDto.class);
    }

    @Override
    public Optional<InterviewerDto> getById(String id) {
        return interviewerRepository.findById(id).map(interviewer -> mappingService.map(interviewer, InterviewerDto.class));
    }

    @Override
    public void deleteById(String id) {
        if (interviewerRepository.existsById(id)) {
            try {
                interviewerRepository.deleteById(id);
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

}
