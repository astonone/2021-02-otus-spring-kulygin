package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Interview;
import ru.otus.kulygin.dto.InterviewDto;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.dto.pageable.InterviewPageableDto;
import ru.otus.kulygin.enumeration.DecisionStatus;
import ru.otus.kulygin.enumeration.InterviewStatus;
import ru.otus.kulygin.exception.InterviewDecisionException;
import ru.otus.kulygin.exception.InterviewDoesNotExistException;
import ru.otus.kulygin.exception.InterviewStatusException;
import ru.otus.kulygin.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.repository.CandidateRepository;
import ru.otus.kulygin.repository.InterviewRepository;
import ru.otus.kulygin.repository.InterviewTemplateRepository;
import ru.otus.kulygin.repository.InterviewerRepository;
import ru.otus.kulygin.service.InterviewService;
import ru.otus.kulygin.service.impl.util.MappingService;

import java.util.Optional;
import java.util.stream.DoubleStream;

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
        interviewById.ifPresent(interview -> forSave.getInterviewTemplate().setCriterias(interview.getInterviewTemplate().getCriterias()));
        if (forSave.getId() == null && !InterviewStatus.valueOf(interviewDto.getInterviewStatus()).equals(InterviewStatus.PLANNED)) {
            throw new InterviewStatusException();
        }
        forSave.setInterviewStatus(InterviewStatus.valueOf(interviewDto.getInterviewStatus()));
        forSave.setDesiredSalary(interviewDto.getDesiredSalary());
        forSave.setTotalMark(interviewDto.getTotalMark());
        forSave.setTotalComment(interviewDto.getTotalComment());
        if (forSave.getId() == null && !DecisionStatus.valueOf(interviewDto.getDecisionStatus()).equals(DecisionStatus.NOT_APPLICABLE)) {
            throw new InterviewDecisionException();
        }
        forSave.setDecisionStatus(DecisionStatus.valueOf(interviewDto.getDecisionStatus()));
        return mappingService.map(interviewRepository.save(forSave), InterviewDto.class);
    }

    @Override
    public void deleteById(String id) {
        if (interviewRepository.existsById(id)) {
            interviewRepository.deleteById(id);
        }
    }

    @Override
    public InterviewDto getById(String id) {
        return interviewRepository.findById(id)
                .map(interview -> mappingService.map(interview, InterviewDto.class))
                .orElseThrow(InterviewDoesNotExistException::new);
    }

    @Override
    public InterviewDto updateCriteria(String interviewId, String criteriaId, Integer mark) {
        val interview = interviewRepository.findById(interviewId).orElseThrow(InterviewDoesNotExistException::new);
        val templateCriteria = interview.getInterviewTemplate().getCriterias().stream()
                .filter(criteria -> criteria.getId().equals(criteriaId))
                .findAny().orElseThrow(InterviewTemplateCriteriaDoesNotExist::new);
        templateCriteria.setMark(mark);
        val totalMarksSum = interview.getInterviewTemplate().getCriterias().stream()
                .flatMapToDouble(criteria -> DoubleStream.of(criteria.getMark()))
                .sum();
        val totalMarksCount = interview.getInterviewTemplate().getCriterias().size();
        val total = totalMarksSum / (totalMarksCount * 5);
        interview.setTotalMark((double) Math.round(total * 100) / 100);
        return mappingService.map(interviewRepository.save(interview), InterviewDto.class);
    }

    @Override
    public InterviewDto updateCriteriaComment(String interviewId, String criteriaId, InterviewTemplateCriteriaDto criteria) {
        val interview = interviewRepository.findById(interviewId).orElseThrow(InterviewDoesNotExistException::new);
        val templateCriteria = interview.getInterviewTemplate().getCriterias().stream()
                .filter(c -> c.getId().equals(criteriaId))
                .findAny().orElseThrow(InterviewTemplateCriteriaDoesNotExist::new);
        templateCriteria.setInterviewerComment(criteria.getInterviewerComment());
        return mappingService.map(interviewRepository.save(interview), InterviewDto.class);
    }

}
