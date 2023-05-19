package ru.otus.kulygin.interviewservice.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import lombok.val;
import ru.otus.kulygin.interviewservice.domain.*;
import ru.otus.kulygin.interviewservice.enumerations.DecisionStatus;
import ru.otus.kulygin.interviewservice.enumerations.InterviewStatus;
import ru.otus.kulygin.interviewservice.repository.InterviewRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@ChangeLog(order = "001")
public class Changelog001 {

    @ChangeSet(order = "001", id = "2021-30-07--001-insert-interview--vkulygin", author = "viktor.kulygin")
    public void insertInterview(InterviewRepository interviewRepository) {

        val interviewer = Interviewer.builder()
                .id("1")
                .firstName("Джон")
                .lastName("Петрович")
                .username("johnyp")
                .password("$2y$10$VMLDDjrQJX7vuX7t0wcCNutiLcz33cUHk8gp5yEnMfn6WVvEaHeci")
                .role("ROLE_DEVELOPER")
                .positionType("Java Developer")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        val candidate = Candidate.builder()
                .id("2")
                .firstName("Ирина")
                .lastName("Кулыгина")
                .claimingPosition("Java Developer")
                .interviewerComment("Должен быть толковый джун")
                .build();

        val criteria1 = InterviewTemplateCriteria.builder()
                .id("1")
                .name("Знание основных типов данных, переменных, операторов, циклов, массивов")
                .positionType("Java Developer")
                .build();

        val criteria2 = InterviewTemplateCriteria.builder()
                .id("2")
                .name("Назовите принципы ООП и расскажите о каждом")
                .positionType("Java Developer")
                .build();

        val criteria3 = InterviewTemplateCriteria.builder()
                .name("Какие модификации уровня доступа вы знаете, расскажите про каждый из них")
                .id("3")
                .positionType("Java Developer")
                .build();

        val criteria4 = InterviewTemplateCriteria.builder()
                .id("4")
                .name("Что такое сигнатура метода?")
                .positionType("Java Developer")
                .build();

        val criteria5 = InterviewTemplateCriteria.builder()
                .id("5")
                .name("Какие методы называются перегруженными?")
                .positionType("Java Developer")
                .build();

        val criteria6 = InterviewTemplateCriteria.builder()
                .id("6")
                .name("Могут ли нестатические методы перегрузить статические?")
                .positionType("Java Developer")
                .build();

        val criteria7 = InterviewTemplateCriteria.builder()
                .id("7")
                .name("Расскажите про переопределение методов.")
                .positionType("Java Developer")
                .build();

        val criteria8 = InterviewTemplateCriteria.builder()
                .id("8")
                .name("Может ли метод принимать разное количество параметров (аргументы переменной длины)?")
                .positionType("Java Developer")
                .build();

        val criteria9 = InterviewTemplateCriteria.builder()
                .id("9")
                .name("Какие преобразования называются нисходящими и восходящими?")
                .positionType("Java Developer")
                .build();

        val criteria10 = InterviewTemplateCriteria.builder()
                .id("10")
                .name("Чем отличается переопределение от перегрузки?")
                .positionType("Java Developer")
                .build();

        val criteria11 = InterviewTemplateCriteria.builder()
                .id("11")
                .name("Зачем нужны и какие бывают блоки инициализации?")
                .positionType("Java Developer")
                .build();

        val criteria12 = InterviewTemplateCriteria.builder()
                .id("12")
                .name("Где и для чего используется модификатор abstract?")
                .positionType("Java Developer")
                .build();

        val criteria13 = InterviewTemplateCriteria.builder()
                .id("13")
                .name("О чем говорит ключевое слово final?")
                .positionType("Java Developer")
                .build();

        val criteria14 = InterviewTemplateCriteria.builder()
                .id("14")
                .name("Можно ли объявить метод абстрактным и статическим одновременно?")
                .positionType("Java Developer")
                .build();

        val criteria15 = InterviewTemplateCriteria.builder()
                .id("15")
                .name("Дайте определение понятию “интерфейс”.")
                .positionType("Java Developer")
                .build();

        val criteria16 = InterviewTemplateCriteria.builder()
                .id("16")
                .name("Как связан любой пользовательский класс с классом Object?")
                .positionType("Java Developer")
                .build();

        val criteria17 = InterviewTemplateCriteria.builder()
                .id("17")
                .name("Расскажите про каждый из методов класса Object.")
                .positionType("Java Developer")
                .build();

        val criteria18 = InterviewTemplateCriteria.builder()
                .id("18")
                .name("Что такое метод equals(). Чем он отличается от операции ==.")
                .positionType("Java Developer")
                .build();

        val criteria19 = InterviewTemplateCriteria.builder()
                .id("19")
                .name("В чем особенность работы методов hashCode и equals? Каким образом реализованы методы hashCode и equals в классе Object? Какие правила и соглашения существуют для реализации этих методов? Когда они применяются?")
                .positionType("Java Developer")
                .build();

        val criteria20 = InterviewTemplateCriteria.builder()
                .id("20")
                .name("Чем отличается абстрактный класс от интерфейса, в каких случаях что вы будете использовать?")
                .positionType("Java Developer")
                .build();

        val criteria21 = InterviewTemplateCriteria.builder()
                .id("21")
                .name("Можно ли получить доступ к private переменным класса и если да, то каким образом?")
                .positionType("Java Developer")
                .build();

        val criteria22 = InterviewTemplateCriteria.builder()
                .id("22")
                .name("Что такое volatile и transient? Для чего и в каких случаях можно было бы использовать default?")
                .positionType("Java Developer")
                .build();

        val criteria23 = InterviewTemplateCriteria.builder()
                .id("23")
                .name("Расширение модификаторов при наследовании, переопределении и сокрытии методов. Если у класса-родителя есть метод, объявленный как private, может ли наследник расширить его видимость? А если protected? А сузить видимость?")
                .positionType("Java Developer")
                .build();

        val criteria24 = InterviewTemplateCriteria.builder()
                .id("24")
                .name("Что такое finalize? Зачем он нужен? Что Вы можете рассказать о сборщике мусора и алгоритмах его работы.")
                .positionType("Java Developer")
                .build();

        val criteria25 = InterviewTemplateCriteria.builder()
                .id("25")
                .name("Имеет ли смысл объявлять метод private final?")
                .positionType("Java Developer")
                .build();

        val criteria26 = InterviewTemplateCriteria.builder()
                .id("26")
                .name("Дайте определение понятию “исключение”")
                .positionType("Java Developer")
                .build();

        val criteria27 = InterviewTemplateCriteria.builder()
                .id("27")
                .name("Какова иерархия исключений.")
                .positionType("Java Developer")
                .build();

        val criteria28 = InterviewTemplateCriteria.builder()
                .id("28")
                .name("Можно/нужно ли обрабатывать ошибки jvm?")
                .positionType("Java Developer")
                .build();

        val criteria29 = InterviewTemplateCriteria.builder()
                .id("29")
                .name("Какие существуют способы обработки исключений?")
                .positionType("Java Developer")
                .build();

        val criteria30 = InterviewTemplateCriteria.builder()
                .id("30")
                .name("В чем особенность блока finally? Всегда ли он исполняется?")
                .positionType("Java Developer")
                .build();

        val criteria31 = InterviewTemplateCriteria.builder()
                .id("31")
                .name("Может ли не быть ни одного блока catch при отлавливании исключений?")
                .positionType("Java Developer")
                .build();

        val criteria32 = InterviewTemplateCriteria.builder()
                .id("32")
                .name("Могли бы вы придумать ситуацию, когда блок finally не будет выполнен?")
                .positionType("Java Developer")
                .build();

        val criteria33 = InterviewTemplateCriteria.builder()
                .id("33")
                .name("Может ли один блок catch отлавливать несколько исключений (с одной и разных веток наследований)")
                .positionType("Java Developer")
                .build();

        val criteria34 = InterviewTemplateCriteria.builder()
                .id("34")
                .name("Что вы знаете об обрабатываемых и не обрабатываемых (checked/unchecked) исключениях?")
                .positionType("Java Developer")
                .build();

        val criteria35 = InterviewTemplateCriteria.builder()
                .id("35")
                .name("В чем особенность RuntimeException?")
                .positionType("Java Developer")
                .build();

        val criteria36 = InterviewTemplateCriteria.builder()
                .id("36")
                .name("Какой оператор позволяет принудительно выбросить исключение?")
                .positionType("Java Developer")
                .build();

        val criteria37 = InterviewTemplateCriteria.builder()
                .id("37")
                .name("Если оператор return содержится и в блоке catch и в finally, какой из них “главнее”?")
                .positionType("Java Developer")
                .build();

        val criteria38 = InterviewTemplateCriteria.builder()
                .id("38")
                .name("Предположим, есть блок try-finally. В блоке try возникло исключение и выполнение переместилось в блок finally. В блоке finally тоже возникло исключение. Какое из двух исключений “выпадет” из блока try-finally? Что случится со вторым исключением?")
                .positionType("Java Developer")
                .build();

        val criteria39 = InterviewTemplateCriteria.builder()
                .id("39")
                .name("Предположим, есть метод, который может выбросить IOException и FileNotFoundException в какой последовательности должны идти блоки catch? Сколько блоков catch будет выполнено?")
                .positionType("Java Developer")
                .build();

        val criteria40 = InterviewTemplateCriteria.builder()
                .id("40")
                .name("Назовите преимущества использования коллекций.")
                .positionType("Java Developer")
                .build();

        val criteria41 = InterviewTemplateCriteria.builder()
                .id("41")
                .name("Какова иерархия коллекций?")
                .positionType("Java Developer")
                .build();

        val criteria42 = InterviewTemplateCriteria.builder()
                .id("42")
                .name("Что вы знаете о коллекциях типа List?Что вы знаете о коллекциях типа Set?")
                .positionType("Java Developer")
                .build();

        val criteria43 = InterviewTemplateCriteria.builder()
                .id("43")
                .name("Что вы знаете о коллекциях типа Map, в чем их принципиальное отличие?")
                .positionType("Java Developer")
                .build();

        val criteria44 = InterviewTemplateCriteria.builder()
                .id("44")
                .name("Что разного/общего у классов ArrayList и LinkedList, когда лучше использовать ArrayList, а когда LinkedList?")
                .positionType("Java Developer")
                .build();

        val criteria45 = InterviewTemplateCriteria.builder()
                .id("45")
                .name("Что будет, если в Map положить два значения с одинаковым ключом?")
                .positionType("Java Developer")
                .build();

        val criteria46 = InterviewTemplateCriteria.builder()
                .id("46")
                .name("Какие “строковые” классы вы знаете?Какие основные свойства “строковых” классов (их особенности)?Можно ли наследовать строковый тип, почему?")
                .positionType("Java Developer")
                .build();

        val criteria47 = InterviewTemplateCriteria.builder()
                .id("47")
                .name("Дайте определение понятию “пул строк”.")
                .positionType("Java Developer")
                .build();

        val criteria48 = InterviewTemplateCriteria.builder()
                .id("48")
                .name("Что делает метод intern()?")
                .positionType("Java Developer")
                .build();

        val criteria49 = InterviewTemplateCriteria.builder()
                .id("49")
                .name("Чем отличаются и что общего у классов String, StringBuffer и StringBuilder?")
                .positionType("Java Developer")
                .build();

        val criteria50 = InterviewTemplateCriteria.builder()
                .id("50")
                .name("Напишите метод удаления данного символа из строки.")
                .positionType("Java Developer")
                .build();

        val criteria51 = InterviewTemplateCriteria.builder()
                .id("51")
                .name("Дайте определение понятию “процесс”.")
                .positionType("Java Developer")
                .build();

        val criteria52 = InterviewTemplateCriteria.builder()
                .id("52")
                .name("Дайте определение понятию “поток”.")
                .positionType("Java Developer")
                .build();

        val criteria53 = InterviewTemplateCriteria.builder()
                .id("53")
                .name("Как взаимодействуют программы, процессы и потоки?")
                .positionType("Java Developer")
                .build();

        val criteria54 = InterviewTemplateCriteria.builder()
                .id("54")
                .name("Что может произойти если два потока будут выполнять один и тот же код в программе?")
                .positionType("Java Developer")
                .build();

        val criteria55 = InterviewTemplateCriteria.builder()
                .id("55")
                .name("Что вы знаете о главном потоке программы?")
                .positionType("Java Developer")
                .build();

        val criteria56 = InterviewTemplateCriteria.builder()
                .id("56")
                .name("Дайте определение понятию “монитор”.")
                .positionType("Java Developer")
                .build();

        val criteria57 = InterviewTemplateCriteria.builder()
                .id("57")
                .name("Дайте определение понятию “взаимная блокировка”.")
                .positionType("Java Developer")
                .build();

        val criteria58 = InterviewTemplateCriteria.builder()
                .id("58")
                .name("Что такое JDBC API и когда его используют?")
                .positionType("Java Developer")
                .build();

        val criteria59 = InterviewTemplateCriteria.builder()
                .id("59")
                .name("Что такое JPA?")
                .positionType("Java Developer")
                .build();

        val criteria60 = InterviewTemplateCriteria.builder()
                .id("60")
                .name("В чем её отличие JPA от Hibernate?")
                .positionType("Java Developer")
                .build();

        val criteria61 = InterviewTemplateCriteria.builder()
                .id("61")
                .name("Можно ли использовать JPA c noSQl базами?")
                .positionType("Java Developer")
                .build();

        val criteria62 = InterviewTemplateCriteria.builder()
                .id("62")
                .name("Что такое Entity?")
                .positionType("Java Developer")
                .build();

        val criteria63 = InterviewTemplateCriteria.builder()
                .id("63")
                .name("Какие типы связей (relationship) между Entity вы знаете (перечислите восемь типов, либо укажите четыре типа связей, каждую из которых можно разделить ещё на два вида)?")
                .positionType("Java Developer")
                .build();

        val criteria64 = InterviewTemplateCriteria.builder()
                .id("64")
                .name("Какие два типа fetch стратегии в JPA вы знаете?")
                .positionType("Java Developer")
                .build();

        val criteria65 = InterviewTemplateCriteria.builder()
                .id("65")
                .name("Что такое EntityManager и какие основные его функции вы можете перечислить?")
                .positionType("Java Developer")
                .build();

        val criteria66 = InterviewTemplateCriteria.builder()
                .id("66")
                .name("Что такое Hibernate Framework?Какие важные преимущества дает использование Hibernate Framework?")
                .positionType("Java Developer")
                .build();

        val criteria67 = InterviewTemplateCriteria.builder()
                .id("67")
                .name("Какие преимущества Hibernate над JDBC?")
                .positionType("Java Developer")
                .build();

        val criteria68 = InterviewTemplateCriteria.builder()
                .id("68")
                .name("Расскажите о Spring Framework.Какие некоторые из важных особенностей и преимуществ Spring Framework?")
                .positionType("Java Developer")
                .build();

        val criteria69 = InterviewTemplateCriteria.builder()
                .id("69")
                .name("Что вы понимаете под Dependency Injection (DI)?Как реализуется DI в Spring Framework?Что такое IoC контейнер Spring?")
                .positionType("Java Developer")
                .build();

        val criteria70 = InterviewTemplateCriteria.builder()
                .id("70")
                .name("Приведите названия некоторых важных Spring модулей.")
                .positionType("Java Developer")
                .build();

        val criteria71 = InterviewTemplateCriteria.builder()
                .id("71")
                .name("Что такое Spring бин?Какие вы знаете различные scope у Spring Bean?")
                .positionType("Java Developer")
                .build();

        val criteria72 = InterviewTemplateCriteria.builder()
                .id("72")
                .name("Какая разница между аннотациями @Component, @Repository и @Service в Spring?")
                .positionType("Java Developer")
                .build();

        val criteria73 = InterviewTemplateCriteria.builder()
                .id("73")
                .name("Задача. Дана строка 'Мама мыла раму', напишите функцию для подсчета частоты встречаемости символов в данной строке. Выходной формат данных на ваше усмотрение.")
                .positionType("Java Developer")
                .build();

        val criterias = Arrays.asList(criteria1, criteria2, criteria3, criteria4, criteria5,
                criteria6, criteria7, criteria8, criteria9, criteria10, criteria11, criteria12, criteria13, criteria14,
                criteria15, criteria16, criteria17, criteria18, criteria19, criteria20, criteria21, criteria22,
                criteria23, criteria24, criteria25, criteria26, criteria27, criteria28, criteria29, criteria30,
                criteria31, criteria32, criteria33, criteria34, criteria35, criteria36, criteria37, criteria38,
                criteria39, criteria40, criteria41, criteria42, criteria43, criteria44, criteria45, criteria46, criteria47,
                criteria48, criteria49, criteria50, criteria51, criteria52, criteria53, criteria54, criteria55,
                criteria56, criteria57, criteria58, criteria59, criteria60, criteria61, criteria62, criteria63,
                criteria64, criteria64, criteria65, criteria66, criteria67, criteria68, criteria69, criteria70,
                criteria71, criteria72, criteria73);

        val template = InterviewTemplate.builder()
                .id("1")
                .positionName("Java Junior Developer")
                .criterias(criterias)
                .build();

        val interview = Interview.builder()
                .interviewer(interviewer)
                .candidate(candidate)
                .interviewTemplate(template)
                .desiredSalary("2000 EUR")
                .interviewDateTime(LocalDateTime.now())
                .interviewStatus(InterviewStatus.PLANNED)
                .decisionStatus(DecisionStatus.NOT_APPLICABLE)
                .build();

        interviewRepository.save(interview);
    }
}
