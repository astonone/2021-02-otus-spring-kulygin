package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Interview;
import ru.otus.kulygin.dto.InterviewDto;
import ru.otus.kulygin.dto.pageable.InterviewPageableDto;
import ru.otus.kulygin.enumeration.DecisionStatus;
import ru.otus.kulygin.enumeration.InterviewStatus;
import ru.otus.kulygin.exception.InterviewDoesNotExistException;
import ru.otus.kulygin.repository.CandidateRepository;
import ru.otus.kulygin.repository.InterviewRepository;
import ru.otus.kulygin.repository.InterviewTemplateRepository;
import ru.otus.kulygin.repository.InterviewerRepository;
import ru.otus.kulygin.service.InterviewService;
import ru.otus.kulygin.service.impl.util.MappingService;

import java.util.Optional;

@Service
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final CandidateRepository candidateRepository;
    private final InterviewerRepository interviewerRepository;
    private final InterviewTemplateRepository interviewTemplateRepository;
    private final MappingService mappingService;

    public InterviewServiceImpl(InterviewRepository interviewRepository, CandidateRepository candidateRepository, InterviewerRepository interviewerRepository, InterviewTemplateRepository interviewTemplateRepository, MappingService mappingService) {
        this.interviewRepository = interviewRepository;
        this.candidateRepository = candidateRepository;
        this.interviewerRepository = interviewerRepository;
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.mappingService = mappingService;
    }

    @Override
    public InterviewPageableDto findAll(Pageable pageable) {
        val interviews = interviewRepository.findAll(pageable);

        return InterviewPageableDto.builder()
                .page(interviews.getPageable().getPageNumber())
                .pageSize(interviews.getPageable().getPageSize())
                .currentPageSize(interviews.getContent().size())
                .totalSize(interviews.getTotalElements())
                .totalPageSize(interviews.getTotalPages())
                .interviews(mappingService.mapAsList(interviews.getContent(), InterviewDto.class))
                .build();
    }

    @Override
    public InterviewPageableDto findAll() {
        val interviews = interviewRepository.findAll();

        return InterviewPageableDto.builder()
                .interviews(mappingService.mapAsList(interviews, InterviewDto.class))
                .build();
    }

    @Override
    public InterviewPageableDto findAllByInterviewStatus(Pageable pageable, String status) {
        val interviews = interviewRepository.findAllByInterviewStatus(InterviewStatus.valueOf(status), pageable);

        return InterviewPageableDto.builder()
                .page(interviews.getPageable().getPageNumber())
                .pageSize(interviews.getPageable().getPageSize())
                .currentPageSize(interviews.getContent().size())
                .totalSize(interviews.getTotalElements())
                .totalPageSize(interviews.getTotalPages())
                .interviews(mappingService.mapAsList(interviews.getContent(), InterviewDto.class))
                .build();
    }

    @Override
    public InterviewDto save(InterviewDto interviewDto) {
        Interview forSave = Interview.builder().build();
        Optional<Interview> interviewById = Optional.empty();
        if (interviewDto.getId() != null) {
            interviewById = interviewRepository.findById(interviewDto.getId());
            if (interviewById.isEmpty()) {
                throw new InterviewDoesNotExistException();
            }
        }
        forSave.setId(interviewById.map(Interview::getId).orElse(null));
        forSave.setCandidate(candidateRepository.findById(interviewDto.getCandidate().getId()).orElse(null));
        forSave.setInterviewer(interviewerRepository.findById(interviewDto.getInterviewer().getId()).orElse(null));
        forSave.setInterviewDateTime(interviewDto.getInterviewDateTime());
        forSave.setInterviewTemplate(interviewTemplateRepository.findById(interviewDto.getInterviewTemplate().getId()).orElse(null));
        forSave.setInterviewStatus(InterviewStatus.valueOf(interviewDto.getInterviewStatus()));
        forSave.setDesiredSalary(interviewDto.getDesiredSalary());
        forSave.setTotalMark(interviewDto.getTotalMark());
        forSave.setTotalComment(interviewDto.getTotalComment());
        forSave.setDecisionStatus(DecisionStatus.valueOf(interviewDto.getDecisionStatus()));
        return mappingService.map(interviewRepository.save(forSave), InterviewDto.class);
    }

    @Override
    public void deleteById(String id) {
        if (interviewRepository.existsById(id)) {
            interviewRepository.deleteById(id);
        }
    }

}
