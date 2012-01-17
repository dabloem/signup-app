package org.company.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.company.service.SignupRequestNotFoundException;

/**
 * Handle the SignupRequestNotFoundException exception, if encountered a SignupRequestNotFoundException, return 404
 * response code to the client.
 *
 * @author hantsy
 */
@Provider
public class SignupRequestNotFoundExceptionMapper implements
        ExceptionMapper<SignupRequestNotFoundException> {

    @Override
    public Response toResponse(SignupRequestNotFoundException exception) {
        return Response.status(404).entity(exception.getMessage()).type("text/plain").build();
    }
}
