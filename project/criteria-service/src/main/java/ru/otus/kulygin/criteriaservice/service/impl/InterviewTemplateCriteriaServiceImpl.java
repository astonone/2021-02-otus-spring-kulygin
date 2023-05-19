package ru.otus.kulygin.criteriaservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.criteriaservice.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.criteriaservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.criteriaservice.dto.pageable.InterviewTemplateCriteriaPageableDto;
import ru.otus.kulygin.criteriaservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.criteriaservice.repository.InterviewTemplateCriteriaRepository;
import ru.otus.kulygin.criteriaservice.service.InterviewTemplateCriteriaService;

import java.util.Optional;

@AllArgsConstructor
@Service
public class InterviewTemplateCriteriaServiceImpl implements InterviewTemplateCriteriaService {

    private final InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository;
    private final MappingService mappingService;

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

    @Override
    public InterviewTemplateCriteria processItem(InterviewTemplateCriteria criteria) {
        return interviewTemplateCriteriaRepository.existsByNameAndPositionType(criteria.getName(), criteria.getPositionType())
                ? null
                : criteria;
    }

    @Override
    public boolean existById(String id) {
        return interviewTemplateCriteriaRepository.existsById(id);
    }
}
