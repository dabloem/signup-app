package org.company.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
	@Consumes("text/xml")
	public void register(SignupRequest request) {
		signupRequestService.register(request);
	}

	@Path("/confirm/{id}")
	public void confirm(String id) {
		signupRequestService.confirm(id);
	}

	@Path("/approve/{id}")
	public void approve(String id) {
		signupRequestService.approve(id);
	}

	@Path("/deny/{id}")
	public void deny(String id) {
		signupRequestService.decline(id);
	}

}
