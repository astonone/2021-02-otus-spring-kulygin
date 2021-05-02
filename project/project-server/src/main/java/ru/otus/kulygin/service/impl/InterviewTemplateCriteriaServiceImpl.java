package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.dto.pageable.InterviewTemplateCriteriaPageableDto;
import ru.otus.kulygin.exception.InterviewTemplateCriteriaDoesNotExist;
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
    public InterviewTemplateCriteriaDto save(InterviewTemplateCriteriaDto criteriaDto) {
        InterviewTemplateCriteria forSave = InterviewTemplateCriteria.builder().build();
        Optional<InterviewTemplateCriteria> criteriaById = Optional.empty();
        if (criteriaDto.getId() != null) {
            criteriaById = interviewTemplateCriteriaRepository.findById(criteriaDto.getId());
            if (criteriaById.isEmpty()) {
                throw new InterviewTemplateCriteriaDoesNotExist();
            }
        }
        forSave.setId(criteriaById.map(InterviewTemplateCriteria::getId).orElse(null));
        forSave.setName(criteriaDto.getName());
        forSave.setPositionType(criteriaDto.getPositionType());
        forSave.setMark(criteriaDto.getMark());
        forSave.setInterviewerComment(criteriaDto.getInterviewerComment());
        return mappingService.map(interviewTemplateCriteriaRepository.save(forSave), InterviewTemplateCriteriaDto.class);
    }

    @Override
    public void deleteById(String id) {
        if (interviewTemplateCriteriaRepository.existsById(id)) {
            interviewTemplateCriteriaRepository.deleteById(id);
        }
    }

}
