package ru.otus.kulygin;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class HT08SpringDataMongoDb {

    // Для того чтобы Flapdoodle Embedded MongoDB работал на маках с процессорами m1
    static { System.setProperty("os.arch", "i686_64"); }

    public static void main(String[] args) {
        SpringApplication.run(HT08SpringDataMongoDb.class, args);
    }

}
