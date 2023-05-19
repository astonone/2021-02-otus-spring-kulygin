package ru.otus.kulygin.candidateservice.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import lombok.val;
import ru.otus.kulygin.candidateservice.domain.Candidate;
import ru.otus.kulygin.candidateservice.repository.CandidateRepository;

import java.util.Arrays;

@ChangeLog(order = "001")
public class Changelog001 {

    @ChangeSet(order = "001", id = "2021-26-04--001-insert-candidates--vkulygin", author = "viktor.kulygin")
    public void insertCandidates(CandidateRepository candidateRepository) {
        val candidate = Candidate.builder()
                .id("1")
                .firstName("Виктор")
                .lastName("Кулыгин")
                .claimingPosition("Java Software Architect")
                .build();

        val candidate2 = Candidate.builder()
                .id("2")
                .firstName("Ирина")
                .lastName("Кулыгина")
                .claimingPosition("Java Developer")
                .interviewerComment("Должен быть толковый джун")
                .build();

        val candidate3 = Candidate.builder()
                .id("3")
                .firstName("Андрей")
                .lastName("Оськин")
                .claimingPosition("Java Senior Developer")
                .build();

        candidateRepository.saveAll(Arrays.asList(candidate, candidate2, candidate3));
    }

}
