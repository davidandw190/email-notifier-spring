package com.davidandw190.emailnotifier.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Represents an HTTP response model used for conveying response details to clients.
 */
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected String _developerMessage;     /*  developer message providing additional information about the response.*/
    protected String path;                  /*  path of the requested resource associated with the response. */
    protected String requestMethod;
    protected Map<?, ?> data;
}
