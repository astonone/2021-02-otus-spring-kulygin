package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.dto.pageable.InterviewTemplateCriteriaPageableDto;
import ru.otus.kulygin.repository.InterviewTemplateCriteriaRepository;
import ru.otus.kulygin.service.InterviewTemplateCriteriaService;
import ru.otus.kulygin.service.impl.util.MappingService;

import java.util.Optional;

@Service
public class InterviewTemplateCriteriaServiceImpl implements InterviewTemplateCriteriaService {

    private final InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository;
    private final MappingService mappingService;

    public InterviewTemplateCriteriaServiceImpl(InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository, MappingService mappingService) {
        this.interviewTemplateCriteriaRepository = interviewTemplateCriteriaRepository;
        this.mappingService = mappingService;
    }

    @Override
    public InterviewTemplateCriteriaPageableDto findAll(Pageable pageable) {
        val criterias = interviewTemplateCriteriaRepository.findAll(pageable);

        return InterviewTemplateCriteriaPageableDto.builder()
                .page(criterias.getPageable().getPageNumber())
                .pageSize(criterias.getPageable().getPageSize())
                .currentPageSize(criterias.getContent().size())
                .totalSize(criterias.getTotalElements())
                .totalPageSize(criterias.getTotalPages())
                .interviewTemplateCriterias(mappingService.mapAsList(criterias.getContent(), InterviewTemplateCriteriaDto.class))
                .build();
    }

    @Override
    public InterviewTemplateCriteriaDto save(InterviewTemplateCriteria criteria) {
        return mappingService.map(interviewTemplateCriteriaRepository.save(criteria), InterviewTemplateCriteriaDto.class);
    }

    @Override
    public Optional<InterviewTemplateCriteriaDto> getById(String id) {
        return interviewTemplateCriteriaRepository.findById(id).map(criteria -> mappingService.map(criteria, InterviewTemplateCriteriaDto.class));
    }

    @Override
    public void deleteById(String id) {
        if (interviewTemplateCriteriaRepository.existsById(id)) {
            interviewTemplateCriteriaRepository.deleteById(id);
        }
    }

}
