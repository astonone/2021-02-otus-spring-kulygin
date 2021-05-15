package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.dto.InterviewerDto;
import ru.otus.kulygin.dto.pageable.InterviewerPageableDto;
import ru.otus.kulygin.exception.InterviewerDoesNotExistException;
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
    public InterviewerPageableDto findAll() {
        val interviewers = interviewerRepository.findAll();

        return InterviewerPageableDto.builder()
                .interviewers(mappingService.mapAsList(interviewers, InterviewerDto.class))
                .build();
    }

    @Override
    public InterviewerDto save(InterviewerDto interviewerDto) {
        Interviewer forSave = Interviewer.builder().build();
        Optional<Interviewer> interviewerById = Optional.empty();
        if (interviewerDto.getId() != null) {
            interviewerById = interviewerRepository.findById(interviewerDto.getId());
            if (interviewerById.isEmpty()) {
                throw new InterviewerDoesNotExistException();
            }
        }
        forSave.setId(interviewerById.map(Interviewer::getId).orElse(null));
        forSave.setFirstName(interviewerDto.getFirstName());
        forSave.setLastName(interviewerDto.getLastName());
        forSave.setPositionType(interviewerDto.getPositionType());
        return mappingService.map(interviewerRepository.save(forSave), InterviewerDto.class);
    }

    @Override
    public void deleteById(String id) {
        if (interviewerRepository.existsById(id)) {
            interviewerRepository.deleteById(id);
        }
    }

}
