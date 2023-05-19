package ru.otus.kulygin.templateservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.templateservice.domain.InterviewTemplate;
import ru.otus.kulygin.templateservice.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.templateservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.templateservice.dto.InterviewTemplateDto;
import ru.otus.kulygin.templateservice.dto.pageable.InterviewTemplatePageableDto;
import ru.otus.kulygin.templateservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.templateservice.exception.InterviewTemplateDoesNotExistException;
import ru.otus.kulygin.templateservice.repository.InterviewTemplateRepository;
import ru.otus.kulygin.templateservice.service.CriteriaService;
import ru.otus.kulygin.templateservice.service.InterviewTemplateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InterviewTemplateServiceImpl implements InterviewTemplateService {

    private final InterviewTemplateRepository interviewTemplateRepository;
    private final CriteriaService criteriaService;
    private final MappingService mappingService;

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
    public InterviewTemplatePageableDto findAll() {
        val templates = interviewTemplateRepository.findAll();

        return InterviewTemplatePageableDto.builder()
                .templates(mappingService.mapAsList(templates, InterviewTemplateDto.class))
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
        if (templateById.isPresent()) {
            forSave.setCriterias(templateById.get().getCriterias());
        } else {
            forSave.setCriterias(new ArrayList<>());
        }
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
        if (!criteriaService.existsCriteriaById(criteriaId)) {
            throw new InterviewTemplateCriteriaDoesNotExist();
        }
        final InterviewTemplate interviewTemplate = interviewTemplateRepository.findById(templateId).get();
        interviewTemplate.getCriterias().removeIf(criteria -> criteria.getId().equals(criteriaId));
        return mappingService.map(interviewTemplateRepository.save(interviewTemplate), InterviewTemplateDto.class);
    }

    @Override
    public boolean existByCriteriaId(String criteriaId) {
        return interviewTemplateRepository.existByCriteriaId(criteriaId);
    }

    @Override
    public List<InterviewTemplateDto> findAllByCriteriaId(String criteriaId) {
        return mappingService.mapAsList(interviewTemplateRepository.findAllByCriteriaId(criteriaId), InterviewTemplateDto.class);
    }

    @Override
    public List<InterviewTemplateDto> saveAll(List<InterviewTemplateDto> interviews) {
        return mappingService.mapAsList(interviewTemplateRepository.saveAll(mappingService.mapAsList(interviews, InterviewTemplate.class)), InterviewTemplateDto.class);
    }
}
