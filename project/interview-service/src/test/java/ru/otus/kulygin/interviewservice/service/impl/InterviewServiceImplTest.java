package ru.otus.kulygin.interviewservice.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.kulygin.interviewservice.BaseTest;
import ru.otus.kulygin.interviewservice.domain.Candidate;
import ru.otus.kulygin.interviewservice.domain.Interview;
import ru.otus.kulygin.interviewservice.domain.InterviewTemplate;
import ru.otus.kulygin.interviewservice.domain.Interviewer;
import ru.otus.kulygin.interviewservice.dto.InterviewDto;
import ru.otus.kulygin.interviewservice.dto.InterviewTemplateCriteriaDto;
import ru.otus.kulygin.interviewservice.dto.InterviewTemplateDto;
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
import ru.otus.kulygin.interviewservice.vo.CandidateVO;
import ru.otus.kulygin.interviewservice.vo.InterviewTemplateVO;
import ru.otus.kulygin.interviewservice.vo.InterviewerVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = InterviewServiceImpl.class)
class InterviewServiceImplTest extends BaseTest {

    @Autowired
    private InterviewService interviewService;

    @MockBean
    private InterviewRepository interviewRepository;

    @MockBean
    private CandidateService candidateService;

    @MockBean
    private InterviewerService interviewerService;

    @MockBean
    private TemplateService templateService;

    @MockBean
    private MappingService mappingService;

    @Test
    void shouldFindAllPageable() {
        final Page<Interview> interviewPage = new PageImpl<>(getInterviews(), getPageRequestP0PS10(), 10);
        when(interviewRepository.findAll(getPageRequestP0PS10())).thenReturn(interviewPage);
        when(mappingService.mapAsList(interviewPage.getContent(), InterviewDto.class)).thenReturn(getInterviewsDto());

        final InterviewPageableDto result = interviewService.findAll(getPageRequestP0PS10());

        assertThat(result).isNotNull();
        assertThat(result.getPage()).isEqualTo(interviewPage.getPageable().getPageNumber());
        assertThat(result.getPageSize()).isEqualTo(interviewPage.getPageable().getPageSize());
        assertThat(result.getCurrentPageSize()).isEqualTo(interviewPage.getContent().size());
        assertThat(result.getTotalSize()).isEqualTo(interviewPage.getTotalElements());
        assertThat(result.getTotalPageSize()).isEqualTo(interviewPage.getTotalPages());
        assertThat(result.getInterviews()).isEqualTo(getInterviewsDto());
    }

    @Test
    void shouldFindAll() {
        when(interviewRepository.findAll()).thenReturn(getInterviews());
        when(mappingService.mapAsList(getInterviews(), InterviewDto.class)).thenReturn(getInterviewsDto());

        final InterviewPageableDto result = interviewService.findAll();

        assertThat(result).isNotNull();
        assertThat(result.getInterviews()).isEqualTo(getInterviewsDto());
    }

    @Test
    void shouldFindAllByInterviewStatus() {
        final Page<Interview> interviewPage = new PageImpl<>(getInterviews(), getPageRequestP0PS10(), 10);
        when(interviewRepository.findAllByInterviewStatus(InterviewStatus.PLANNED, getPageRequestP0PS10())).thenReturn(interviewPage);
        when(mappingService.mapAsList(interviewPage.getContent(), InterviewDto.class)).thenReturn(getInterviewsDto());

        final InterviewPageableDto result = interviewService.findAllByInterviewStatus(getPageRequestP0PS10(),
                InterviewStatus.PLANNED.getCode());

        assertThat(result).isNotNull();
        assertThat(result.getPage()).isEqualTo(interviewPage.getPageable().getPageNumber());
        assertThat(result.getPageSize()).isEqualTo(interviewPage.getPageable().getPageSize());
        assertThat(result.getCurrentPageSize()).isEqualTo(interviewPage.getContent().size());
        assertThat(result.getTotalSize()).isEqualTo(interviewPage.getTotalElements());
        assertThat(result.getTotalPageSize()).isEqualTo(interviewPage.getTotalPages());
        assertThat(result.getInterviews()).isEqualTo(getInterviewsDto());
    }

