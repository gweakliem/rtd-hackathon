# rtd-hackathon

Should I ride RTD? Should I bike?  Should I Uber?

This app provides transportation recommendations based on the current weather.

## To Run

`./gradlew clean assemble bootRun`

## To Debug

`GRADLE_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 gradle bootRun --debug-jvm`

Create a remote debug instance in intellij and execute it.