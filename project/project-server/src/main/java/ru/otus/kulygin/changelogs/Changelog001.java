package ru.otus.kulygin.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import lombok.val;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.repository.InterviewTemplateCriteriaRepository;
import ru.otus.kulygin.repository.InterviewerRepository;

import java.util.Arrays;

@ChangeLog(order = "001")
public class Changelog001 {

    @ChangeSet(order = "001", id = "2021-09-04--001-insert-interviewers--vkulygin", author = "viktor.kulygin")
    public void insertInterviewers(InterviewerRepository interviewerRepository) {

        val interviewer = Interviewer.builder()
                .firstName("Виктор")
                .lastName("Кулыгин")
                .positionType("Java Developer")
                .build();

        val interviewer2 = Interviewer.builder()
                .firstName("Андрей")
                .lastName("Шмоськин")
                .positionType("Java Developer")
                .build();

        val interviewer3 = Interviewer.builder()
                .firstName("Ирина")
                .lastName("Медошкина")
                .positionType("Java Developer")
                .build();

        interviewerRepository.saveAll(Arrays.asList(interviewer, interviewer2, interviewer3));
    }

    @ChangeSet(order = "002", id = "2021-26-04--001-insert-criterias--vkulygin", author = "viktor.kulygin")
    public void insertCriterias(InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository) {
        val criteria1 = InterviewTemplateCriteria.builder()
                .name("Знание основных типов данных, переменных, операторов, циклов, массивов")
                .positionType("Java Junior Developer")
                .build();

        val criteria2 = InterviewTemplateCriteria.builder()
                .name("Назовите принципы ООП и расскажите о каждом")
                .positionType("Java Junior Developer")
                .build();

        val criteria3 = InterviewTemplateCriteria.builder()
                .name("Какие модификации уровня доступа вы знаете, расскажите про каждый из них")
                .positionType("Java Junior Developer")
                .build();

        val criteria4 = InterviewTemplateCriteria.builder()
                .name("Что такое сигнатура метода?")
                .positionType("Java Junior Developer")
                .build();

        val criteria5 = InterviewTemplateCriteria.builder()
                .name("Какие методы называются перегруженными?")
                .positionType("Java Junior Developer")
                .build();

        val criteria6 = InterviewTemplateCriteria.builder()
                .name("Могут ли нестатические методы перегрузить статические?")
                .positionType("Java Junior Developer")
                .build();

        val criteria7 = InterviewTemplateCriteria.builder()
                .name("Расскажите про переопределение методов.")
                .positionType("Java Junior Developer")
                .build();

        val criteria8 = InterviewTemplateCriteria.builder()
                .name("Может ли метод принимать разное количество параметров (аргументы переменной длины)?")
                .positionType("Java Junior Developer")
                .build();

        val criteria9 = InterviewTemplateCriteria.builder()
                .name("Какие преобразования называются нисходящими и восходящими?")
                .positionType("Java Junior Developer")
                .build();

        val criteria10 = InterviewTemplateCriteria.builder()
                .name("Чем отличается переопределение от перегрузки?")
                .positionType("Java Junior Developer")
                .build();

        val criteria11 = InterviewTemplateCriteria.builder()
                .name("Зачем нужны и какие бывают блоки инициализации?")
                .positionType("Java Junior Developer")
                .build();

        val criteria12 = InterviewTemplateCriteria.builder()
                .name("Где и для чего используется модификатор abstract?")
                .positionType("Java Junior Developer")
                .build();

        val criteria13 = InterviewTemplateCriteria.builder()
                .name("О чем говорит ключевое слово final?")
                .positionType("Java Junior Developer")
                .build();

        val criteria14 = InterviewTemplateCriteria.builder()
                .name("Можно ли объявить метод абстрактным и статическим одновременно?")
                .positionType("Java Junior Developer")
                .build();

        val criteria15 = InterviewTemplateCriteria.builder()
                .name("Дайте определение понятию “интерфейс”.")
                .positionType("Java Junior Developer")
                .build();

        val criteria16 = InterviewTemplateCriteria.builder()
                .name("Как связан любой пользовательский класс с классом Object?")
                .positionType("Java Junior Developer")
                .build();

        val criteria17 = InterviewTemplateCriteria.builder()
                .name("Расскажите про каждый из методов класса Object.")
                .positionType("Java Junior Developer")
                .build();

        val criteria18 = InterviewTemplateCriteria.builder()
                .name("Что такое метод equals(). Чем он отличается от операции ==.")
                .positionType("Java Junior Developer")
                .build();

        val criteria19 = InterviewTemplateCriteria.builder()
                .name("В чем особенность работы методов hashCode и equals? Каким образом реализованы методы hashCode и equals в классе Object? Какие правила и соглашения существуют для реализации этих методов? Когда они применяются?")
                .positionType("Java Junior Developer")
                .build();

        val criteria20 = InterviewTemplateCriteria.builder()
                .name("Чем отличается абстрактный класс от интерфейса, в каких случаях что вы будете использовать?")
                .positionType("Java Junior Developer")
                .build();

        val criteria21 = InterviewTemplateCriteria.builder()
                .name("Можно ли получить доступ к private переменным класса и если да, то каким образом?")
                .positionType("Java Junior Developer")
                .build();

        val criteria22 = InterviewTemplateCriteria.builder()
                .name("Что такое volatile и transient? Для чего и в каких случаях можно было бы использовать default?")
                .positionType("Java Junior Developer")
                .build();

        val criteria23 = InterviewTemplateCriteria.builder()
                .name("Расширение модификаторов при наследовании, переопределении и сокрытии методов. Если у класса-родителя есть метод, объявленный как private, может ли наследник расширить его видимость? А если protected? А сузить видимость?")
                .positionType("Java Junior Developer")
                .build();

        val criteria24 = InterviewTemplateCriteria.builder()
                .name("Что такое finalize? Зачем он нужен? Что Вы можете рассказать о сборщике мусора и алгоритмах его работы.")
                .positionType("Java Junior Developer")
                .build();

        val criteria25 = InterviewTemplateCriteria.builder()
                .name("Имеет ли смысл объявлять метод private final?")
                .positionType("Java Junior Developer")
                .build();

        interviewTemplateCriteriaRepository.saveAll(Arrays.asList(criteria1, criteria2, criteria3, criteria4, criteria5,
                criteria6, criteria7, criteria8, criteria9, criteria10, criteria11, criteria12, criteria13, criteria14,
                criteria15, criteria16, criteria17, criteria18, criteria19, criteria20, criteria21, criteria22,
                criteria23, criteria24, criteria25));
    }

}
