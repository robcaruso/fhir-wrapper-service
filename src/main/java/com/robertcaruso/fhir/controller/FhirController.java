package com.robertcaruso.fhir.controller;

import com.robertcaruso.fhir.client.FhirClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;

@RestController
public class FhirController {
    @Value("${fhir.patientId}")
    private String patientId;
    @Autowired
    FhirClient fhirClient;

    /**
     * 
     * @param token Oauth token needed to log in
     * @param _skip skip for paging
     * @return a fhir bundle as a <code>ResponseEntity</code>
     */
    @GetMapping(path = "/{token}")
    public ResponseEntity<String> getEverything(@PathVariable String token,
            @RequestParam(required = false, name = "_skip") String _skip) {
        HttpStatus status = HttpStatus.OK;
        String bundle = null;
        try {
            bundle = fhirClient.getPatient(token, patientId, _skip);
        } catch (AuthenticationException e) {
            status = HttpStatus.UNAUTHORIZED;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(bundle, status);
    }
}
