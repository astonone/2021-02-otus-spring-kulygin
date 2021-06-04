#ht13-spring-security-authorization-and-acl 
Homework

Introduce authorisation based on URL and/or domain entities

>Purpose: learn how to protect your application with full authentication and differentiation of access rights<br>
Result: a complete application with Spring Security

Warning. The task is based on a non-reactive Spring MVC application!

Requirements:
1. Minimum: configure authorization at the URL level in the application.
2. Maximum: configure the authorization in the application based on domain entities and service methods.

Recommendations for implementation:

It is not recommended to assign users with different permissions to different classes - i.e. just a single user class.
In case of authorization based on domain entities and PostgreSQL do not use GUIDs for entities.

This task does NOT count the previous ones!