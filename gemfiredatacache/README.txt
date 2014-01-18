See tutorial online at:

https://spring.io/guides/gs/caching-gemfire/

Or in my notes directory:

Gemfire Data Access Tutorial.docx

In STS run the gradle tasks clean test

In a shell in /gemfiredatacache run:

./gradlew clean build && java -jar build/libs/gemfiredatacache-0.1.0.jar



Logging output is displayed. The service should be up and running within a few seconds.

Found Page [name=SpringSource, website=http://www.springsource.com], and it only took 620 ms to find out!

Found Page [name=SpringSource, website=http://www.springsource.com], and it only took 0 ms to find out!

Found Page [name=Pivotal, website=http://www.gopivotal.com], and it only took 78 ms to find out!

From this you can see that the first call to Facebook for the SpringSource page took 620ms, which the second call took 0ms. 
That clearly shows that the second call was cached and never actually hit Facebook. But when GoPivotal's page was retrieved, 
it took 78ms, which while faster than 620ms, was definitely NOT the result of retrieving cached data.