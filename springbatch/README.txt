See tutorial online at:

https://spring.io/guides/gs/batch-processing/

Or in my notes directory:

Spring Batch Tutorial.docx

In STS run the gradle tasks clean test

In a shell in /springbatch run:

./gradlew clean build && java -jar build/libs/springbatch-0.1.0.jar


schema-all.sql is loaded on startup, as Spring Boot runs schema-@@platform@@.sql automatically during startup. -all is the default for all platforms.

The job prints out a line for each person that gets transformed. After the job runs, you can also see the output from querying the database.

Converting (firstName: Jill, lastName: Doe) into (firstName: JILL, lastName: DOE)
Converting (firstName: Joe, lastName: Doe) into (firstName: JOE, lastName: DOE)
Converting (firstName: Justin, lastName: Doe) into (firstName: JUSTIN, lastName: DOE)
Converting (firstName: Jane, lastName: Doe) into (firstName: JANE, lastName: DOE)
Converting (firstName: John, lastName: Doe) into (firstName: JOHN, lastName: DOE)
Found <firstName: JILL, lastName: DOE> in the database.
Found <firstName: JOE, lastName: DOE> in the database.
Found <firstName: JUSTIN, lastName: DOE> in the database.
Found <firstName: JANE, lastName: DOE> in the database.
Found <firstName: JOHN, lastName: DOE> in the database.