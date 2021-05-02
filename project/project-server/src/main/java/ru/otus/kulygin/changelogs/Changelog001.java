package ru.otus.kulygin.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import ru.otus.kulygin.domain.Candidate;
import ru.otus.kulygin.domain.InterviewTemplate;
import ru.otus.kulygin.domain.InterviewTemplateCriteria;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.repository.CandidateRepository;
import ru.otus.kulygin.repository.InterviewTemplateCriteriaRepository;
import ru.otus.kulygin.repository.InterviewTemplateRepository;
import ru.otus.kulygin.repository.InterviewerRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@ChangeLog(order = "001")
public class Changelog001 {

    private final String storagePath = System.getProperty("user.home") + "/interviewer-stor/";

    @ChangeSet(order = "001", id = "2021-09-04--001-insert-interviewers--vkulygin", author = "viktor.kulygin")
    public void insertInterviewers(InterviewerRepository interviewerRepository) {

        val interviewer = Interviewer.builder()
                .firstName("Джон")
                .lastName("Петрович")
                .positionType("Java Developer")
                .build();

        val interviewer2 = Interviewer.builder()
                .firstName("Василий")
                .lastName("Петров")
                .positionType("JavaScript Developer")
                .build();

        val interviewer3 = Interviewer.builder()
                .firstName("Анна")
                .lastName("Светлова")
                .positionType("Scala Developer")
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

        val criteria26 = InterviewTemplateCriteria.builder()
                .name("Дайте определение понятию “исключение”")
                .positionType("Java Junior Developer")
                .build();

        val criteria27 = InterviewTemplateCriteria.builder()
                .name("Какова иерархия исключений.")
                .positionType("Java Junior Developer")
                .build();

        val criteria28 = InterviewTemplateCriteria.builder()
                .name("Можно/нужно ли обрабатывать ошибки jvm?")
                .positionType("Java Junior Developer")
                .build();

        val criteria29 = InterviewTemplateCriteria.builder()
                .name("Какие существуют способы обработки исключений?")
                .positionType("Java Junior Developer")
                .build();

        val criteria30 = InterviewTemplateCriteria.builder()
                .name("В чем особенность блока finally? Всегда ли он исполняется?")
                .positionType("Java Junior Developer")
                .build();

        val criteria31 = InterviewTemplateCriteria.builder()
                .name("Может ли не быть ни одного блока catch при отлавливании исключений?")
                .positionType("Java Junior Developer")
                .build();

        val criteria32 = InterviewTemplateCriteria.builder()
                .name("Могли бы вы придумать ситуацию, когда блок finally не будет выполнен?")
                .positionType("Java Junior Developer")
                .build();

        val criteria33 = InterviewTemplateCriteria.builder()
                .name("Может ли один блок catch отлавливать несколько исключений (с одной и разных веток наследований)")
                .positionType("Java Junior Developer")
                .build();

        val criteria34 = InterviewTemplateCriteria.builder()
                .name("Что вы знаете об обрабатываемых и не обрабатываемых (checked/unchecked) исключениях?")
                .positionType("Java Junior Developer")
                .build();

        val criteria35 = InterviewTemplateCriteria.builder()
                .name("В чем особенность RuntimeException?")
                .positionType("Java Junior Developer")
                .build();

        val criteria36 = InterviewTemplateCriteria.builder()
                .name("Какой оператор позволяет принудительно выбросить исключение?")
                .positionType("Java Junior Developer")
                .build();

        val criteria37 = InterviewTemplateCriteria.builder()
                .name("Если оператор return содержится и в блоке catch и в finally, какой из них “главнее”?")
                .positionType("Java Junior Developer")
                .build();

        val criteria38 = InterviewTemplateCriteria.builder()
                .name("Предположим, есть блок try-finally. В блоке try возникло исключение и выполнение переместилось в блок finally. В блоке finally тоже возникло исключение. Какое из двух исключений “выпадет” из блока try-finally? Что случится со вторым исключением?")
                .positionType("Java Junior Developer")
                .build();

        val criteria39 = InterviewTemplateCriteria.builder()
                .name("Предположим, есть метод, который может выбросить IOException и FileNotFoundException в какой последовательности должны идти блоки catch? Сколько блоков catch будет выполнено?")
                .positionType("Java Junior Developer")
                .build();

        val criteria40 = InterviewTemplateCriteria.builder()
                .name("Назовите преимущества использования коллекций.")
                .positionType("Java Junior Developer")
                .build();

        val criteria41 = InterviewTemplateCriteria.builder()
                .name("Какова иерархия коллекций?")
                .positionType("Java Junior Developer")
                .build();

        val criteria42 = InterviewTemplateCriteria.builder()
                .name("Что вы знаете о коллекциях типа List?Что вы знаете о коллекциях типа Set?")
                .positionType("Java Junior Developer")
                .build();

        val criteria43 = InterviewTemplateCriteria.builder()
                .name("Что вы знаете о коллекциях типа Map, в чем их принципиальное отличие?")
                .positionType("Java Junior Developer")
                .build();

        val criteria44 = InterviewTemplateCriteria.builder()
                .name("Что разного/общего у классов ArrayList и LinkedList, когда лучше использовать ArrayList, а когда LinkedList?")
                .positionType("Java Junior Developer")
                .build();

        val criteria45 = InterviewTemplateCriteria.builder()
                .name("Что будет, если в Map положить два значения с одинаковым ключом?")
                .positionType("Java Junior Developer")
                .build();

        val criteria46 = InterviewTemplateCriteria.builder()
                .name("Какие “строковые” классы вы знаете?Какие основные свойства “строковых” классов (их особенности)?Можно ли наследовать строковый тип, почему?")
                .positionType("Java Junior Developer")
                .build();

        val criteria47 = InterviewTemplateCriteria.builder()
                .name("Дайте определение понятию “пул строк”.")
                .positionType("Java Junior Developer")
                .build();

        val criteria48 = InterviewTemplateCriteria.builder()
                .name("Что делает метод intern()?")
                .positionType("Java Junior Developer")
                .build();

        val criteria49 = InterviewTemplateCriteria.builder()
                .name("Чем отличаются и что общего у классов String, StringBuffer и StringBuilder?")
                .positionType("Java Junior Developer")
                .build();

        val criteria50 = InterviewTemplateCriteria.builder()
                .name("Напишите метод удаления данного символа из строки.")
                .positionType("Java Junior Developer")
                .build();

        val criteria51 = InterviewTemplateCriteria.builder()
                .name("Дайте определение понятию “процесс”.")
                .positionType("Java Junior Developer")
                .build();

        val criteria52 = InterviewTemplateCriteria.builder()
                .name("Дайте определение понятию “поток”.")
                .positionType("Java Junior Developer")
                .build();

        val criteria53 = InterviewTemplateCriteria.builder()
                .name("Как взаимодействуют программы, процессы и потоки?")
                .positionType("Java Junior Developer")
                .build();

        val criteria54 = InterviewTemplateCriteria.builder()
                .name("Что может произойти если два потока будут выполнять один и тот же код в программе?")
                .positionType("Java Junior Developer")
                .build();

        val criteria55 = InterviewTemplateCriteria.builder()
                .name("Что вы знаете о главном потоке программы?")
                .positionType("Java Junior Developer")
                .build();

        val criteria56 = InterviewTemplateCriteria.builder()
                .name("Дайте определение понятию “монитор”.")
                .positionType("Java Junior Developer")
                .build();

        val criteria57 = InterviewTemplateCriteria.builder()
                .name("Дайте определение понятию “взаимная блокировка”.")
                .positionType("Java Junior Developer")
                .build();

        val criteria58 = InterviewTemplateCriteria.builder()
                .name("Что такое JDBC API и когда его используют?")
                .positionType("Java Junior Developer")
                .build();

        val criteria59 = InterviewTemplateCriteria.builder()
                .name("Что такое JPA?")
                .positionType("Java Junior Developer")
                .build();

        val criteria60 = InterviewTemplateCriteria.builder()
                .name("В чем её отличие JPA от Hibernate?")
                .positionType("Java Junior Developer")
                .build();

        val criteria61 = InterviewTemplateCriteria.builder()
                .name("Можно ли использовать JPA c noSQl базами?")
                .positionType("Java Junior Developer")
                .build();

        val criteria62 = InterviewTemplateCriteria.builder()
                .name("Что такое Entity?")
                .positionType("Java Junior Developer")
                .build();

        val criteria63 = InterviewTemplateCriteria.builder()
                .name("Какие типы связей (relationship) между Entity вы знаете (перечислите восемь типов, либо укажите четыре типа связей, каждую из которых можно разделить ещё на два вида)?")
                .positionType("Java Junior Developer")
                .build();

        val criteria64 = InterviewTemplateCriteria.builder()
                .name("Какие два типа fetch стратегии в JPA вы знаете?")
                .positionType("Java Junior Developer")
                .build();

        val criteria65 = InterviewTemplateCriteria.builder()
                .name("Что такое EntityManager и какие основные его функции вы можете перечислить?")
                .positionType("Java Junior Developer")
                .build();

        val criteria66 = InterviewTemplateCriteria.builder()
                .name("Что такое Hibernate Framework?Какие важные преимущества дает использование Hibernate Framework?")
                .positionType("Java Junior Developer")
                .build();

        val criteria67 = InterviewTemplateCriteria.builder()
                .name("Какие преимущества Hibernate над JDBC?")
                .positionType("Java Junior Developer")
                .build();

        val criteria68 = InterviewTemplateCriteria.builder()
                .name("Расскажите о Spring Framework.Какие некоторые из важных особенностей и преимуществ Spring Framework?")
                .positionType("Java Junior Developer")
                .build();

        val criteria69 = InterviewTemplateCriteria.builder()
                .name("Что вы понимаете под Dependency Injection (DI)?Как реализуется DI в Spring Framework?Что такое IoC контейнер Spring?")
                .positionType("Java Junior Developer")
                .build();

        val criteria70 = InterviewTemplateCriteria.builder()
                .name("Приведите названия некоторых важных Spring модулей.")
                .positionType("Java Junior Developer")
                .build();

        val criteria71 = InterviewTemplateCriteria.builder()
                .name("Что такое Spring бин?Какие вы знаете различные scope у Spring Bean?")
                .positionType("Java Junior Developer")
                .build();

        val criteria72 = InterviewTemplateCriteria.builder()
                .name("Какая разница между аннотациями @Component, @Repository и @Service в Spring?")
                .positionType("Java Junior Developer")
                .build();

        val criteria73 = InterviewTemplateCriteria.builder()
                .name("Задача. Дана строка 'Мама мыла раму', напишите функцию для подсчета частоты встречаемости символов в данной строке. Выходной формат данных на ваше усмотрение.")
                .positionType("Java Junior Developer")
                .build();

        interviewTemplateCriteriaRepository.saveAll(Arrays.asList(criteria1, criteria2, criteria3, criteria4, criteria5,
                criteria6, criteria7, criteria8, criteria9, criteria10, criteria11, criteria12, criteria13, criteria14,
                criteria15, criteria16, criteria17, criteria18, criteria19, criteria20, criteria21, criteria22,
                criteria23, criteria24, criteria25, criteria26, criteria27, criteria28, criteria29, criteria30,
                criteria31, criteria32, criteria33, criteria34, criteria35, criteria36, criteria37, criteria38,
                criteria39, criteria40, criteria41, criteria42, criteria43, criteria44, criteria45, criteria46, criteria47,
                criteria48, criteria49, criteria50, criteria51, criteria52, criteria53, criteria54, criteria55,
                criteria56, criteria57, criteria58, criteria59, criteria60, criteria61, criteria62, criteria63,
                criteria64, criteria64, criteria65, criteria66, criteria67, criteria68, criteria69, criteria70,
                criteria71, criteria72, criteria73));
    }

