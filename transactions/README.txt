See tutorial online at:

https://spring.io/guides/gs/managing-transactions/

Or in my notes directory:

Managing Transactions Tutorial.docx

In STS run the gradle tasks clean test

In a shell in /transactions run:

./gradlew clean build && java -jar build/libs/transactions-0.1.0.jar


You should see the following output:

Creating tables
Booking Alice in a seat...
Booking Bob in a seat...
Booking Carol in a seat...
Booking Chris in a seat...
Booking Samuel in a seat...
Jul 11, 2013 10:20:14 AM org.springframework.beans.factory.xml.XmlBeanDefinitionReader loadBeanDefinitions
INFO: Loading XML bean definitions from class path resource [org/springframework/jdbc/support/sql-error-codes.xml]
Jul 11, 2013 10:20:14 AM org.springframework.jdbc.support.SQLErrorCodesFactory <init>
INFO: SQLErrorCodes loaded: [DB2, Derby, H2, HSQL, Informix, MS-SQL, MySQL, Oracle, PostgreSQL, Sybase]
PreparedStatementCallback; SQL [insert into BOOKINGS(FIRST_NAME) values (?)]; Value too long for column "FIRST_NAME VARCHAR(5) NOT NULL": "'Samuel' (6)"; SQL statement:
insert into BOOKINGS(FIRST_NAME) values (?) [22001-171]; nested exception is org.h2.jdbc.JdbcSQLException: Value too long for column "FIRST_NAME VARCHAR(5) NOT NULL": "'Samuel' (6)"; SQL statement:
insert into BOOKINGS(FIRST_NAME) values (?) [22001-171]
Booking Buddy in a seat...
Booking null in a seat...
PreparedStatementCallback; SQL [insert into BOOKINGS(FIRST_NAME) values (?)]; NULL not allowed for column "FIRST_NAME"; SQL statement:
insert into BOOKINGS(FIRST_NAME) values (?) [23502-171]; nested exception is org.h2.jdbc.JdbcSQLException: NULL not allowed for column "FIRST_NAME"; SQL statement:
insert into BOOKINGS(FIRST_NAME) values (?) [23502-171]
The BOOKINGS table has two constraints on the first_name column:

Names cannot be longer than five characters.
Names cannot be null.
The first three names inserted are Alice, Bob, and Carol. The application asserts that three people were added to that table. If that had not worked, the application would have exited early.

Next, another booking is done for Chris and Samuel. Samuel's name is deliberately too long, forcing an insert error. Transactional behavior stipulates that both Chris and Samuel; that is, this transaction, should be rolled back. Thus there should still be only three people in that table, which the assertion demonstrates.

Finally, Buddy and null are booked. As the output shows, null causes a rollback as well, leaving the same three people booked.