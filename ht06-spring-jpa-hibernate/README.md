#ht06-spring-jpa-hibernate
Homework

>To rewrite book storage application on ORM<br>
Purpose: to work fully with JPA + Hibernate to connect to relational databases via ORM framework<br>
Result: High-level application with JPA entity mapping<br>

Homework is done by rewriting the previous one in JPA.

Requirements:
1. Use JPA, Hibernate only as a JPA provider.
2. Hibernate-specific @Fetch and @BatchSize annotations can be used to solve the N+1 problem.
3. Add a "book comment" entity, implement CRUD for the new entity.
4. Cover repositories with tests, using H2 database and appropriate H2 Hibernate dialect for tests.
5. Don't forget to disable DDL via Hibernate
6. It is recommended to put @Transactional only on service methods.

This homework will be used as a basis for other DDLs. This work does not pass the previous one!