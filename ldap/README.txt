See tutorial online at:

https://spring.io/guides/gs/authenticating-ldap/

Or in my notes directory:

LDAP Authentication Tutorial.docx

In STS run the gradle tasks clean test

In a shell in /ldap run:

./gradlew clean build && java -jar build/libs/ldap-0.1.0.jar

Navigate to:

http://localhost:8080

Enter username ben and password benspassword.