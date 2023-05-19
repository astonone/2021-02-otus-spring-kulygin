package ru.otus.kulygin.criteriaservice.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.criteriaservice.BaseTest;
import ru.otus.kulygin.criteriaservice.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.criteriaservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.criteriaservice.dto.pageable.InterviewTemplateCriteriaPageableDto;
import ru.otus.kulygin.criteriaservice.exception.InterviewTemplateCriteriaDoesNotExist;
import ru.otus.kulygin.criteriaservice.repository.InterviewTemplateCriteriaRepository;
import ru.otus.kulygin.criteriaservice.service.InterviewTemplateCriteriaService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = InterviewTemplateCriteriaServiceImpl.class)
class InterviewTemplateCriteriaServiceImplTest extends BaseTest {

    @Autowired
    private InterviewTemplateCriteriaService interviewTemplateCriteriaService;

    @MockBean
    private InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository;

    @MockBean
    private MappingService mappingService;

    @Test
    void shouldFindAllPageable() {
        final Page<InterviewTemplateCriteria> criteriaPage = new PageImpl<>(getCriterias(), getPageRequestP0PS10(), 10);
        when(interviewTemplateCriteriaRepository.findAll(getPageRequestP0PS10())).thenReturn(criteriaPage);
        when(mappingService.mapAsList(criteriaPage.getContent(), InterviewTemplateCriteriaDto.class)).thenReturn(getCriteriasDto());

        final InterviewTemplateCriteriaPageableDto result = interviewTemplateCriteriaService.findAll(getPageRequestP0PS10());

        assertThat(result).isNotNull();
        assertThat(result.getPage()).isEqualTo(criteriaPage.getPageable().getPageNumber());
        assertThat(result.getPageSize()).isEqualTo(criteriaPage.getPageable().getPageSize());
        assertThat(result.getCurrentPageSize()).isEqualTo(criteriaPage.getContent().size());
        assertThat(result.getTotalSize()).isEqualTo(criteriaPage.getTotalElements());
        assertThat(result.getTotalPageSize()).isEqualTo(criteriaPage.getTotalPages());
        assertThat(result.getInterviewTemplateCriterias()).isEqualTo(getCriteriasDto());
    }

    @Test
    void shouldSaveCreateMode() {
        val criteria = getCriteriasDto().get(0);
        criteria.setId(null);
        val criteriaForSave = getCriterias().get(0);
        criteriaForSave.setId(null);
        when(interviewTemplateCriteriaRepository.save(criteriaForSave)).thenReturn(getCriterias().get(0));
        when(mappingService.map(getCriterias().get(0), InterviewTemplateCriteriaDto.class)).thenReturn(getCriteriasDto().get(0));

        val result = interviewTemplateCriteriaService.save(criteria);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(getCriteriasDto().get(0));
    }

    @Test
    void shouldSaveUpdateMode() {
        val criteria = getCriteriasDto().get(0);
        val criteriaForSave = getCriterias().get(0);
        when(interviewTemplateCriteriaRepository.findById(criteria.getId())).thenReturn(Optional.of(criteriaForSave));
        when(interviewTemplateCriteriaRepository.save(criteriaForSave)).thenReturn(getCriterias().get(0));
        when(mappingService.map(getCriterias().get(0), InterviewTemplateCriteriaDto.class)).thenReturn(getCriteriasDto().get(0));

        val result = interviewTemplateCriteriaService.save(criteria);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(getCriteriasDto().get(0));
    }

    @Test
    void shouldNotSaveUpdateModeCriteriaDoesntExists() {
        val criteria = getCriteriasDto().get(0);

        assertThatThrownBy(() -> interviewTemplateCriteriaService.save(criteria))
                .isInstanceOf(InterviewTemplateCriteriaDoesNotExist.class);
    }

    @Test
    void shouldDeleteById() {
        when(interviewTemplateCriteriaRepository.existsById("1")).thenReturn(true);
        interviewTemplateCriteriaService.deleteById("1");

        verify(interviewTemplateCriteriaRepository).existsById("1");
        verify(interviewTemplateCriteriaRepository).deleteById("1");
    }

    @Test
    void shouldProcessItem() {
        val interviewTemplateCriteria = getCriterias().get(0);
        when(interviewTemplateCriteriaRepository.existsByNameAndPositionType(interviewTemplateCriteria.getName(),
                interviewTemplateCriteria.getPositionType())).thenReturn(false);

        val result = interviewTemplateCriteriaService.processItem(interviewTemplateCriteria);

        assertThat(result).isEqualTo(interviewTemplateCriteria);
    }

    @Test
    void shouldNotProcessItem() {
        val interviewTemplateCriteria = getCriterias().get(0);
        when(interviewTemplateCriteriaRepository.existsByNameAndPositionType(interviewTemplateCriteria.getName(),
                interviewTemplateCriteria.getPositionType())).thenReturn(true);

        val result = interviewTemplateCriteriaService.processItem(interviewTemplateCriteria);

        assertThat(result).isNull();
    }

    @Test
    void shouldExistById() {
        interviewTemplateCriteriaService.existById("1");

        verify(interviewTemplateCriteriaRepository).existsById("1");
    }
}