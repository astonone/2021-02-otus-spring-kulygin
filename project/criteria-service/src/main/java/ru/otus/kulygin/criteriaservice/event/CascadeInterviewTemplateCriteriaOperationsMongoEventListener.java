package ru.otus.kulygin.criteriaservice.event;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.criteriaservice.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.criteriaservice.service.InterviewService;
import ru.otus.kulygin.criteriaservice.service.TemplateService;
import ru.otus.kulygin.criteriaservice.vo.InterviewTemplateVO;
import ru.otus.kulygin.criteriaservice.vo.InterviewVO;

import java.util.Collection;

@Component
@AllArgsConstructor
public class CascadeInterviewTemplateCriteriaOperationsMongoEventListener extends AbstractMongoEventListener<InterviewTemplateCriteria> {

    private final TemplateService templateService;
    private final InterviewService interviewService;


    @Override
    public void onBeforeDelete(BeforeDeleteEvent<InterviewTemplateCriteria> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (templateService.existsTemplateByCriteriaId(id)) {
            throw new RuntimeException("Criteria has related templates");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<InterviewTemplateCriteria> event) {
        val criteria = event.getSource();
        try {
            val interviewTemplates = templateService.findAllByCriteriaId(criteria.getId());
            interviewTemplates.stream()
                    .map(InterviewTemplateVO::getCriterias)
                    .flatMap(Collection::stream)
                    .filter(cr -> cr.getId().equals(criteria.getId()))
                    .forEach(cr -> {
                        cr.setName(criteria.getName());
                        cr.setPositionType(criteria.getPositionType());
                    });
            if (!interviewTemplates.isEmpty()) {
                templateService.saveAll(interviewTemplates);
            }
        } catch (Exception e) {
            System.out.println("Cascade operation was not finish");
        }

        try {
            val interviews = interviewService.findAllPlannedByCriteriaId(criteria.getId());
            interviews.stream()
                    .map(InterviewVO::getInterviewTemplate)
                    .map(InterviewTemplateVO::getCriterias)
                    .flatMap(Collection::stream)
                    .filter(cr -> cr.getId().equals(criteria.getId()))
                    .forEach(cr -> {
                        cr.setName(criteria.getName());
                        cr.setPositionType(criteria.getPositionType());
                    });
            if (!interviews.isEmpty()) {
                interviewService.saveAll(interviews);
            }
        } catch (Exception e) {
            System.out.println("Cascade operation was not finish");
        }

    }
}
