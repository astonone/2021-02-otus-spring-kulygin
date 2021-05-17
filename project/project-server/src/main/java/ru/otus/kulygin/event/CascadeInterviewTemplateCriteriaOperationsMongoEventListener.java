package ru.otus.kulygin.event;

import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Interview;
import ru.otus.kulygin.domain.InterviewTemplate;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.repository.InterviewRepository;
import ru.otus.kulygin.repository.InterviewTemplateRepository;

import java.util.Collection;

@Component
public class CascadeInterviewTemplateCriteriaOperationsMongoEventListener extends AbstractMongoEventListener<InterviewTemplateCriteria> {

    private final InterviewTemplateRepository interviewTemplateRepository;
    private final InterviewRepository interviewRepository;

    public CascadeInterviewTemplateCriteriaOperationsMongoEventListener(InterviewTemplateRepository interviewTemplateRepository, InterviewRepository interviewRepository) {
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.interviewRepository = interviewRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<InterviewTemplateCriteria> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (interviewTemplateRepository.existByCriteriaId(id)) {
            throw new RuntimeException("Criteria has related templates");
        }
        if (interviewRepository.existByCriteriaId(id)) {
            throw new RuntimeException("Criteria has related interviews");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<InterviewTemplateCriteria> event) {
        val criteria = event.getSource();
        val interviewTemplates = interviewTemplateRepository.findAllByCriteriaId(criteria.getId());
        interviewTemplates.stream()
                .map(InterviewTemplate::getCriterias)
                .flatMap(Collection::stream)
                .filter(cr -> cr.getId().equals(criteria.getId()))
                .forEach(cr -> {
                    cr.setName(criteria.getName());
                    cr.setPositionType(criteria.getPositionType());
                });
        if (!interviewTemplates.isEmpty()) {
            interviewTemplateRepository.saveAll(interviewTemplates);
        }
        val interviews = interviewRepository.findAllByCriteriaId(criteria.getId());
        interviews.stream()
                .map(Interview::getInterviewTemplate)
                .map(InterviewTemplate::getCriterias)
                .flatMap(Collection::stream)
                .filter(cr -> cr.getId().equals(criteria.getId()))
                .forEach(cr -> {
                    cr.setName(criteria.getName());
                    cr.setPositionType(criteria.getPositionType());
                });
        if (!interviews.isEmpty()) {
            interviewRepository.saveAll(interviews);
        }
    }

}
