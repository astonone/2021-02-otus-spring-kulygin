#ht05-spring-jdbc
Homework

>Create an application that stores information about books in the library<br>
Purpose: use Spring JDBC and spring-boot-starter-jdbc capabilities to connect to relational databases<br>
Result: an application with data storage in a relational database, which we will develop<br>

This homework is NOT based on the previous one.

Requirements:
1. Use Spring JDBC and a relational database (H2 or true relational database). We strongly recommend using NamedParametersJdbcTemplate
2. Provide tables of authors, books, and genres.
3. Assume a many-to-one relationship (book has one author and genre). Optional complication - many-to-many relation (book can have many authors and/or genres).
4. The interface is done in Spring Shell (CRUD of the book is required, author and genre operations are optional).
5. The table creation script and data filling script should automatically run with spring-boot-starter-jdbc.
6. Cover with tests as much as possible.

Recommendations for doing the work:

1. Do NOT do AbstractDao.
2. DO NOT do inheritance in tests.

This homework is the basis for the following.