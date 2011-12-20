package org.company.rest;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.company.model.SignupRequest;
import org.company.service.SignupRequestNotFoundException;
import org.company.service.SignupRequestService;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members
 * table.
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

	@GET
	@Produces("text/xml")
	@Path("/unconfirmed")
	public List<SignupRequest> listAllUnconfirmedRequsts() {
		List<SignupRequest> results = signupRequestService
				.getAllUnconfirmedRequests();
		return results;
	}

	@GET
	@Produces("text/xml")
	@Path("/confirmed")
	public List<SignupRequest> listAllConfirmedRequsts() {
		List<SignupRequest> results = signupRequestService
				.getAllConfirmedRequests();
		return results;
	}

	@GET
	@Produces("text/xml")
	@Path("/approved")
	public List<SignupRequest> listAllApprovedRequsts() {
		List<SignupRequest> results = signupRequestService
				.getAllApprovedRequests();
		return results;
	}

	@GET
	@Produces("text/xml")
	@Path("/denied")
	public List<SignupRequest> listAllDeniedRequsts() {
		List<SignupRequest> results = signupRequestService
				.getAllDeniedRequests();
		return results;
	}

	@GET
	@Path("/{id:[\\w]{32}}")
	@Produces("text/xml")
	public Response lookupById(@PathParam("id") String id) {
		SignupRequest _request=null;
		try {
			_request= signupRequestService.get(id);
		} catch (SignupRequestNotFoundException e) {
			return translateSignupRequestNotFoundExceptionToResponse(e);
		}
		return Response.ok(_request).build();
	}

	@POST
	@Path("/register")
	@Consumes("application/x-www-form-urlencoded")
	public Response register(MultivaluedMap<String, String> formParams) {
		SignupRequest _request = new SignupRequest();
		_request.setFirstName(formParams.getFirst(SignupRequest.ATTR_FIRSTNAME));
		_request.setLastName(formParams.getFirst(SignupRequest.ATTR_LASTNAME));
		_request.setCompanyName(formParams
				.getFirst(SignupRequest.ATTR_COMPANY_NAME));
		_request.setEmail(formParams.getFirst(SignupRequest.ATTR_EMAIL));
		_request.setComment(formParams.getFirst(SignupRequest.ATTR_COMMENT));

		_request.setHttpRefer(uriInfo.getRequestUri().toASCIIString());
		signupRequestService.register(_request);

		return Response.seeOther(redirectUri("/ok.jsf")).build();
	}

	@GET
	@Path("/confirm/{id:[\\w]{32}}")
	public Response confirm(@PathParam("id") String id) {
		try {
			signupRequestService.confirm(id);
		} catch (SignupRequestNotFoundException e) {
			return translateSignupRequestNotFoundExceptionToResponse(e);
		}
		return Response.seeOther(redirectUri("/ok.jsf")).build();
	}

	@GET
	@Path("/approve/{id:[\\w]{32}}")
	public Response approve(@PathParam("id") String id) {
		try {
			signupRequestService.approve(id);
		} catch (SignupRequestNotFoundException e) {
			return translateSignupRequestNotFoundExceptionToResponse(e);
		}
		return Response.seeOther(redirectUri("/ok.jsf")).build();
	}

	@GET
	@Path("/deny/{id:[\\w]{32}}")
	public Response deny(@PathParam("id") String id) {
		try {
			signupRequestService.deny(id);
		} catch (SignupRequestNotFoundException e) {
			return translateSignupRequestNotFoundExceptionToResponse(e);
		}
		return Response.seeOther(redirectUri("/ok.jsf")).build();
	}

	private URI redirectUri(String path) {
		URI baseUri = uriInfo.getBaseUri();
		String host = baseUri.getHost();
		String schema = baseUri.getScheme();
		int port = baseUri.getPort();
		return UriBuilder
				.fromPath(
						schema
								+ "://"
								+ host
								+ (port == 80 ? "" : ":" + String.valueOf(port))
								+ servletContext.getContextPath()).path(path)
				.build();
	}

	private Response translateSignupRequestNotFoundExceptionToResponse(
			SignupRequestNotFoundException ex) {
		return Response.status(404).entity(ex.getMessage()).type("text/plain")
				.build();
	}

}
