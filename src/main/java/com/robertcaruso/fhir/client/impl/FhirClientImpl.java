package com.robertcaruso.fhir.client.impl;

import com.robertcaruso.fhir.client.FhirClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Parameters;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.IntegerDt;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BearerTokenAuthInterceptor;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;

@Service
public class FhirClientImpl implements FhirClient {

    private FhirContext fhirContext = FhirContext.forDstu2();
    @Value("${fhir.url}")
    private String fhirURL;

    /**
     * 
     * @param token     Oauth token needed to log in
     * @param patientId The patient we will be searching
     * @param skip      skip for paging
     * @return a json string
     * @throws AuthenticationException
     */
    public String getPatient(String token, String patientId, String skip) throws AuthenticationException {
        IGenericClient fhirClient = getFhirClient(token);
        Bundle bundle = null;

        if (skip == null) {
            bundle = fhirClient.operation().onInstance(new IdDt("Patient", patientId)).named("$everything")
                    .withNoParameters(Parameters.class).useHttpGet().returnResourceType(Bundle.class).execute();
        } else {
            bundle = fhirClient.operation().onInstance(new IdDt("Patient", patientId)).named("$everything")
                    .withParameter(Parameters.class, "_skip", new IntegerDt(skip)).useHttpGet()
                    .returnResourceType(Bundle.class).execute();
        }

        return parseBundle(bundle);

    }

    private String parseBundle(Bundle bundle) {
        IParser parser = fhirContext.newJsonParser();
        return parser.encodeResourceToString(bundle);

    }

    /**
     * Gets fhir client.
     *
     * @return the fhir client
     */
    public IGenericClient getFhirClient(String token) {
        IGenericClient fhirClient = fhirContext.newRestfulGenericClient(fhirURL);

        BearerTokenAuthInterceptor authInterceptor = new BearerTokenAuthInterceptor(token);
        fhirClient.registerInterceptor(authInterceptor);

        return fhirClient;
    }
}
