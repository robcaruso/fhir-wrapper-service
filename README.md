# fhir-wrapper-service
This is a spring boot service that wraps hapi fhir client to make a call to the $everything resource of a fhir server.  

## Assumptions 
This service uses a hard coded patient ID that can be updated in configuration.  It assumes the token you are sending is valid and associated with the hard coded patient.

## Configuring the service
You can update the application.properties file in the /src/main/resources directory

```
fhir.url=your fhir server 
fhir.patientId= your patient id  
origins.url= origin url for cors  
```
## Running the service
`mvnw spring-boot:run`

Once it is running, you can access the service locally here http://localhost:8080

