# rtd-data

Placeholder for experiments with the GTFS data supplied by the Denver Regional Transportation District.

RTD supplies static routing information and real time updates for specific routes.

The general idea is to build a Java model for the static data and then see
what kinds of things I can do with the real time data.

## Notes

kinds of questions we like to ask about data:
* What's the nearest transit stop to a given location?
* If I want to go to a given destination from a given location, what routes are available? To answer this, we need to
know the nearest stop to the start and destination, then figure out what routes serve both stops.
* What routes are running today?
* Given a particular route, when is the next bus on that route?

## To Run

`./gradlew clean assemble bootRun`

## To Debug

`GRADLE_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 gradle bootRun --debug-jvm`

Create a remote debug instance in intellij and execute it.