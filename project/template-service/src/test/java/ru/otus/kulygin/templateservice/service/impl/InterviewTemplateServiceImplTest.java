package ru.otus.kulygin.templateservice.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.templateservice.BaseTest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = InterviewTemplateServiceImpl.class)
class InterviewTemplateServiceImplTest extends BaseTest {

    @Autowired
    private InterviewTemplateService interviewTemplateService;

    @MockBean
    private InterviewTemplateRepository interviewTemplateRepository;

    @MockBean
    private CriteriaService criteriaService;

    @MockBean
    private MappingService mappingService;

    @Test
    void shouldFindAllPageable() {
        final Page<InterviewTemplate> templatePage = new PageImpl<>(getTemplates(), getPageRequestP0PS10(), 10);
        when(interviewTemplateRepository.findAll(getPageRequestP0PS10())).thenReturn(templatePage);
        when(mappingService.mapAsList(templatePage.getContent(), InterviewTemplateDto.class)).thenReturn(getTemplatesDto());

        final InterviewTemplatePageableDto result = interviewTemplateService.findAll(getPageRequestP0PS10());

        assertThat(result).isNotNull();
        assertThat(result.getPage()).isEqualTo(templatePage.getPageable().getPageNumber());
        assertThat(result.getPageSize()).isEqualTo(templatePage.getPageable().getPageSize());
        assertThat(result.getCurrentPageSize()).isEqualTo(templatePage.getContent().size());
        assertThat(result.getTotalSize()).isEqualTo(templatePage.getTotalElements());
        assertThat(result.getTotalPageSize()).isEqualTo(templatePage.getTotalPages());
        assertThat(result.getTemplates()).isEqualTo(getTemplatesDto());
    }

    @Test
    void shouldFindAll() {
        when(interviewTemplateRepository.findAll()).thenReturn(getTemplates());
        when(mappingService.mapAsList(getTemplates(), InterviewTemplateDto.class)).thenReturn(getTemplatesDto());

        final InterviewTemplatePageableDto result = interviewTemplateService.findAll();

        assertThat(result).isNotNull();
        assertThat(result.getTemplates()).isEqualTo(getTemplatesDto());
    }

    @Test
    void shouldSaveCrateMode() {
        final InterviewTemplateDto interviewTemplateDto = getTemplatesDto().get(0);
        interviewTemplateDto.setId(null);
        final InterviewTemplate interviewTemplateForSave = getTemplates().get(0);
        interviewTemplateForSave.setId(null);
        interviewTemplateForSave.setCriterias(new ArrayList<>());

        when(interviewTemplateRepository.save(interviewTemplateForSave)).thenReturn(interviewTemplateForSave);
        when(mappingService.map(interviewTemplateForSave, InterviewTemplateDto.class))
                .thenReturn(getTemplatesDto().get(0));

        final InterviewTemplateDto result = interviewTemplateService.save(interviewTemplateDto);

        assertThat(result).isEqualTo(getTemplatesDto().get(0));
    }

    @Test
    void shouldSaveUpdateMode() {
        final InterviewTemplateDto interviewTemplateDto = getTemplatesDto().get(0);
        interviewTemplateDto.setPositionName("test");
        final InterviewTemplate existedInterviewTemplate = getTemplates().get(0);
        final InterviewTemplate interviewTemplateForSave = InterviewTemplate.builder()
                .id("1")
                .positionName("test")
                .criterias(new ArrayList<>(getTemplates().get(0).getCriterias()))
                .build();
        final InterviewTemplateDto expectedTemplateForSave = InterviewTemplateDto.builder()
                .id("1")
                .positionName("test")
                .criterias(new ArrayList<>(getTemplatesDto().get(0).getCriterias()))
                .build();

        when(interviewTemplateRepository.findById("1")).thenReturn(Optional.of(existedInterviewTemplate));
        when(interviewTemplateRepository.save(interviewTemplateForSave)).thenReturn(interviewTemplateForSave);
        when(mappingService.map(interviewTemplateForSave, InterviewTemplateDto.class))
                .thenReturn(expectedTemplateForSave);

        final InterviewTemplateDto result = interviewTemplateService.save(interviewTemplateDto);

        assertThat(result).isEqualTo(expectedTemplateForSave);
    }

    @Test
    void shouldNotSaveUpdateMode() {
        assertThatThrownBy(() -> interviewTemplateService.save(getTemplatesDto().get(0)))
                .isInstanceOf(InterviewTemplateDoesNotExistException.class);
    }

    @Test
    void shouldDeleteById() {
        when(interviewTemplateRepository.existsById("1")).thenReturn(true);
        interviewTemplateService.deleteById("1");

        verify(interviewTemplateRepository).existsById("1");
        verify(interviewTemplateRepository).deleteById("1");
    }

    @Test
    void shouldGetById() {
        when(interviewTemplateRepository.findById("1")).thenReturn(Optional.of(getTemplates().get(0)));
        when(mappingService.map(getTemplates().get(0), InterviewTemplateDto.class)).thenReturn(getTemplatesDto().get(0));

        final InterviewTemplateDto result = interviewTemplateService.getById("1");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(getTemplatesDto().get(0));
    }

    @Test
    void interviewTemplateService() {
        assertThatThrownBy(() -> interviewTemplateService.getById("1"))
                .isInstanceOf(InterviewTemplateDoesNotExistException.class);
    }

