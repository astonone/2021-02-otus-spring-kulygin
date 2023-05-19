#ht01-spring-xml-configuration 
Homework

>Student testing application<br>
Purpose: to create an application using Spring IoC to learn about the core IoC functionality on which all of Spring is built.<br>
Result: a simple application configured with XML context.<br>

Task Description:

The resource stores questions and various answers to them as a CSV file (5 questions).
The questions can be multiple-choice or free-answer questions - up to your desire and discretion.
The application should simply output the test questions from the CSV file with possible answers.

Requirements:
0. The application must have an object model (give preference to objects and classes rather than strings and arrays/lists of strings).
1. All the classes in the application must solve a strictly defined task (see 18-19 "Rules for code design.pdf" attached to the class materials).
2. The context is described by an XML file.
3. All dependencies must be configured in the IoC container.
4. The name of the resource with the questions (CSV file) must be charcoded with a line in the XML file with the context.
5. The CSV with questions is read exactly as a resource, not as a file.
6. Scanner, PrintStream and other standard types do not need to be put in context!
7. All input and output is in English.
8. It is highly desirable to write a unit-test of some service (only the attempt to write a test will be evaluated).
9. Remember - "without fanaticism".

Optional (task with a "star"):
1*. The application must run correctly with `java -jar`.