    @Test
    void shouldSaveCreateMode() {
        final InterviewDto interviewDto = getInterviewsDto().get(0);
        interviewDto.setId(null);
        when(candidateService.getCandidateById(interviewDto.getCandidate().getId())).thenReturn(CandidateVO.builder().build());
        when(mappingService.map(CandidateVO.builder().build(), Candidate.class)).thenReturn(getInterviews().get(0).getCandidate());
        when(interviewerService.getInterviewerById(interviewDto.getInterviewer().getId())).thenReturn(InterviewerVO.builder().build());
        when(mappingService.map(InterviewerVO.builder().build(), Interviewer.class)).thenReturn(getInterviews().get(0).getInterviewer());
        when(templateService.getInterviewTemplateById(interviewDto.getInterviewTemplate().getId())).thenReturn(InterviewTemplateVO.builder().build());
        when(mappingService.map(InterviewTemplateVO.builder().build(), InterviewTemplate.class)).thenReturn(getInterviews().get(0).getInterviewTemplate());
        final Interview interviewForSave = getInterviews().get(0);
        interviewForSave.setId(null);
        when(interviewRepository.save(interviewForSave)).thenReturn(interviewForSave);
        when(mappingService.map(interviewForSave, InterviewDto.class)).thenReturn(getInterviewsDto().get(0));

        final InterviewDto result = interviewService.save(interviewDto);

        assertThat(result).isEqualTo(getInterviewsDto().get(0));
    }

    @Test
    void shouldSaveUpdateMode() {
        final InterviewDto interviewDto = getInterviewsDto().get(0);
        interviewDto.setDesiredSalary("6 money");
        when(interviewRepository.findById(interviewDto.getId())).thenReturn(Optional.of(getInterviews().get(0)));
        when(candidateService.getCandidateById(interviewDto.getCandidate().getId())).thenReturn(CandidateVO.builder().build());
        when(mappingService.map(CandidateVO.builder().build(), Candidate.class)).thenReturn(getInterviews().get(0).getCandidate());
        when(interviewerService.getInterviewerById(interviewDto.getInterviewer().getId())).thenReturn(InterviewerVO.builder().build());
        when(mappingService.map(InterviewerVO.builder().build(), Interviewer.class)).thenReturn(getInterviews().get(0).getInterviewer());
        when(templateService.getInterviewTemplateById(interviewDto.getInterviewTemplate().getId())).thenReturn(InterviewTemplateVO.builder().build());
        when(mappingService.map(InterviewTemplateVO.builder().build(), InterviewTemplate.class)).thenReturn(getInterviews().get(0).getInterviewTemplate());
        final Interview interviewForSave = getInterviews().get(0);
        interviewForSave.setDesiredSalary("6 money");
        when(interviewRepository.save(interviewForSave)).thenReturn(interviewForSave);
        final InterviewDto expectedInterviewDto = getInterviewsDto().get(0);
        expectedInterviewDto.setDesiredSalary("6 money");
        when(mappingService.map(interviewForSave, InterviewDto.class)).thenReturn(expectedInterviewDto);

        final InterviewDto result = interviewService.save(interviewDto);

        assertThat(result).isEqualTo(expectedInterviewDto);
    }

    @Test
    void shouldNotSaveUpdateModeInterviewDoesNotExistException() {
        final InterviewDto interviewDto = getInterviewsDto().get(0);

        assertThatThrownBy(() -> interviewService.save(interviewDto))
                .isInstanceOf(InterviewDoesNotExistException.class);
    }

