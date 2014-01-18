See tutorial online at:

https://spring.io/guides/gs/accessing-data-gemfire/

Or in my notes directory:

Gemfire Data Access Tutorial.docx

In STS run the gradle tasks clean test

In a shell in /gemfiredataaccess run:

./gradlew clean build && java -jar build/libs/gemfiredataaccess-0.1.0.jar


You should see something like this (with other stuff like queries as well):

Before linking up with GemFire...
	Alice is 40 years old.
	Baby Bob is 1 years old.
	Teen Carol is 13 years old.
Lookup each person by name...
	Alice is 40 years old.
	Baby Bob is 1 years old.
	Teen Carol is 13 years old.
Adults (over 18):
	Alice is 40 years old.
Babies (less than 5):
	Baby Bob is 1 years old.
Teens (between 12 and 20):
	Teen Carol is 13 years old.
That isn't everything you'll see. With the debug levels of Spring Data GemFire turned up, you 
also see some mixed in log statements, giving you a glimpse of the query language used with GemFire. 