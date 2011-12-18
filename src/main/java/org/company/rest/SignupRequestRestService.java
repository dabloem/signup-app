package org.company.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.company.model.SignupRequest;
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
	
	@Context UriInfo uriInfo;

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
	@Path("/aproved")
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
	@Path("/{id}")
	@Produces("text/xml")
	public SignupRequest lookupById(@PathParam("id") String id) {
		return signupRequestService.get(id);
	}

	@POST
	@Path("/register")
	@Consumes("application/x-www-form-urlencoded")
	public void register(MultivaluedMap<String, String> formParams) {
		SignupRequest _request = new SignupRequest();
		_request.setFirstName(formParams.getFirst(SignupRequest.ATTR_FIRSTNAME));
		_request.setLastName(formParams.getFirst(SignupRequest.ATTR_LASTNAME));
		_request.setCompanyName(formParams
				.getFirst(SignupRequest.ATTR_COMPANY_NAME));
		_request.setEmail(formParams.getFirst(SignupRequest.ATTR_EMAIL));
		_request.setComment(formParams.getFirst(SignupRequest.ATTR_COMMENT));
		_request.setHttpRefer(uriInfo.getRequestUri().toASCIIString());

		signupRequestService.register(_request);
	}

	@PUT
	@Path("/confirm/{id}")
	public void confirm(@PathParam("id") String id) {
		signupRequestService.confirm(id);
	}

	@PUT
	@Path("/approve/{id}")
	public void approve(@PathParam("id") String id) {
		signupRequestService.approve(id);
	}

	@PUT
	@Path("/deny/{id}")
	public void deny(@PathParam("id") String id) {
		signupRequestService.decline(id);
	}

}
