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
** The issue with this is that the nearest stop may not have a route to the destination, or that a stop may be served
by a large number of routes.
** Algorithms like Dijkstra & A* https://en.wikipedia.org/wiki/A*_search_algorithm progressively build routes by navigating
stop to stop. Problem is figuring how far to look for stops. Maybe the nearest stop algo should filter by distance?
** Just because a stop is nearest doesn't mean it's served by a route that can get you where you want to go, or that that
route is the fastest. It's possible a farther stop could have faster service.
* What routes are running today?
* Given a particular route, when is the next bus on that route?

Ways to navigate model
trip -> stop time -> stop
stop -> stop time -> trip -> route

## TODOs
1. Figure out how to get the RestTemplate parsing working (override incorrect media type served by RTD)

## To Run

`./gradlew clean assemble bootRun`

## To Debug

`GRADLE_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 gradle bootRun --debug-jvm`

Create a remote debug instance in intellij and execute it.