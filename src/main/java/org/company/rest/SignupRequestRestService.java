package org.company.rest;

import java.net.URI;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.company.model.SignupRequest;
import org.company.service.SignupRequestService;

/**
 * Singup request REST API.
 *
 * @author hantsy
 */
@Path("/signup")
@RequestScoped
public class SignupRequestRestService {

    @Inject
    private SignupRequestService signupRequestService;
    @Context
    UriInfo uriInfo;
    @Context
    ServletContext servletContext;

    /**
     * Fetch all unconfirmed requests.
     *
     * @return the unconfirmed request in xml format.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/secured/unconfirmed")
    public List<SignupRequest> listAllUnconfirmedRequsts() {
        List<SignupRequest> results = signupRequestService.getAllUnconfirmedRequests();
        return results;
    }

    /**
     * Fetch all confirmed requests.
     *
     * @return the confirmed request in xml format.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/secured/confirmed")
    public List<SignupRequest> listAllConfirmedRequsts() {
        List<SignupRequest> results = signupRequestService.getAllConfirmedRequests();
        return results;
    }

    /**
     * Fetch all approved requests.
     *
     * @return the approved request in xml format.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/secured/approved")
    public List<SignupRequest> listAllApprovedRequsts() {
        List<SignupRequest> results = signupRequestService.getAllApprovedRequests();
        return results;
    }

    /**
     * Fetch all denied requests.
     *
     * @return the denied request in xml format.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/secured/denied")
    public List<SignupRequest> listAllDeniedRequsts() {
        List<SignupRequest> results = signupRequestService.getAllDeniedRequests();
        return results;
    }

    /**
     * Retrieve single Signup request content.
     *
     * @param id the Signup request id.
     * @return return the request in xml format.
     */
    @GET
    @Path("/secured/{id:[\\w]{32}}")
    @Produces(MediaType.APPLICATION_XML)
    public Response lookupById(@PathParam("id") String id) {
        SignupRequest _request = null;
        _request = signupRequestService.get(id);
        return Response.ok(_request).build();
    }

    /**
     * Register a Signup request from client form based post page.
     *
     * @param formParams the form fields.
     * @param referer the Refer header.
     * @return if successfully, redirect to the "/ok.jsf" page.
     */
    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(MultivaluedMap<String, String> formParams, @HeaderParam("Referer") String referer) {
        SignupRequest _request = new SignupRequest();
        _request.setFirstName(formParams.getFirst(SignupRequest.ATTR_FIRSTNAME));
        _request.setLastName(formParams.getFirst(SignupRequest.ATTR_LASTNAME));
        _request.setCompanyName(formParams.getFirst(SignupRequest.ATTR_COMPANY_NAME));
        _request.setEmail(formParams.getFirst(SignupRequest.ATTR_EMAIL));
        _request.setComment(formParams.getFirst(SignupRequest.ATTR_COMMENT));

        _request.setHttpRefer(referer);
        signupRequestService.register(_request);

        return Response.seeOther(redirectUri("/ok.jsf")).build();
    }

    /**
     * Confirm the unconfirmed request.
     *
     * @param id id of the unconfirmed request.
     * @return if successfully, redirect to the "/ok.jsf" page.
     */
    @GET
    @Path("/confirm/{id:[\\w]{32}}")
    public Response confirm(@PathParam("id") String id) {

        signupRequestService.confirm(id);

        return Response.seeOther(redirectUri("/ok.jsf")).build();
    }

    /**
     * Approved the confirmed request.
     *
     * @param id id of the confirmed request.
     * @return if successfully, redirect to the "/ok.jsf" page.
     */
    @GET
    @Path("/secured/approve/{id:[\\w]{32}}")
    public Response approve(@PathParam("id") String id) {

        signupRequestService.approve(id);

        return Response.seeOther(redirectUri("/ok.jsf")).build();
    }

    /**
     * Approved the denied request.
     *
     * @param id id of the denied request.
     * @return if successfully, redirect to the "/ok.jsf" page.
     */
    @GET
    @Path("/secured/approveDenied/{id:[\\w]{32}}")
    public Response approveDenied(@PathParam("id") String id) {

        signupRequestService.approveDenied(id);

        return Response.seeOther(redirectUri("/ok.jsf")).build();
    }

    /**
     * Deny the confirmed request.
     *
     * @param id id of the confirmed request.
     * @return if successfully, redirect to the "/ok.jsf" page.
     */
    @GET
    @Path("/secured/deny/{id:[\\w]{32}}")
    public Response deny(@PathParam("id") String id) {
        signupRequestService.deny(id);
        return Response.seeOther(redirectUri("/ok.jsf")).build();
    }

    private URI redirectUri(String path) {
        URI baseUri = uriInfo.getBaseUri();
        String host = baseUri.getHost();
        String schema = baseUri.getScheme();
        int port = baseUri.getPort();
        return UriBuilder.fromPath(
                schema
                + "://"
                + host
                + (port == 80 ? "" : ":" + String.valueOf(port))
                + servletContext.getContextPath()).path(path).build();
    }
    // private Response translateSignupRequestNotFoundExceptionToResponse(
    // SignupRequestNotFoundException ex) {
    // return Response.status(404).entity(ex.getMessage()).type("text/plain")
    // .build();
    // }
}
