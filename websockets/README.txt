See tutorial online at:

https://spring.io/guides/gs/messaging-stomp-websocket/

Or in my notes directory:

WebSockets Tutorial.docx

In STS run the gradle tasks clean test

In a shell in /websockets run:

./gradlew clean build && java -jar build/libs/websockets-0.1.0.jar

Now that the service is running, point your browser at http://localhost:8080 and click the 
"Connect" button.

Upon opening a connection, you are asked for your name. Enter your name and click "Send". 
Your name is sent to the server as a JSON message over STOMP. After a 3-second simulated 
delay, the server sends a message back with a "Hello" greeting that is displayed on the 
page. At this point, you can send another name, or you can click the "Disconnect" button 
to close the connection.