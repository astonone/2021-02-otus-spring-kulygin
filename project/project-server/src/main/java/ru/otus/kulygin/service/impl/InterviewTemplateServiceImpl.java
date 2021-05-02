package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.InterviewTemplate;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.dto.InterviewTemplateDto;
import ru.otus.kulygin.dto.pageable.InterviewTemplatePageableDto;
import ru.otus.kulygin.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.exception.InterviewTemplateDoesNotExistException;
import ru.otus.kulygin.repository.InterviewTemplateCriteriaRepository;
import ru.otus.kulygin.repository.InterviewTemplateRepository;
import ru.otus.kulygin.service.InterviewTemplateService;
import ru.otus.kulygin.service.impl.util.MappingService;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class InterviewTemplateServiceImpl implements InterviewTemplateService {

    private final InterviewTemplateRepository interviewTemplateRepository;
    private final InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository;
    private final MappingService mappingService;

    public InterviewTemplateServiceImpl(InterviewTemplateRepository interviewTemplateRepository, InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository, MappingService mappingService) {
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.interviewTemplateCriteriaRepository = interviewTemplateCriteriaRepository;
        this.mappingService = mappingService;
    }

    @Override
    public InterviewTemplatePageableDto findAll(Pageable pageable) {
        val templates = interviewTemplateRepository.findAll(pageable);

        return InterviewTemplatePageableDto.builder()
                .page(templates.getPageable().getPageNumber())
                .pageSize(templates.getPageable().getPageSize())
                .currentPageSize(templates.getContent().size())
                .totalSize(templates.getTotalElements())
                .totalPageSize(templates.getTotalPages())
                .templates(mappingService.mapAsList(templates.getContent(), InterviewTemplateDto.class))
                .build();
    }

    @Override
    public InterviewTemplateDto save(InterviewTemplateDto templateDto) {
        InterviewTemplate forSave = InterviewTemplate.builder().build();
        Optional<InterviewTemplate> templateById = Optional.empty();
        if (templateDto.getId() != null) {
            templateById = interviewTemplateRepository.findById(templateDto.getId());
            if (templateById.isEmpty()) {
                throw new InterviewTemplateDoesNotExistException();
            }
        }
        forSave.setId(templateById.map(InterviewTemplate::getId).orElse(null));
        forSave.setPositionName(templateDto.getPositionName());
        forSave.setCriterias(new ArrayList<>());
        return mappingService.map(interviewTemplateRepository.save(forSave), InterviewTemplateDto.class);
    }

    @Override
    public void deleteById(String id) {
        if (interviewTemplateRepository.existsById(id)) {
            interviewTemplateRepository.deleteById(id);
        }
    }

    @Override
    public InterviewTemplateDto getById(String id) {
        return interviewTemplateRepository.findById(id)
                .map(template -> mappingService.map(template, InterviewTemplateDto.class))
                .orElseThrow(InterviewTemplateDoesNotExistException::new);
    }

    @Override
    public InterviewTemplateDto addCriteria(String templateId, InterviewTemplateCriteriaDto criteria) {
        if (!interviewTemplateRepository.existsById(templateId)) {
            throw new InterviewTemplateDoesNotExistException();
        }
        final InterviewTemplate interviewTemplate = interviewTemplateRepository.findById(templateId).get();
        interviewTemplate.getCriterias().add(mappingService.map(criteria, InterviewTemplateCriteria.class));
        return mappingService.map(interviewTemplateRepository.save(interviewTemplate), InterviewTemplateDto.class);
    }

    @Override
    public InterviewTemplateDto deleteCriteria(String templateId, String criteriaId) {
        if (!interviewTemplateRepository.existsById(templateId)) {
            throw new InterviewTemplateDoesNotExistException();
        }
        if (!interviewTemplateCriteriaRepository.existsById(criteriaId)) {
            throw new InterviewTemplateCriteriaDoesNotExist();
        }
        final InterviewTemplate interviewTemplate = interviewTemplateRepository.findById(templateId).get();
        interviewTemplate.getCriterias().removeIf(criteria -> criteria.getId().equals(criteriaId));
        return mappingService.map(interviewTemplateRepository.save(interviewTemplate), InterviewTemplateDto.class);
    }

}
