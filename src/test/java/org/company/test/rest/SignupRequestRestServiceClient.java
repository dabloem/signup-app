package org.company.test.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@Path("/signup")
public interface SignupRequestRestServiceClient {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/secured/unconfirmed")
	public String listAllUnconfirmedRequsts();

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/secured/confirmed")
	public String listAllConfirmedRequsts();

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/secured/approved")
	public String listAllApprovedRequsts();

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/secured/denied")
	public String listAllDeniedRequsts();

	@GET
	@Path("/secured/{id:[\\w]{32}}")
	@Produces(MediaType.APPLICATION_XML)
	public Response lookupById(@PathParam("id") String id);

	@POST
	@Path("/register")
	@Consumes("application/x-www-form-urlencoded")
	public Response register(MultivaluedMap<String, String> formParams,
			@HeaderParam("Referer") String referer);

	@GET
	@Path("/confirm/{id:[\\w]{32}}")
	public Response confirm(@PathParam("id") String id);

	@GET
	@Path("/secured/approve/{id:[\\w]{32}}")
	public Response approve(@PathParam("id") String id);
	

	@GET
	@Path("/secured/approveDenied/{id:[\\w]{32}}")
	public Response approveDenied(@PathParam("id") String id);

	@GET
	@Path("/secured/deny/{id:[\\w]{32}}")
	public Response deny(@PathParam("id") String id);

}