    @Test
    void shouldNotSaveCreateModeInterviewStatusException() {
        final InterviewDto interviewDto = getInterviewsDto().get(0);
        interviewDto.setId(null);
        interviewDto.setInterviewStatus(InterviewStatus.IN_PROGRESS.getCode());
        when(candidateService.getCandidateById(interviewDto.getCandidate().getId())).thenReturn(CandidateVO.builder().build());
        when(mappingService.map(CandidateVO.builder().build(), Candidate.class)).thenReturn(getInterviews().get(0).getCandidate());
        when(interviewerService.getInterviewerById(interviewDto.getInterviewer().getId())).thenReturn(InterviewerVO.builder().build());
        when(mappingService.map(InterviewerVO.builder().build(), Interviewer.class)).thenReturn(getInterviews().get(0).getInterviewer());
        when(templateService.getInterviewTemplateById(interviewDto.getInterviewTemplate().getId())).thenReturn(InterviewTemplateVO.builder().build());
        when(mappingService.map(InterviewTemplateVO.builder().build(), InterviewTemplate.class)).thenReturn(getInterviews().get(0).getInterviewTemplate());

        assertThatThrownBy(() -> interviewService.save(interviewDto))
                .isInstanceOf(InterviewStatusException.class);
    }

    @Test
    void shouldNotSaveCreateModeInterviewDecisionException() {
        final InterviewDto interviewDto = getInterviewsDto().get(0);
        interviewDto.setId(null);
        interviewDto.setDecisionStatus(DecisionStatus.SHOULD_BE_HIRED.getCode());
        when(candidateService.getCandidateById(interviewDto.getCandidate().getId())).thenReturn(CandidateVO.builder().build());
        when(mappingService.map(CandidateVO.builder().build(), Candidate.class)).thenReturn(getInterviews().get(0).getCandidate());
        when(interviewerService.getInterviewerById(interviewDto.getInterviewer().getId())).thenReturn(InterviewerVO.builder().build());
        when(mappingService.map(InterviewerVO.builder().build(), Interviewer.class)).thenReturn(getInterviews().get(0).getInterviewer());
        when(templateService.getInterviewTemplateById(interviewDto.getInterviewTemplate().getId())).thenReturn(InterviewTemplateVO.builder().build());
        when(mappingService.map(InterviewTemplateVO.builder().build(), InterviewTemplate.class)).thenReturn(getInterviews().get(0).getInterviewTemplate());

        assertThatThrownBy(() -> interviewService.save(interviewDto))
                .isInstanceOf(InterviewDecisionException.class);
    }

    @Test
    void shouldDeleteById() {
        when(interviewRepository.existsById("1")).thenReturn(true);
        interviewService.deleteById("1");

        verify(interviewRepository).existsById("1");
        verify(interviewRepository).deleteById("1");
    }

    @Test
    void shouldGetById() {
        when(interviewRepository.findById("1")).thenReturn(Optional.of(getInterviews().get(0)));
        when(mappingService.map(getInterviews().get(0), InterviewDto.class)).thenReturn(getInterviewsDto().get(0));

        final InterviewDto result = interviewService.getById("1");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(getInterviewsDto().get(0));
    }

    @Test
    void shouldNotGetByIdInterviewDoesNotExistException() {
        when(interviewRepository.findById("1")).thenThrow(InterviewDoesNotExistException.class);

        assertThatThrownBy(() -> interviewService.getById("1"))
                .isInstanceOf(InterviewDoesNotExistException.class);
    }

