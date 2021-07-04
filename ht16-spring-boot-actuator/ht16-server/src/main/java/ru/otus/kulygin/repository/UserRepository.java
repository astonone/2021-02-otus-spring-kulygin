package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

}
