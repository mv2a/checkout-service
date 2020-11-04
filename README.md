#Requirements
Java 8+

Gradle 6.7+. Use the wrapper provided to ensure compatibility with the gradle syntax used in this project.
To use the wrapper, simply use ./gradlew (depending on your OS), so you don't need to have gradle installed locally as
the wrapper will download the correct one.

##How to build
`./gradlew clean build`

##How to test
`./gradlew clean test`. Gradle build should also run the tests. 


##To do / To Improve
The code should be self-explanatory, please refer to the tests under test/groovy as a guidance.
Notes of TODOs and assumptions are annotated throughout the code.
An effort was made to keep the implementation of this exercise under 2.5 hours, including all the testing,
readme and github repo setup.

##General Comments
I recommend using IntelliJ as an IDE to go through the unit tests. 
I wrote the tests in groovy as a personal preference, but I would not have any problems writing them
in java. The implementation was kept as standard Java as requested. 

If this message appears:
```
 > Task :test
 WARNING: An illegal reflective access operation has occurred...
```
when building/testing in the command line, it may be due to groovy or the jvm version, java 11+ most likely, 
but nothing to be alarmed.


##Improvements and suggestions
I would recommend the use of a rules engine such as https://github.com/j-easy/easy-rules.
The use of a rule descriptor, i.e `promotionXYZ-rule.yml` could enable the market team to create rules without touching code,
and the checkout service could load them without the need of making a release, also easier to test and verify rules during 
development and testing phases.