    @Test
    void shouldUpdateCriteria() {
        when(interviewRepository.findById("1")).thenReturn(Optional.of(getInterviews().get(0)));
        val templateForSave = InterviewTemplate.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(new ArrayList<>(getInterviews().get(0).getInterviewTemplate().getCriterias()))
                .build();
        templateForSave.getCriterias().get(0).setMark(5);
        val interviewForSave = Interview.builder()
                .id("1")
                .interviewer(getInterviews().get(0).getInterviewer())
                .candidate(getInterviews().get(0).getCandidate())
                .interviewTemplate(templateForSave)
                .desiredSalary("2000 EUR")
                .interviewDateTime(LocalDateTime.of(2021, 9, 20, 15, 30))
                .interviewStatus(InterviewStatus.PLANNED)
                .totalMark(0.2)
                .decisionStatus(DecisionStatus.NOT_APPLICABLE)
                .build();
        when(interviewRepository.save(interviewForSave)).thenReturn(interviewForSave);
        val expectedTemplate = InterviewTemplateDto.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(new ArrayList<>(getInterviewsDto().get(0).getInterviewTemplate().getCriterias()))
                .build();
        expectedTemplate.getCriterias().get(0).setMark(5);
        val expectedInterview = InterviewDto.builder()
                .id("1")
                .interviewer(getInterviewsDto().get(0).getInterviewer())
                .candidate(getInterviewsDto().get(0).getCandidate())
                .interviewTemplate(expectedTemplate)
                .desiredSalary("2000 EUR")
                .interviewDateTime(LocalDateTime.of(2021, 9, 20, 15, 30))
                .interviewStatus(InterviewStatus.PLANNED.getCode())
                .totalMark(0.2)
                .decisionStatus(DecisionStatus.NOT_APPLICABLE.getCode())
                .build();
        when(mappingService.map(interviewForSave, InterviewDto.class)).thenReturn(expectedInterview);

        final InterviewDto result = interviewService.updateCriteria("1", "1", 5);

        assertThat(result).isEqualTo(expectedInterview);
    }

    @Test
    void shouldNotUpdateCriteriaInterviewDoesNotExistException() {
        assertThatThrownBy(() -> interviewService.updateCriteria("1", "1", 5))
                .isInstanceOf(InterviewDoesNotExistException.class);
    }

    @Test
    void shouldNotUpdateCriteriaInterviewTemplateCriteriaDoesNotExist() {
        when(interviewRepository.findById("1")).thenReturn(Optional.of(getInterviews().get(0)));

        assertThatThrownBy(() -> interviewService.updateCriteria("1", "6", 5))
                .isInstanceOf(InterviewTemplateCriteriaDoesNotExist.class);
    }

    @Test
    void shouldUpdateCriteriaComment() {
        val criteria1 = InterviewTemplateCriteriaDto.builder()
                .id("1")
                .name("Знание основных типов данных, переменных, операторов, циклов, массивов")
                .positionType("Java Developer")
                .interviewerComment("lalala")
                .build();
        when(interviewRepository.findById("1")).thenReturn(Optional.of(getInterviews().get(0)));
        val templateForSave = InterviewTemplate.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(new ArrayList<>(getInterviews().get(0).getInterviewTemplate().getCriterias()))
                .build();
        templateForSave.getCriterias().get(0).setInterviewerComment("lalala");
        val interviewForSave = Interview.builder()
                .id("1")
                .interviewer(getInterviews().get(0).getInterviewer())
                .candidate(getInterviews().get(0).getCandidate())
                .interviewTemplate(templateForSave)
                .desiredSalary("2000 EUR")
                .interviewDateTime(LocalDateTime.of(2021, 9, 20, 15, 30))
                .interviewStatus(InterviewStatus.PLANNED)
                .decisionStatus(DecisionStatus.NOT_APPLICABLE)
                .build();
        when(interviewRepository.save(interviewForSave)).thenReturn(interviewForSave);
        val expectedTemplate = InterviewTemplateDto.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(new ArrayList<>(getInterviewsDto().get(0).getInterviewTemplate().getCriterias()))
                .build();
        expectedTemplate.getCriterias().get(0).setInterviewerComment("lalala");
        val expectedInterview = InterviewDto.builder()
                .id("1")
                .interviewer(getInterviewsDto().get(0).getInterviewer())
                .candidate(getInterviewsDto().get(0).getCandidate())
                .interviewTemplate(expectedTemplate)
                .desiredSalary("2000 EUR")
                .interviewDateTime(LocalDateTime.of(2021, 9, 20, 15, 30))
                .interviewStatus(InterviewStatus.PLANNED.getCode())
                .decisionStatus(DecisionStatus.NOT_APPLICABLE.getCode())
                .build();
        when(mappingService.map(interviewForSave, InterviewDto.class)).thenReturn(expectedInterview);

        final InterviewDto result = interviewService.updateCriteriaComment("1", "1", criteria1);

        assertThat(result).isEqualTo(expectedInterview);
    }

