package com.robertcaruso.fhir.client;

import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;

public interface FhirClient {
    /**
     * 
     * @param token     Oauth token needed to log in
     * @param patientId The patient we will be searching
     * @param skip      skip for paging
     * @return a json string
     * @throws AuthenticationException
     */
    public String getPatient(String token, String patientId, String skip) throws AuthenticationException;

}
