package ru.otus.kulygin.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.repository.InterviewerRepository;

import java.util.Arrays;

@ChangeLog(order = "001")
public class Changelog001 {

    @ChangeSet(order = "001", id = "2021-09-04--001-insert-interviewers--vkulygin", author = "viktor.kulygin")
    public void insertInterviewers(InterviewerRepository interviewerRepository) {

        Interviewer interviewer = Interviewer.builder()
                .firstName("Виктор")
                .lastName("Кулыгин")
                .build();

        Interviewer interviewer2 = Interviewer.builder()
                .firstName("Андрей")
                .lastName("Шмоськин")
                .build();

        Interviewer interviewer3 = Interviewer.builder()
                .firstName("Ирина")
                .lastName("Медошкина")
                .build();

        interviewerRepository.saveAll(Arrays.asList(interviewer, interviewer2, interviewer3));
    }

}