    @Test
    void shouldNotUpdateCriteriaCommentInterviewDoesNotExistException() {
        val criteria1 = InterviewTemplateCriteriaDto.builder()
                .id("1")
                .name("Знание основных типов данных, переменных, операторов, циклов, массивов")
                .positionType("Java Developer")
                .interviewerComment("lalala")
                .build();

        assertThatThrownBy(() -> interviewService.updateCriteriaComment("1", "1", criteria1))
                .isInstanceOf(InterviewDoesNotExistException.class);
    }

    @Test
    void shouldNotUpdateCriteriaCommentInterviewTemplateCriteriaDoesNotExist() {
        val criteria1 = InterviewTemplateCriteriaDto.builder()
                .id("1")
                .name("Знание основных типов данных, переменных, операторов, циклов, массивов")
                .positionType("Java Developer")
                .interviewerComment("lalala")
                .build();
        when(interviewRepository.findById("1")).thenReturn(Optional.of(getInterviews().get(0)));

        assertThatThrownBy(() -> interviewService.updateCriteriaComment("1", "6", criteria1))
                .isInstanceOf(InterviewTemplateCriteriaDoesNotExist.class);
    }

    @Test
    void shouldExistsByCandidateId() {
        interviewService.existsByCandidateId("1");

        verify(interviewRepository).existsByCandidate_Id("1");
    }

    @Test
    void shouldFindAllByCandidateId() {
        when(interviewRepository.findAllByCandidate_Id("1")).thenReturn(getInterviews());
        when(mappingService.mapAsList(getInterviews(), InterviewDto.class)).thenReturn(getInterviewsDto());

        final List<InterviewDto> result = interviewService.findAllByCandidateId("1");

        assertThat(result).isEqualTo(getInterviewsDto());
    }


    @Test
    void shouldSaveAll() {
        when(mappingService.mapAsList(getInterviewsDto(), Interview.class)).thenReturn(getInterviews());
        when(interviewRepository.saveAll(getInterviews())).thenReturn(getInterviews());
        when(mappingService.mapAsList(getInterviews(), InterviewDto.class)).thenReturn(getInterviewsDto());

        final List<InterviewDto> result = interviewService.saveAll(getInterviewsDto());

        assertThat(result).isEqualTo(getInterviewsDto());
    }

    @Test
    void shouldExistsByInterviewerId() {
        interviewService.existsByInterviewerId("1");

        verify(interviewRepository).existsByInterviewer_Id("1");
    }

    @Test
    void shouldFindAllByInterviewerId() {
        when(interviewRepository.findAllByInterviewer_Id("1")).thenReturn(getInterviews());
        when(mappingService.mapAsList(getInterviews(), InterviewDto.class)).thenReturn(getInterviewsDto());

        final List<InterviewDto> result = interviewService.findAllByInterviewerId("1");

        assertThat(result).isEqualTo(getInterviewsDto());
    }

    @Test
    void shouldFindAllPlannedByCriteriaId() {
        when(interviewRepository.findAllByCriteriaId("1")).thenReturn(getInterviews());
        when(mappingService.mapAsList(getInterviews(), InterviewDto.class)).thenReturn(getInterviewsDto());

        final List<InterviewDto> result = interviewService.findAllPlannedByCriteriaId("1");

        assertThat(result).isEqualTo(getInterviewsDto());
    }

    @Test
    void shouldExistsByInterviewTemplateId() {
        interviewService.existsByInterviewTemplateId("1");

        verify(interviewRepository).existsByInterviewTemplate_Id("1");
    }

    @Test
    void shouldFindAllByInterviewTemplateIdAndInterviewStatus() {
        when(interviewRepository.findAllByInterviewTemplate_IdAndInterviewStatus("1", InterviewStatus.PLANNED)).thenReturn(getInterviews());
        when(mappingService.mapAsList(getInterviews(), InterviewDto.class)).thenReturn(getInterviewsDto());

        final List<InterviewDto> result = interviewService.findAllByInterviewTemplateIdAndInterviewStatus("1");

        assertThat(result).isEqualTo(getInterviewsDto());
    }
}