    @ChangeSet(order = "003", id = "2021-26-04--001-insert-candidates--vkulygin", author = "viktor.kulygin")
    public void insertCandidates(CandidateRepository candidateRepository) {
        val candidate = Candidate.builder()
                .firstName("Виктор")
                .lastName("Кулыгин")
                .claimingPosition("Java Software Architect")
                .pathToCvFile(storagePath + "Viktor_Kulygin_Java_Developer.pdf")
                .build();

        val candidate2 = Candidate.builder()
                .firstName("Ирина")
                .lastName("Кулыгина")
                .claimingPosition("Java Junior Developer")
                .pathToCvFile(storagePath + "Irina_Kulygina_-_Java_Developer.pdf")
                .build();

        val candidate3 = Candidate.builder()
                .firstName("Андрей")
                .lastName("Оськин")
                .claimingPosition("Java Senior Developer")
                .pathToCvFile(storagePath + "Andrew Oskin Java Developer.pdf")
                .build();

        candidateRepository.saveAll(Arrays.asList(candidate, candidate2, candidate3));

        copyCvFilesToUserHomeDirectory(storagePath, "Viktor_Kulygin_Java_Developer.pdf",
                "Irina_Kulygina_-_Java_Developer.pdf", "Andrew Oskin Java Developer.pdf");
    }

    private void copyCvFilesToUserHomeDirectory(String storagePath, String... fileNames) {
        for (String fileName : fileNames) {
            copyFile(getFileInputStream(fileName), storagePath, fileName);
        }
    }

    private InputStream getFileInputStream(String filename) {
        try {
            return new ClassPathResource("cv/" + filename).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void copyFile(InputStream fileInputStream, String storagePath, String filename) {
        File targetFile = new File(storagePath + filename);

        try {
            Files.copy(
                    fileInputStream,
                    targetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            IOUtils.close(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ChangeSet(order = "004", id = "2021-02-05--001-insert-template--vkulygin", author = "viktor.kulygin")
    public void insertTemplate(InterviewTemplateRepository interviewTemplateRepository, InterviewTemplateCriteriaRepository interviewTemplateCriteriaRepository) {

        val java_junior_developer = interviewTemplateCriteriaRepository.findAllByPositionType("Java Junior Developer");

        val template = InterviewTemplate.builder()
                .positionName("Java Junior Developer")
                .criterias(java_junior_developer)
                .build();

        interviewTemplateRepository.save(template);
    }

}
