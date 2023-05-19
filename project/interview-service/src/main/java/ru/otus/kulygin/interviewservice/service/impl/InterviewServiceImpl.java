package ru.otus.kulygin.interviewservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.interviewservice.domain.Candidate;
import ru.otus.kulygin.interviewservice.domain.Interview;
import ru.otus.kulygin.interviewservice.domain.InterviewTemplate;
import ru.otus.kulygin.interviewservice.domain.Interviewer;
import ru.otus.kulygin.interviewservice.dto.InterviewDto;
import ru.otus.kulygin.interviewservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.interviewservice.dto.pageable.InterviewPageableDto;
import ru.otus.kulygin.interviewservice.enumerations.DecisionStatus;
import ru.otus.kulygin.interviewservice.enumerations.InterviewStatus;
import ru.otus.kulygin.interviewservice.exception.InterviewDecisionException;
import ru.otus.kulygin.interviewservice.exception.InterviewDoesNotExistException;
import ru.otus.kulygin.interviewservice.exception.InterviewStatusException;
import ru.otus.kulygin.interviewservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.interviewservice.repository.InterviewRepository;
import ru.otus.kulygin.interviewservice.service.CandidateService;
import ru.otus.kulygin.interviewservice.service.InterviewService;
import ru.otus.kulygin.interviewservice.service.InterviewerService;
import ru.otus.kulygin.interviewservice.service.TemplateService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@AllArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final CandidateService candidateService;
    private final InterviewerService interviewerService;
    private final TemplateService templateService;
    private final MappingService mappingService;

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
        forSave.setCandidate(mappingService.map(candidateService.getCandidateById(interviewDto.getCandidate().getId()), Candidate.class));
        forSave.setInterviewer(mappingService.map(interviewerService.getInterviewerById(interviewDto.getInterviewer().getId()), Interviewer.class));
        forSave.setInterviewDateTime(interviewDto.getInterviewDateTime());
        forSave.setInterviewTemplate(mappingService.map(templateService.getInterviewTemplateById(interviewDto.getInterviewTemplate().getId()), InterviewTemplate.class));
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

    @Override
    public boolean existsByCandidateId(String candidateId) {
        return interviewRepository.existsByCandidate_Id(candidateId);
    }

    @Override
    public List<InterviewDto> findAllByCandidateId(String candidateId) {
        return mappingService.mapAsList(interviewRepository.findAllByCandidate_Id(candidateId), InterviewDto.class);
    }

    @Override
    public List<InterviewDto> saveAll(List<InterviewDto> interviews) {
        return mappingService.mapAsList(interviewRepository.saveAll(mappingService.mapAsList(interviews, Interview.class)), InterviewDto.class);
    }

    @Override
    public boolean existsByInterviewerId(String interviewerId) {
        return interviewRepository.existsByInterviewer_Id(interviewerId);
    }

    @Override
    public List<InterviewDto> findAllByInterviewerId(String interviewerId) {
        return mappingService.mapAsList(interviewRepository.findAllByInterviewer_Id(interviewerId), InterviewDto.class);
    }

    @Override
    public List<InterviewDto> findAllPlannedByCriteriaId(String criteriaId) {
        return mappingService.mapAsList(interviewRepository.findAllByCriteriaId(criteriaId).stream()
                .filter(interview -> interview.getInterviewStatus().equals(InterviewStatus.PLANNED))
                .collect(Collectors.toList()), InterviewDto.class);
    }

    @Override
    public boolean existsByInterviewTemplateId(String templateId) {
        return interviewRepository.existsByInterviewTemplate_Id(templateId);
    }

    @Override
    public List<InterviewDto> findAllByInterviewTemplateIdAndInterviewStatus(String templateId) {
        return mappingService.mapAsList(interviewRepository.findAllByInterviewTemplate_IdAndInterviewStatus(templateId, InterviewStatus.PLANNED), InterviewDto.class);
    }
}
