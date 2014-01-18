See tutorial online at:

https://spring.io/guides/gs/messaging-rabbitmq/

Or in my notes directory:

RabbitMQ Tutorial.docx

In STS run the gradle tasks clean test

Start RabbitMQ:

In dev/tools/rabbitmq_server-3.2.2:

sudo ./sbin/rabbitmq-server

or 

sudo ./sbin/rabbitmqctl start

In a shell in /rabbitmq run:

./gradlew clean build && java -jar build/libs/rabbitmq-0.1.0.jar

This shows the following output:

Sending message... Received <Hello from RabbitMQ!>

Stop RabbitMQ with:

sudo ./sbin/rabbitmqctl stop