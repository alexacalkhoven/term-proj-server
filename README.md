# term-proj-server
ENSF 409 Term Project Server

## Group
- Alexa Calkhoven, alexa.calkhoven1@ucalgary.ca
- Radu Schirliu, radu.schirliu1@ucalgary.ca
- Jordan Kwan, jordan.kwan2@ucalgary.ca

## Extra Features
The extra features include:
- A login system so that multiple students may log in, and each see their own separate registrations
- A separate admin system so that admins can create courses, course offerings, students, and pre-requisites
- JUnit tests for the network controllers on the server
- Running the server and client on computers that are not on the same network
- A view of all the students on the admin panel
- Using reflection and method annotations on the server to handle routing of network requests
- MVC architecture for both the client and server

## Building
The project requires JUnit5 and MySQL Connector 8.0.19 in order to build.

## Running
To run the project, run the `Server` class with no arguments.
This will start a that listens for connections on the port `4200`
  
To specify the a port for the server to listen on, specify the port as the first and only
command line argument.

To setup the database connection, the following environment variables need to be set: `ENSF_DB_URL, ENSF_DB_USER, ENSF_DB_PASSWORD`.  
The environment variables should look something along the line of:
```shell
ENSF_DB_URL=jdbc:mysql://localhost/ensf_db
ENSF_DB_USER=root
ENSF_DB_PASSWORD=myPass
```

If the tables do not already exist in the database, they will be automatically created when you run the server.