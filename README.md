# term-proj-server
ENSF 409 Term Project Server
Group: Alexa Calkhoven, Radu Schirliu, Jordan Kwan

## Building
The project requires JUnit5 and MySQL Connector 8.0.19 in order to build.

## Running
To run the project, run the `Server` class with no arguments.
This will start a that listens for connections on the port `4200`
  
To specify the a port for the server to listen on, specify the port as the first and only
command line argument.

## Notes
The server does not currently have a database setup, so it stores all data in memory, thus it does not persist across client sessions.
For testing purposes, there are two users currently set up, with IDs 1 and 3.