    @Test
    void shouldAddCriteria() {
        val criteria6Dto = InterviewTemplateCriteriaDto.builder()
                .id("6")
                .name("Могут ли нестатические методы перегрузить статические?")
                .positionType("Java Developer")
                .build();

        val criteria6 = InterviewTemplateCriteria.builder()
                .id("6")
                .name("Могут ли нестатические методы перегрузить статические?")
                .positionType("Java Developer")
                .build();
        final InterviewTemplate interviewTemplateForSave = InterviewTemplate.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(new ArrayList<>(getTemplates().get(0).getCriterias()))
                .build();
        final InterviewTemplateDto expectedInterviewTemplate = InterviewTemplateDto.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(new ArrayList<>(getTemplatesDto().get(0).getCriterias()))
                .build();
        expectedInterviewTemplate.getCriterias().add(criteria6Dto);
        when(interviewTemplateRepository.existsById("1")).thenReturn(true);
        when(interviewTemplateRepository.findById("1")).thenReturn(Optional.of(interviewTemplateForSave));
        when(mappingService.map(criteria6Dto, InterviewTemplateCriteria.class)).thenReturn(criteria6);
        when(interviewTemplateRepository.save(interviewTemplateForSave)).thenReturn(interviewTemplateForSave);
        when(mappingService.map(interviewTemplateForSave, InterviewTemplateDto.class)).thenReturn(expectedInterviewTemplate);

        final InterviewTemplateDto interviewTemplateDto = interviewTemplateService.addCriteria("1", criteria6Dto);

        assertThat(interviewTemplateDto).isEqualTo(expectedInterviewTemplate);
    }

    @Test
    void shouldNotAddCriteriaInterviewTemplateDoesNotExistException() {
        val criteria6Dto = InterviewTemplateCriteriaDto.builder()
                .id("6")
                .name("Могут ли нестатические методы перегрузить статические?")
                .positionType("Java Developer")
                .build();


        assertThatThrownBy(() -> interviewTemplateService.addCriteria("1", criteria6Dto))
                .isInstanceOf(InterviewTemplateDoesNotExistException.class);
    }


    @Test
    void shouldDeleteCriteria() {
        val criteria6 = InterviewTemplateCriteria.builder()
                .id("6")
                .name("Могут ли нестатические методы перегрузить статические?")
                .positionType("Java Developer")
                .build();

        final InterviewTemplate existedTemplate = InterviewTemplate.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(new ArrayList<>(getTemplates().get(0).getCriterias()))
                .build();
        existedTemplate.getCriterias().add(criteria6);
        final InterviewTemplateDto expectedInterviewTemplate = InterviewTemplateDto.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(new ArrayList<>(getTemplatesDto().get(0).getCriterias()))
                .build();
        expectedInterviewTemplate.getCriterias().removeIf(criteria -> criteria.getId().equals("6"));
        when(interviewTemplateRepository.existsById("1")).thenReturn(true);
        when(criteriaService.existsCriteriaById("6")).thenReturn(true);
        when(interviewTemplateRepository.findById("1")).thenReturn(Optional.of(existedTemplate));
        when(interviewTemplateRepository.save(existedTemplate)).thenReturn(existedTemplate);
        when(mappingService.map(existedTemplate, InterviewTemplateDto.class)).thenReturn(expectedInterviewTemplate);

        final InterviewTemplateDto interviewTemplateDto = interviewTemplateService.deleteCriteria("1", "6");

        assertThat(interviewTemplateDto).isEqualTo(expectedInterviewTemplate);
    }

    @Test
    void shouldNotDeleteCriteriaInterviewTemplateDoesNotExistException() {
        assertThatThrownBy(() -> interviewTemplateService.deleteCriteria("1", "6"))
                .isInstanceOf(InterviewTemplateDoesNotExistException.class);
    }

    @Test
    void shouldNotDeleteCriteriaInterviewTemplateCriteriaDoesNotExist() {
        when(interviewTemplateRepository.existsById("1")).thenReturn(true);

        assertThatThrownBy(() -> interviewTemplateService.deleteCriteria("1", "6"))
                .isInstanceOf(InterviewTemplateCriteriaDoesNotExist.class);
    }

    @Test
    void existByCriteriaId() {
        interviewTemplateService.existByCriteriaId("1");

        verify(interviewTemplateRepository).existByCriteriaId("1");
    }

    @Test
    void findAllByCriteria_Id() {
        when(interviewTemplateRepository.findAllByCriteriaId("1")).thenReturn(getTemplates());
        when(mappingService.mapAsList(getTemplates(), InterviewTemplateDto.class)).thenReturn(getTemplatesDto());

        final List<InterviewTemplateDto> result = interviewTemplateService.findAllByCriteriaId("1");

        assertThat(result).isEqualTo(getTemplatesDto());
    }

    @Test
    void saveAll() {
        when(mappingService.mapAsList(getTemplatesDto(), InterviewTemplate.class)).thenReturn(getTemplates());
        when(interviewTemplateRepository.saveAll(getTemplates())).thenReturn(getTemplates());
        when(mappingService.mapAsList(getTemplates(), InterviewTemplateDto.class)).thenReturn(getTemplatesDto());

        final List<InterviewTemplateDto> result = interviewTemplateService.saveAll(getTemplatesDto());

        assertThat(result).isEqualTo(getTemplatesDto());
    }
}