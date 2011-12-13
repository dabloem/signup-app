package org.company.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
	public List<SignupRequest> listAllRequsts() {
		List<SignupRequest> results = signupRequestService.getAllRequests();
		return results;
	}

	@GET
	@Path("/{id}")
	@Produces("text/xml")
	public SignupRequest lookupById(@PathParam("id") String email) {
		return signupRequestService.get(email);
	}
}
